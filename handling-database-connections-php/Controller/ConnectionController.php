<?php declare(strict_types=1);

namespace App\Controller;

use App\Model\Business\Enums\StatementTypeEnum;
use App\Model\Business\Formatter\CompareResultFormatter;
use App\Model\Business\Formatter\ConnectionFormatter;
use App\Model\Business\Formatter\ConnectionPreviewFormatter;
use App\Model\Business\Formatter\DiagramFormatter;
use App\Model\Business\Formatter\QueryResultFormatter;
use App\Model\Business\Service\ConnectionService;
use App\Model\Form\CompareQueriesType;
use App\Model\Form\CreateConnectionType;
use App\Model\Form\MultiplePermissionsType;
use App\Model\Form\QueryType;
use App\Model\Form\Requests\CompareQueriesRequest;
use App\Model\Form\Requests\CreateConnectionRequest;
use App\Model\Form\Requests\MultiplePermissionsRequest;
use App\Model\Form\Requests\QueryRequest;
use App\Model\Form\Requests\TransformRaRequest;
use App\Model\Form\Requests\UpdateConnectionRequest;
use App\Model\Form\TransformRaType;
use App\Model\Form\UpdateConnectionType;
use Dbs\UtilsBundle\Controller\AbstractController as AbstractController;
use Doctrine\DBAL\ConnectionException;
use Doctrine\DBAL\Exception as DBALException;
use FOS\RestBundle\Controller\Annotations as Rest;
use FOS\RestBundle\View\View;
use Symfony\Component\HttpFoundation\Request;
use Throwable;

class ConnectionController extends AbstractController
{

    public function __construct(
        private ConnectionService $connectionService,
        private ConnectionFormatter $connectionFormatter,
        private ConnectionPreviewFormatter $connectionPreviewFormatter,
        private QueryResultFormatter $queryResultFormatter,
        private CompareResultFormatter $compareResultFormatter,
        private DiagramFormatter $diagramFormatter
    )
    {
    }

    #[Rest\Get(
        path: "/connections",
        name: "getConnections"
    )]
    public function getConnections(): View
    {
        //todo check users scope (show only connections visible to the current user)
        $connections = $this->connectionService->getConnections();
        $results = $this->connectionPreviewFormatter->formatMany($connections);
        return $this->sendSuccess($results);
    }

    #[Rest\Get(
        path: "/connections/{connectionId}",
        name: "getConnection",
        requirements: ["connectionId" => "\d+"]
    )]
    public function getConnection(int $connectionId): View
    {
        //todo check users scope
        $connection = $this->connectionService->getConnection($connectionId);
        if ($connection === null) {
            return $this->sendNotFound();
        }
        $result = $this->connectionFormatter->format($connection);
        return $this->sendSuccess($result);
    }

    #[Rest\Delete(
        path: "/connections/{connectionId}",
        name: "deleteConnection",
        requirements: ["connectionId" => "\d+"]
    )]
    public function deleteConnection(int $connectionId): View
    {
        //todo check owner (only owner can delete connection) and usage (if the connection is being used)
        $connection = $this->connectionService->getConnection($connectionId);
        if ($connection === null) {
            return $this->sendNotFound();
        }
        $this->connectionService->deleteConnection($connection);
        return $this->sendNoContent();
    }

    #[Rest\Post(
        path: "/connections",
        name: "createConnection"
    )]
    public function createConnection(Request $request): View
    {
        $formRequest = new CreateConnectionRequest();
        $form = $this->createForm(CreateConnectionType::class, $formRequest);
        $form->submit($request->request->all());
        if (!$form->isValid()) {
            return $this->sendFormErrors($form);
        }
        try {
            $connection = $this->connectionService->createConnection($formRequest);
        } catch (Throwable $ex) {
            return $this->sendConflict([
                'error' => $ex->getMessage(),
            ]);
        }
        if ($connection === null) {
            return $this->sendConflict();
        }
        return $this->sendCreated('getConnection', [
           'connectionId' => $connection->getId(),
        ]);
    }

    #[Rest\Patch(
        path: "/connections/{connectionId}",
        name: "updateConnection",
        requirements: ["connectionId" => "\d+"]
    )]
    public function updateConnection(Request $request, int $connectionId): View
    {
        //todo check if current user is the owner
        $connection = $this->connectionService->getConnection($connectionId);
        if ($connection === null) {
            return $this->sendNotFound();
        }
        $formRequest = new UpdateConnectionRequest($connection);
        $form = $this->createForm(UpdateConnectionType::class, $formRequest);
        $form->submit($request->request->all(), false);
        if (!$form->isValid()) {
            return $this->sendFormErrors($form);
        }

        try {
            $this->connectionService->updateConnection($connection, $formRequest);
        } catch (Throwable $ex) {
            return $this->sendConflict([
                'error' => $ex->getMessage(),
            ]);
        }

        return $this->sendNoContent();
    }

    #[Rest\Put(
        path: "/connections/{connectionId}/permissions",
        name: "updatePermissions",
        requirements: ["connectionId" => "\d+"]
    )]
    public function updatePermissions(Request $request, int $connectionId): View
    {
        //todo check if current user is the owner
        $connection = $this->connectionService->getConnection($connectionId);
        if ($connection === null) {
            return $this->sendNotFound();
        }

        $formRequest = new MultiplePermissionsRequest();
        $form = $this->createForm(MultiplePermissionsType::class, $formRequest);
        $form->submit($request->request->all());
        if (!$form->isValid()) {
            return $this->sendFormErrors($form);
        }

        $connection = $this->connectionService->updatePermissions($connection, $formRequest);

        if ($connection === null) {
            return $this->sendConflict();
        }
        return $this->sendNoContent();
    }

    #[Rest\Post(
        path: '/connections/{connectionId}/execute-query',
        name: 'executeQuery',
        requirements: ["connectionId" => "\d+"]
    )]
    public function executeQuery(Request $request, int $connectionId): View
    {
        //TODO authorization and required scope detection
        $formRequest = new QueryRequest();

        $form = $this->createForm(QueryType::class, $formRequest);
        $form->submit($request->request->all());

        if (!$form->isValid()) {
            return $this->sendFormErrors($form);
        }

        $connection = $this->connectionService->getConnection($connectionId);

        if ($connection === null) {
            return $this->sendNotFound();
        }

        try {
            if ($formRequest->queryType->value === StatementTypeEnum::RA) {
                $ret = $this->connectionService->transformRa($formRequest->query, $connection);
                $query = $ret[StatementTypeEnum::SQL];
            } else {
                $query = $formRequest->query;
            }

            $result = $this->connectionService->executeQuery($connection, $query);
            return $this->sendSuccess($this->queryResultFormatter->formatMany($result));
        } catch (ConnectionException $ex) {
            return $this->sendBadRequest($ex->getMessage());
        } catch (Throwable $ex) {
            return $this->sendConflict([
                'error' => $ex->getMessage(),
            ]);
        }
    }

    #[Rest\Get(
        path: '/connections/{connectionId}/schema',
        name: 'getSchema',
        requirements: ["connectionId" => "\d+"]
    )]
    public function getSchema(int $connectionId): View
    {
        $connection = $this->connectionService->getConnection($connectionId);
        if ($connection === null) {
            return $this->sendNotFound();
        }

        try {
            $dbalConnection = $this->connectionService->connectToDatabase($connection);
            $schema = $this->connectionService->getSchema($dbalConnection);
            return $this->sendSuccess($schema);
        } catch (DBALException $exception) {
            return $this->sendConflict([
                'error' => $exception->getMessage(),
            ]);
        }
    }

    #[Rest\Get(
        path: '/connections/{connectionId}/diagram',
        name: 'getDiagram',
        requirements: ["connectionId" => "\d+"]
    )]
    public function getDiagram(int $connectionId): View
    {
        $connection = $this->connectionService->getConnection($connectionId);
        if ($connection === null) {
            return $this->sendNotFound();
        }

        try {
            $dbalConnection = $this->connectionService->connectToDatabase($connection);
            $diagram = $this->connectionService->getDiagram($dbalConnection);
            return $this->sendSuccess(
                $this->diagramFormatter->format($diagram)
            );
        } catch (DBALException $exception) {
            return $this->sendConflict([
                'error' => $exception->getMessage(),
            ]);
        }
    }

    #[Rest\Post(
        path: '/connections/{connectionId}/transform',
        name: 'transformRa',
        requirements: ["connectionId" => "\d+"]
    )]
    public function transformRa(Request $request, int $connectionId): View
    {
        $formRequest = new TransformRaRequest();
        $form = $this->createForm(TransformRaType::class, $formRequest);
        $form->submit($request->request->all());
        if (!$form->isValid()) {
            return $this->sendFormErrors($form);
        }

        $connection = $this->connectionService->getConnection($connectionId);
        if ($connection === null) {
            return $this->sendNotFound();
        }

        try {
            $result = $this->connectionService->transformRa($formRequest->ra, $connection);
            return $this->sendSuccess($result);
        } catch (DBALException $exception) {
            return $this->sendConflict([
                'error' => $exception->getMessage(),
            ]);
        } catch (Throwable $ex) {
            return $this->sendConflict([
                'error' => $ex->getMessage(),
            ]);
        }
    }

    #[Rest\Post(
        path: '/connections/{connectionId}/compare-queries',
        name: 'compareQueries'
    )]
    public function compareQueries(Request $request, int $connectionId): View
    {
        //TODO authorization and required scope detection
        $formRequest = new CompareQueriesRequest();

        $form = $this->createForm(CompareQueriesType::class, $formRequest);
        $form->submit($request->request->all());

        if (!$form->isValid()) {
            return $this->sendFormErrors($form);
        }

        $connection = $this->connectionService->getConnection($connectionId);
        if ($connection === null) {
            return $this->sendNotFound();
        }

        try {
            $result = $this->connectionService->compareQueries($formRequest, $connection);
            return $this->sendSuccess(
                $this->compareResultFormatter->format($result)
            );
        } catch (Throwable $ex) {
            return $this->sendConflict([
                'error' => $ex->getMessage(),
            ]);
        }
    }
}
