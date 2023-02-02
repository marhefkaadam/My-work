<?php declare(strict_types=1);

namespace App\Model\Business\QueryParser;

use RuntimeException;

class QueryParserFactory
{
    public function __construct(private array $parsers)
    {
    }

    public function getParser(string $dbType): AbstractQueryParser
    {
        if (!isset($this->parsers[$dbType])) {
            throw new RuntimeException('Parser not found for dbType: ' . $dbType);
        }

        return $this->parsers[$dbType];
    }
}
