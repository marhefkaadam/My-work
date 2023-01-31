package converters.image.ascii.linear

import converters.image.ascii.ConversionTable

/**
 * Trait for specifying linear conversion table used when converting grayscale values to ASCII symbol.
 * A linear conversion table is a table where
 * the range of 255 greyscale values is equally divided between a set of ASCII characters.
 */
trait LinearConversionTable extends ConversionTable {
  def table: Seq[Char]

  /**
   * Method converts grayscale value to ASCII symbol by computing which symbol from conversion table should be used.
   * Method computes index by creating module of table (255/table length) and then dividing grayscale value by module.
   * If the index to table is table length we use the last relevant index which is table length - 1.
   * @param grayscaleValue - value to be converted
   * @return ASCII symbol which was converted from grayscale value
   */
  def convert(grayscaleValue: Double): Char = {
    var characterIndex = Math.min(grayscaleValue / (255.0 / table.length), table.length - 1).toInt
    if(characterIndex < 0)
      characterIndex = 0
    table(characterIndex)
  }
}
