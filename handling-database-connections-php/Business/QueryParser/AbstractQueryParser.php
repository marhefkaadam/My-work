<?php declare(strict_types=1);

namespace App\Model\Business\QueryParser;

use Symfony\Contracts\HttpClient\HttpClientInterface;

abstract class AbstractQueryParser
{
    public function __construct(protected HttpClientInterface $client)
    {
    }

    abstract public function parseQuery(string $query): array;
}
