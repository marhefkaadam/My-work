<?php declare(strict_types=1);

namespace App\Model\Form\Requests;

use App\Model\Business\Entity\Connection;
use App\Model\Business\Enums\DatabaseTypeEnum;
use Symfony\Component\Validator\Constraints as Assert;

class UpdateConnectionRequest
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

    public string|null $createScript = null;

    #[Assert\NotBlank]
    #[Assert\Range(min: 0)]
    public int $ownerId;

    public function __construct(Connection $connection)
    {
        $this->name = $connection->getName();
        $this->dbType = DatabaseTypeEnum::from($connection->getDbType());
        $this->host = $connection->getHost();
        $this->port = $connection->getPort();
        $this->dbName = $connection->getDbName();
        $this->username = $connection->getUsername();
        $this->password = $connection->getPassword();
        $this->sid = $connection->getSid();
        $this->readOnly = $connection->getReadOnly();
        $this->createScript = $connection->getCreateScript();
        $this->ownerId = $connection->getOwnerId();
    }
}
