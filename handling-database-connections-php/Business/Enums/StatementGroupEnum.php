<?php declare(strict_types=1);

namespace App\Model\Business\Enums;

final class StatementGroupEnum
{
    /** Data modification language */
    public const DML = [
        StatementSubTypeEnum::SELECT,
        StatementSubTypeEnum::INSERT,
        StatementSubTypeEnum::UPDATE,
        StatementSubTypeEnum::DELETE,
        StatementSubTypeEnum::MERGE,
        StatementSubTypeEnum::WITH,
    ];

    /** Transaction control language */
    public const TCL = [
        StatementSubTypeEnum::BEGIN,
        StatementSubTypeEnum::COMMIT,
        StatementSubTypeEnum::ROLLBACK,
        StatementSubTypeEnum::SAVEPOINT,
    ];

    /** All statements */
    public const ALL = [
        StatementTypeEnum::SQL,
        StatementTypeEnum::PLSQL,
        StatementTypeEnum::SQLPLUS,
        StatementTypeEnum::UNKNOWN,
    ];
}
