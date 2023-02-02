<?php declare(strict_types=1);

namespace App\Model\Business\QueryParser;

use http\Exception\RuntimeException;

/**
 * Based on https://github.com/mysql/mysql-workbench/blob/6.3/modules/db.mysql.sqlparser/src/mysql_sql_facade.cpp#L82
 */
class MysqlStatementParser
{
    private string $script;

    private int $head = 0;
    private int $tail = 0;
    private int $end;
    private string $delimiter = ';';
    private bool $isInStatement = false;

    /** @var string[] */
    private array $statements = [];

    public function __construct(string $script)
    {
        $this->script = $script;
        $this->end = strlen($script);
        $this->run();
    }

    /**
     * Returns collection of parsed statements.
     * @return string[]
     */
    public function getStatements(): array
    {
        return $this->statements;
    }

    /**
     * Parse statements from script.
     */
    private function run(): void
    {
        while ($this->tail < $this->end) {
            switch ($this->getChar()) {
                case '/':
                    $this->processMultiLineComment();
                    break;
                case '-':
                    $this->processSingleLineComment();
                    break;
                case '#':
                    $this->processMysqlComment();
                    break;
                case '"':
                case '\'':
                case '`':
                    $this->processString();
                    break;
                case 'd':
                case 'D':
                    $this->processDelimiter();
                    break;
                default:
                    if ($this->getChar() > ' ') {
                        $this->isInStatement = true;
                    }
                    $this->tail++;
            }

            // Process statement end
            if ($this->tail !== $this->end) {
                $this->processStatementEnd();
            }
        }

        $this->head = $this->skipWhiteSpaces($this->head, $this->tail);
        if ($this->head < $this->tail) {
            $this->statements[] = substr($this->script, $this->head);
        }
    }

    /**
     * Process multi line comment.
     */
    private function processMultiLineComment(): void
    {
        // Check if next char is *
        $nextChar = $this->script[++$this->tail];
        if ($nextChar !== '*') {
            return;
        }

        // Check if comment contains hidden MySQL command
        $isHiddenCommand = ($this->script[++$this->tail] === '!');

        // Find comment end
        while (true) {
            $this->tail = $this->skipUntilCharacter($this->tail, '*');
            if ($this->tail === $this->end) {
                break;
            }

            if ($this->script[++$this->tail] === '/') {
                $this->tail++;
                break;
            }
        }

        // If we are not in statement then move head pointer after comment
        if (!$this->isInStatement) {
            // Also if comment contains hidden command then push it to stack to execute
            if ($isHiddenCommand) {
                $this->statements[] = substr($this->script, $this->head, $this->tail - $this->head);
            }
            $this->head = $this->tail;
        }
    }

    /**
     * Process single line comment.
     */
    private function processSingleLineComment(): void
    {
        // Check if substring is comment
        $substr = substr($this->script, $this->tail + 1, 2);
        $regMatchResult = preg_match("/-\\s/", $substr);
        if ($regMatchResult === false) {
            throw new RuntimeException("Incorrect regular expression while parsing Mysql query.");
        }

        if ($regMatchResult === 0) {
            $this->tail++;
            return;
        }

        // Move tail pointer to string end
        $this->tail = $this->skipUntilCharacter($this->tail, "\n");

        // If we are not in statement then move head pointer to comment end
        if (!$this->isInStatement) {
            $this->head = $this->tail;
        }
    }

    /**
     * Process MySQL single line comment.
     */
    private function processMysqlComment(): void
    {
        // Move pointer to end of line
        $this->tail = $this->skipUntilCharacter($this->tail, "\n");

        // If we are not in statement then move head pointer too
        if (!$this->isInStatement) {
            $this->head = $this->tail;
        }
    }

    /**
     * Process string and ID.
     */
    private function processString(): void
    {
        $this->isInStatement = true;

        // Get begin quote and move tail pointer
        $quote = $this->script[$this->tail++];

        // Find unescaped end quote
        while ($this->tail < $this->end) {
            // Get current char
            $currentChar = $this->getChar();

            // Move tail pointer
            $this->tail++;

            // Check if current char is end quote
            if ($currentChar === $quote) {
                return;
            }

            // If current char is escape then move tail pointer
            if ($currentChar === '\\') {
                $this->tail++;
            }
        }
    }

    /**
     * Process DELIMITER statement.
     */
    private function processDelimiter(): void
    {
        // Set statement mode since delete stmt also begins with d character
        $this->isInStatement = true;

        // Get previous char
        $previousChar = ($this->tail > 0) ? $this->getChar(-1) : 0;

        // Check if current char is part of identifier
        $regMatchResult = preg_match('[0-9a-zA-Z$_]', strval($previousChar));
        if ($regMatchResult === false) {
            throw new RuntimeException("Incorrect regular expression while parsing Mysql query.");
        }

        if ($previousChar !== 0 && (($previousChar >= 0x80) || $regMatchResult === 1)) {
            $this->tail++;
            return;
        }

        // Check if next characters contains word 'delimiter'
        $substr = substr($this->script, $this->tail, 9);
        if (strtoupper($substr) !== 'DELIMITER') {
            $this->tail++;
            return;
        }

        // Move pointer after delimiter keyword and check if next is space
        $pointer = $this->tail + 9;
        if ($this->script[$pointer] !== ' ') {
            $this->tail++;
            return;
        }

        // Move tail pointer after delimiter keyword and find line end
        $this->tail = $pointer;
        $pointer = $this->skipUntilCharacter($pointer, "\n");

        // Parse delimiter and push statement to buffer
        $this->delimiter = trim(substr($this->script, $this->tail, $pointer - $this->tail));
        $this->statements[] = substr($this->script, $this->head, $pointer - $this->head);

        // Move pointers
        $this->isInStatement = false;
        $this->tail = $pointer;
        $this->head = $this->tail;
    }

    /**
     * Process statement.
     */
    private function processStatementEnd(): void
    {
        // If current char is not a delimiter
        if ($this->getChar() !== $this->delimiter[0]) {
            return;
        }

        // Check if next characters contains delimiter
        $delimiterLength = \strlen($this->delimiter);
        $substr = substr($this->script, $this->tail, $delimiterLength);
        if ($substr !== $this->delimiter) {
            return;
        }

        // Parse statement
        $this->head = $this->skipWhiteSpaces($this->head, $this->tail);
        if ($this->head < $this->tail) {
            $this->statements[] = substr($this->script, $this->head, $this->tail - $this->head);
        }

        // Move pointers
        $this->tail += $delimiterLength;
        $this->head = $this->tail;
        $this->isInStatement = false;
    }

    /**
     * Returns one character from reader's current position.
     */
    private function getChar(int $offset = 0): string
    {
        return $this->script[$this->tail + $offset];
    }

    /**
     * Skip characters until given character is found.
     * @param int $begin index where search begins
     * @param string $char character to found
     * @return int position of found character
     */
    private function skipUntilCharacter(int $begin, string $char): int
    {
        $characterPos = strpos($this->script, $char, $begin);
        if ($characterPos === false) {
            return $this->end;
        }

        return $characterPos;
    }

    /**
     * Skip all whitespace characters
     * @param int $begin index where process begins
     * @param int $end end index
     * @return int position of first non-space character
     */
    private function skipWhiteSpaces(int $begin, int $end): int
    {
        while ($begin < $end && $this->script[$begin] <= ' ') {
            $begin++;
        }
        return $begin;
    }
}
