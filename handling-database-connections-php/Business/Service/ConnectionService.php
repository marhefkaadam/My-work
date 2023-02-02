<?php declare(strict_types=1);

namespace App\Model\Business\Service;

use App\Model\Business\Diagram\Diagram;
use App\Model\Business\Entity\Connection;
use App\Model\Business\Entity\QueryResult;
use App\Model\Business\Enums\ConnectionEnum;
use App\Model\Business\Enums\QueryTypeEnum;
use App\Model\Business\Enums\StatementGroupEnum;
use App\Model\Business\Enums\StatementSubTypeEnum;
use App\Model\Business\QueryParser\QueryParserFactory;
use App\Model\Business\Repository\ConnectionRepository;
use App\Model\Form\Requests\CompareQueriesRequest;
use App\Model\Form\Requests\CreateConnectionRequest;
use App\Model\Form\Requests\MultiplePermissionsRequest;
use App\Model\Form\Requests\UpdateConnectionRequest;
use Dbs\UtilsBundle\Crypto\CryptoInterface;
use Doctrine\DBAL\Connection as DBALConnection;
use Doctrine\DBAL\DriverManager;
use Doctrine\DBAL\Exception as DBALException;
use Doctrine\ORM\EntityManagerInterface;
use Exception;
use RuntimeException;
use Symfony\Component\HttpClient\HttpClient;

class ConnectionService
{
    public function __construct(
        private ConnectionRepository $repository,
        private EntityManagerInterface $em,
        private PermissionService $permissionService,
        private QueryParserFactory $queryParserFactory,
        private DiagramService $diagramService,
        private CryptoInterface $crypto
    )
    {
    }

    /**
     * Gets all connections visible to the current user.
     * @return Connection[]
     */
    public function getConnections(): array
    {
        return $this->repository->findAll();
    }

    /**
     * Gets a connection by its ID
     */
    public function getConnection(int $connectionId): ?Connection
    {
        return $this->repository->find($connectionId);
    }

    /**
     * Deletes a connection
     */
    public function deleteConnection(Connection $connection): void
    {
        $this->em->remove($connection);
        $this->em->flush();
    }

    /**
     * Creates a new connection from given request
     * @return Connection|null Returns created connection, null otherwise (invalid permissions, failed to create connection)
     */
    public function createConnection(CreateConnectionRequest $createConnectionRequest): ?Connection
    {
        $this->em->beginTransaction();
        $connection = new Connection(
            $createConnectionRequest->name,
            $createConnectionRequest->dbType->value,
            $createConnectionRequest->host,
            $createConnectionRequest->port,
            $createConnectionRequest->dbName,
            $createConnectionRequest->username,
            $createConnectionRequest->password,
            $createConnectionRequest->sid,
            $createConnectionRequest->readOnly,
            $createConnectionRequest->createScript,
            1       // TODO: get user id from auth token
        );
        //todo check if specified permission objects exists (from Configurations Microservice)
        $permissions = $this->permissionService->createPermissions($createConnectionRequest->permissions, $connection);
        if ($permissions === null) {
            $this->em->rollback();
            return null;
        }
        $connection->setPermissions($permissions);
        $this->em->persist($connection);
        $this->em->flush();
        $this->em->commit();
        return $connection;
    }

    /**
     * Updates a connection based on the given request
     */
    public function updateConnection(Connection $connection, UpdateConnectionRequest $updateConnectionRequest): ?Connection
    {
        $connection->setName($updateConnectionRequest->name)
            ->setDbType($updateConnectionRequest->dbType->value)
            ->setHost($updateConnectionRequest->host)
            ->setPort($updateConnectionRequest->port)
            ->setDbName($updateConnectionRequest->dbName)
            ->setUsername($updateConnectionRequest->username)
            ->setPassword($updateConnectionRequest->password)
            ->setSid($updateConnectionRequest->sid)
            ->setReadOnly($updateConnectionRequest->readOnly)
            ->setCreateScript($updateConnectionRequest->createScript)
            ->setOwnerId($updateConnectionRequest->ownerId);
        $this->em->flush();
        return $connection;
    }

    /**
     * Updates permissions for a given connection
     */
    public function updatePermissions(Connection $connection, MultiplePermissionsRequest $updatePermissionsRequest): ?Connection
    {
        $currPermissions = $connection->getPermissions();
        $newPermissions = $updatePermissionsRequest->permissions;

        // todo check if new permissions are valid (call configurations microservice api)

        // set permissions
        $result = $this->permissionService->mergePermissions($currPermissions, $newPermissions, $connection);
        $connection->setPermissions($result);

        // delete redundant permissions
        $redundantPermissions = $this->permissionService->getRedundantPermissions($currPermissions, $newPermissions);
        $this->permissionService->removePermissions($redundantPermissions);

        $this->em->persist($connection);
        $this->em->flush();
        return $connection;
    }

    /**
     * Executes given SQL query on specified connection and returns the data from database.
     * @param Connection $connection - connection on which the query should be run
     * @throws DBALException if there is an error in query which is being run (e.q. syntax error, table not found...)
     * @throws RuntimeException if the query is empty or other than select/TCL queries are called on read only connection
     */
    public function executeQuery(Connection $connection, string $query): array
    {
        if (trim($query) === "") {
            throw new RuntimeException("Missing query parameter.");
        }

        $newConnection = $this->connectToDatabase($connection);
        $parsedQueries = $this->parseQuery($connection->getDbType(), $query);

        $queryResults = [];
        foreach ($parsedQueries as $item) {
            $statementType = $item['statementSubType'];
            $statement = $item['query'];

            if (! in_array($statementType, array_merge([StatementSubTypeEnum::SELECT, StatementSubTypeEnum::START_TRANSACTION], StatementGroupEnum::TCL), true)
                && $connection->getReadOnly()) {
                throw new RuntimeException("Connection is read only. SELECT queries are permitted only.");
            }

            $columns = [];
            $resultRows = [];
            $result = $newConnection->executeQuery($statement);
            $rowsCount = $result->rowCount();

            if ($statementType === StatementSubTypeEnum::SELECT) {
                for ($i = 0; $i < ConnectionEnum::FETCH_MAX; $i++) {
                    $fetchedRow = $result->fetchAssociative();
                    if ($fetchedRow === false) {
                        break;
                    }

                    $resultRows[] = $fetchedRow;
                }

                $columns = $rowsCount !== 0 ? array_keys($resultRows[0]) : [];
            }

            $queryResults[] = new QueryResult($statementType, $statement, $rowsCount, $columns, $resultRows);
        }

        $newConnection->close();
        return $queryResults;
    }

    /**
     * Connects to specified database in the connection attribute and returns the connection.
     * @param Connection $connection - connection to which the DBAL\Connection should connect
     * @throws DBALException if the connection can not be established
     */
    public function connectToDatabase(Connection $connection): DBALConnection
    {
        $dbType = $connection->getDbType();
        $connectionParams = [
            'dbname' => $connection->getDbName(),
            'user' => $connection->getUsername(),
            'password' => $this->crypto->decrypt($connection->getPassword()),
            'host' => $connection->getHost(),
            'port' => $connection->getPort(),
            'servicename' => $dbType === ConnectionEnum::ORACLE ? $connection->getSid() : null,
            'driver' => ConnectionEnum::DBAL_DRIVERS[$dbType],
        ];

        $newConnection = DriverManager::getConnection($connectionParams);
        $newConnection->connect();

        return $newConnection;
    }

    /**
     * Method calls parser factory which returns the parser for the specified database and parses the query.
     * @param string $dbType - is a type DB connection, it is used to choose the external parser
     * @param string $query - DB query which is sent to a parser to parse
     * @return array of parsed queries with its statement subtypes and the query itself
     * @throws RuntimeException if the there is an error in parsing process, e.g. SyntaxError
     */
    public function parseQuery(string $dbType, string $query): array
    {
        $parser = $this->queryParserFactory->getParser($dbType);
        return $parser->parseQuery($query);
    }

    /**
     * Returns list of columns in each table of the database.
     * @return array of tables with columns
     * @throws Exception if schema creation fails
     */
    public function getSchema(DBALConnection $conn): array
    {
        $sm = $conn->createSchemaManager();
        $tables = $sm->createSchema()->getTables();

        $schema = [];
        // get columns for each table
        foreach ($tables as $table) {
            $tableName = $table->getName();
            if (!array_key_exists($tableName, $schema)) {
                $schema[$tableName] = [];
            }

            $columns = $table->getColumns();
            foreach ($columns as $column) {
                $schema[$tableName][] = $column->getName();
            }
        }

        return $schema;
    }

    /**
     * Calls RatAPI with given parameters and returns translated RA.
     * @param string $ra - Relation algebra string to translate
     * @param Connection $connection - Connection for translation
     * @return array - SQL translated from given RA query
     * @throws Exception - Error in translation (bad syntax...)
     */
    public function transformRa(string $ra, Connection $connection): array
    {
        $result = [];
        $httpClient = HttpClient::create();
        try {
            $dbalConnection = $this->connectToDatabase($connection);
            $schema = $this->getSchema($dbalConnection);
            $response = $httpClient->request(
                'PUT',
                'http://host.docker.internal:' . ConnectionEnum::ENDPOINT_PORTS[ConnectionEnum::RAT] . '/translate',
                [
                    'json' => [
                        'query' => $ra,
                        'type' => ConnectionEnum::RAT_DB_TYPES[$connection->getDbType()],
                        'scheme' => $schema,
                    ],
                ]
            );

            $response = json_decode($response->getContent(false), true);
            if (!is_array($response)) {
                throw new Exception('Translate response failed.');
            }
        } catch (\Throwable $ex) {
            throw new Exception($ex->getMessage());
        }

        if ($response['status'] !== 'success') {
            $errors = json_encode($response['messages']);
            if ($errors !== false) {
                throw new Exception($errors);
            } else {
                throw new Exception('Invalid RatAPI response.');
            }
        }
        $result['sql'] = $response['sql'];
        return $result;
    }
}
