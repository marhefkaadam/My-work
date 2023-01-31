package converters.image.ascii

/**
 * Trait for specifying conversion table used when converting grayscale values to ascii symbol.
 */
trait ConversionTable {
  /**
   * Method converts grayscale value to Char ascii symbol by defined behaviour.
   * @param grayscaleValue - value to be converted
   * @return ascii symbol which was converted from grayscale value
   */
  def convert(grayscaleValue: Double): Char
}
