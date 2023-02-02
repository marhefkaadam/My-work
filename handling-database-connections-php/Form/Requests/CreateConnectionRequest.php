<?php declare(strict_types=1);

namespace App\Model\Form\Requests;

use App\Model\Business\Enums\DatabaseTypeEnum;
use App\Model\Form\SinglePermissionType;
use Symfony\Component\Validator\Constraints as Assert;

class CreateConnectionRequest
{
    #[Assert\NotBlank]
    #[Assert\Length(max: 50)]
    public string $name;

    #[Assert\NotBlank]
    public DatabaseTypeEnum $dbType;

    #[Assert\NotBlank]
    #[Assert\Length(max: 255)]
    public string $host;

    #[Assert\NotBlank]
    #[Assert\Range(min: 0)]
    public int $port;

    #[Assert\NotBlank]
    #[Assert\Length(max: 100)]
    public string $dbName;

    #[Assert\NotBlank]
    #[Assert\Length(max: 100)]
    public string $username;

    #[Assert\NotBlank]
    #[Assert\Length(max: 100)]
    public string $password;

    #[Assert\Length(max: 20)]
    public string|null $sid = null;

    public bool $readOnly;

    #[Assert\Valid]
    /** @var SinglePermissionType[] */
    public array $permissions = [];

    public string|null $createScript = null;

    #[Assert\NotBlank]
    #[Assert\Range(min: 0)]
    public int $ownerId;
}
