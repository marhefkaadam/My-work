<?php declare(strict_types=1);

namespace App\Model\Business\QueryParser;

use App\Model\Business\Enums\ConnectionEnum;
use RuntimeException;
use Symfony\Component\HttpFoundation\Response;
use Throwable;

class SqlsrvQueryParser extends AbstractQueryParser
{
    public function parseQuery(string $query): array
    {
        // Send request and parse response
        try {
            $response = $this->client->request(
                'POST',
                'http://host.docker.internal:' . ConnectionEnum::ENDPOINT_PORTS[ConnectionEnum::SQLSRV] . '/parse/script',
                [
                    'headers' => [
                        'Content-Type' => 'application/x-www-form-urlencoded',
                    ],
                    'body' => [
                        'query' => $query,
                    ],
                ]
            );

            if ($response->getStatusCode() !== Response::HTTP_OK) {
                throw new RuntimeException('Query parsing failed.');
            }

            $body = $response->toArray();
        } catch (Throwable $ex) {
            throw new RuntimeException($ex->getMessage());
        }

        // Check if response is valid
        if ($body['status'] !== 'SUCCESS') {
            throw new RuntimeException($body['message']);
        }

        // Return parsed queries
        return $body['queries'];
    }
}
