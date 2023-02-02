<?php declare(strict_types=1);

namespace App\Model\Business\Enums;

final class StatementSubTypeEnum
{
    // DDL
    public const ALTER = 'ALTER';
    public const CREATE = 'CREATE';
    public const DROP = 'DROP';

    // DML
    public const DELETE = 'DELETE';
    public const INSERT = 'INSERT';
    public const SELECT = 'SELECT';
    public const UPDATE = 'UPDATE';
    public const MERGE = 'MERGE';
    public const WITH = 'WITH';

    // TCL
    public const BEGIN = 'BEGIN';
    public const COMMIT = 'COMMIT';
    public const ROLLBACK = 'ROLLBACK_SQL';
    public const SAVEPOINT = 'SAVEPOINT';
    public const START_TRANSACTION = 'START TRANSACTION';

    // Other
    public const UNKNOWN = 'UNKNOWN';

    // SQL*Plus
    public const COMMENT_PLUS = 'COMMENT_PLUS';
    public const EXECUTE = 'EXECUTE';
    public const PROMPT = 'PROMPT';
    public const SET_ECHO = 'SET_ECHO';
    public const SET_FEEDBACK = 'SET_FEEDBACK';
    public const SET_DEFINE = 'SET_DEFINE';
    public const SET_AUTOCOMMIT = 'SET_AUTOCOMMIT';
    public const SET_NULL = 'SET_NULL';
    public const SET_SERVER_OUTPUT = 'SET_SERVEROUTPUT';
    public const SET_TIMING = 'SET_TIMING';

    // MySQL
    public const SHOW_MYSQL = 'SHOW_MYSQL';
    public const DELIMITER = 'DELIMITER_MYSQL';
}
