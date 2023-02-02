<?php declare(strict_types=1);

namespace App\Model\Form;

use App\Model\Form\Requests\TransformRaRequest;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;

class TransformRaType extends AbstractFormType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder->add('ra', TextType::class);
    }

    protected function getEntityName(): string
    {
        return TransformRaRequest::class;
    }
}
