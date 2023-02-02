<?php declare(strict_types=1);

namespace App\Model\Business\Enums;

// phpcs:disable
enum DatabaseTypeEnum : string
{
    case ORACLE = 'oracle';
    case POSTGRES = 'postgresql';
    case MYSQL = 'mysql';
    case SQLSRV = 'sqlsrv';
}
// phpcs:enable
