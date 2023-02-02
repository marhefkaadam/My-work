<?php declare(strict_types=1);

namespace App\Model\Form\Requests;

use Symfony\Component\Validator\Constraints as Assert;

class TransformRaRequest
{
    #[Assert\NotBlank]
    public string $ra;
}
