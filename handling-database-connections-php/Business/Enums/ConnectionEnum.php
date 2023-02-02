<?php declare(strict_types=1);

namespace App\Model\Business\Enums;

final class ConnectionEnum
{
    /** Oracle database identifier. */
    public const ORACLE = 'oracle';
    /** PostgreSQL database identifier. */
    public const POSTGRES = 'postgresql';
    /** MySQL database identifier. */
    public const MYSQL = 'mysql';
    /** Microsoft SQL Server database identifier. */
    public const SQLSRV = 'sqlsrv';
    /** Relational algebra translator identifier. */
    public const RAT = 'rat';
    /** Maximum of rows fetched for the execute-query response. */
    public const FETCH_MAX = 100;

    /** Drivers used to create new connection using PHP DBAL connection. */
    public const DBAL_DRIVERS = [
        self::POSTGRES => 'pdo_pgsql',
        self::ORACLE => 'oci8',
        self::MYSQL => 'pdo_mysql',
        self::SQLSRV => 'pdo_sqlsrv',
    ];

    /** Ports for docker container endpoints used for parsing SQL scripts or relational algebra translator. */
    public const ENDPOINT_PORTS = [
        self::RAT => 8084,
        self::POSTGRES => 8085,
        self::ORACLE => 8086,
        self::SQLSRV => 8087,
    ];

    /** Database type names for RatAPI. */
    public const RAT_DB_TYPES = [
        self::ORACLE => 'oracle',
        self::POSTGRES => 'postgresql',
        self::MYSQL => 'mariadb',
    ];

    /**
     * Returns an array containing constants of this enum type.
     * @return array map of [constant => constant]
     */
    public static function values(): array
    {
        return [
            self::ORACLE => self::ORACLE,
            self::POSTGRES => self::POSTGRES,
            self::MYSQL => self::MYSQL,
            self::SQLSRV => self::SQLSRV,
        ];
    }

    /**
     * Returns an array containing constants of this enum type with pretty names.
     * @return array map of [constant => pretty name]
     */
    public static function valuesPretty(): array
    {
        return [
            self::ORACLE => 'Oracle',
            self::POSTGRES => 'PostgreSQL',
            self::MYSQL => 'MySQL/MariaDB',
            self::SQLSRV => 'SQL Server',
        ];
    }
}
