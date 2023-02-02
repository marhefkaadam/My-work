<?php declare(strict_types=1);

namespace App\Model\Business\QueryParser;

use App\Model\Business\Enums\StatementSubTypeEnum;

class MysqlQueryParser extends AbstractQueryParser
{
    public function parseQuery(string $query): array
    {
        $parser = new MysqlStatementParser($query);
        $queries = [];
        foreach ($parser->getStatements() as $statement) {
            $trimStatement = trim($statement);
            $length = strpos($trimStatement, ' ');
            $firstKeyword = strtoupper(substr($trimStatement, 0, $length === false ? null : $length));

            $type = match ($firstKeyword) {
                // DDL:
                'CREATE' => StatementSubTypeEnum::CREATE,
                'DROP' => StatementSubTypeEnum::DROP,
                'ALTER' => StatementSubTypeEnum::ALTER,
                // DML:
                'INSERT' => StatementSubTypeEnum::INSERT,
                'UPDATE' => StatementSubTypeEnum::UPDATE,
                'DELETE' => StatementSubTypeEnum::DELETE,
                'SELECT' => StatementSubTypeEnum::SELECT,
                'WITH' => StatementSubTypeEnum::WITH,
                'MERGE' => StatementSubTypeEnum::MERGE,
                // TCL:
                'START' => StatementSubTypeEnum::START_TRANSACTION,
                'BEGIN' => StatementSubTypeEnum::BEGIN,
                'COMMIT' => StatementSubTypeEnum::COMMIT,
                'ROLLBACK' => StatementSubTypeEnum::ROLLBACK,
                'SAVEPOINT' => StatementSubTypeEnum::SAVEPOINT,
                // Other:
                'DELIMITER' => StatementSubTypeEnum::DELIMITER,
                'SHOW' => StatementSubTypeEnum::SHOW_MYSQL,
                default => StatementSubTypeEnum::UNKNOWN,
            };

            $queries[] = [
                'statementSubType' => $type,
                'query' => $statement,
            ];
        }

        return $queries;
    }
}
