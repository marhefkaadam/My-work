<?php declare(strict_types=1);

namespace App\Model\Business\Enums;

final class StatementTypeEnum
{
    public const UNKNOWN = 'UNKNOWN';
    public const SQL = 'sql';
    public const RA = 'ra';
    public const PLSQL = 'PLSQL';
    public const SQLPLUS = 'SQLPLUS';
    public const COMMENT = 'COMMENT';
    public const EMPTYLINE = 'EMPTYLINE';
}
