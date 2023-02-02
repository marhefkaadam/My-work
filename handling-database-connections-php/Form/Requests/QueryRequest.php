<?php declare(strict_types=1);

namespace App\Model\Form\Requests;

use App\Model\Business\Enums\QueryTypeEnum;
use Symfony\Component\Validator\Constraints as Assert;

class QueryRequest
{
    #[Assert\NotBlank]
    public QueryTypeEnum $queryType;

    #[Assert\NotBlank]
    public string $query;
}
