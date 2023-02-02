<?php declare(strict_types=1);

namespace App\Model\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\OptionsResolver\OptionsResolver;

abstract class AbstractFormType extends AbstractType
{
    /**
     * Gets fully qualified class name to associated request entity.
     */
    abstract protected function getEntityName(): string;

    /** @inheritdoc */
    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => $this->getEntityName(),
            'allow_extra_fields' => true,
            'csrf_protection' => false,
        ]);
    }
}
