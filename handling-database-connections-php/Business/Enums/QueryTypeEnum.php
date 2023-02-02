<?php declare(strict_types=1);

namespace App\Model\Business\Enums;

// phpcs:disable
enum QueryTypeEnum: string
{
    case SQL = 'sql';
    case RA = 'ra';
}
// phpcs:enable
