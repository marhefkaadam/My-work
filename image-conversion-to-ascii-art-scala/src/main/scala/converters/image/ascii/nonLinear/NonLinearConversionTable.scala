package converters.image.ascii.nonLinear

import converters.image.ascii.ConversionTable

/**
 * Trait for specifying non linear conversion table used when converting grayscale values to ascii symbol.
 * Non linear conversion table is when 255 greyscale values are divided un-equally between a set of ASCII characters.
 */
trait NonLinearConversionTable extends ConversionTable {
  /**
   * Method converts grayscale value to specified ASCII value where to logic of transformation is specified in table.
   * @param grayscaleValue - value to be converted
   * @return ASCII symbol which was converted from grayscale value
   */
  def convert(grayscaleValue: Double): Char
}