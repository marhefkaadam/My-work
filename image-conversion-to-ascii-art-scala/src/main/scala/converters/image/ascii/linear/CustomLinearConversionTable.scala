package converters.image.ascii.linear

/**
 * Class is used for storing user created linear conversion tables and using them for conversion of image.
 * @param customTable - user defined linear conversion table
 */
class CustomLinearConversionTable(customTable: Seq[Char]) extends LinearConversionTable {
  require(customTable.nonEmpty, "Custom conversion table must have some symbols.")

  override def table: Seq[Char] = customTable
}
