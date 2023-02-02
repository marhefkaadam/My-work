<?php declare(strict_types=1);

namespace App\Model\Form;

use App\Model\Business\Enums\QueryTypeEnum;
use App\Model\Form\Requests\QueryRequest;
use Symfony\Component\Form\Extension\Core\Type\EnumType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;

class QueryType extends AbstractFormType
{
    /** @inheritDoc */
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder->add('query_type', EnumType::class, [
            'class' => QueryTypeEnum::class,
            'property_path' => 'queryType',
        ]);
        $builder->add('query', TextType::class);
    }

    /** @inheritDoc */
    protected function getEntityName(): string
    {
        return QueryRequest::class;
    }
}
