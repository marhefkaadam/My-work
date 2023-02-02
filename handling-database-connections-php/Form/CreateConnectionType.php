<?php declare(strict_types=1);

namespace App\Model\Form;

use App\Model\Business\Enums\DatabaseTypeEnum;
use App\Model\Form\Requests\CreateConnectionRequest;
use Dbs\UtilsBundle\Form\Fields\CypherTextType;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\Extension\Core\Type\CollectionType;
use Symfony\Component\Form\Extension\Core\Type\EnumType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;

class CreateConnectionType extends AbstractFormType
{

    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('name', TextType::class)
            ->add('db_type', EnumType::class, [
                'class' => DatabaseTypeEnum::class,
                'property_path' => 'dbType',
            ])
            ->add('host', TextType::class)
            ->add('port', IntegerType::class)
            ->add('db_name', TextType::class, [
                'property_path' => 'dbName',
            ])
            ->add('username', TextType::class)
            ->add('password', CypherTextType::class)
            ->add('sid', TextType::class)
            ->add('read_only', CheckboxType::class, [
                'property_path' => 'readOnly',
            ])
            ->add('permissions', CollectionType::class, [
                'entry_type' => SinglePermissionType::class,
                'allow_add' => true,
                'allow_delete' => true,
            ])
            ->add('create_script', TextType::class, [
                'property_path' => 'createScript',
            ])
            ->add('owner_id', IntegerType::class, [
                'property_path' => 'ownerId',
            ]);
    }

    protected function getEntityName(): string
    {
        return CreateConnectionRequest::class;
    }
}
