package converters.image.ascii.nonLinear

/**
 * Class for custom implementation of non linear conversion table for conversion of images.
 */
class CrazyNonLinearConversionTable extends NonLinearConversionTable {
  /**
   * Method convers grayscale value to ASCII symbol by specified logic.
   * @param grayscaleValue - value to be converted
   * @return ASCII symbol which was converted from grayscale value
   */
  override def convert(grayscaleValue: Double): Char = {
    if(grayscaleValue > 0 && grayscaleValue < 20) {
      '&'
    } else if(grayscaleValue >= 20 && grayscaleValue < 50 && grayscaleValue % 2 == 0) {
      '.'
    } else if(grayscaleValue >= 50 && grayscaleValue < 120 && grayscaleValue % 3 == 1) {
      'A'
    } else if(grayscaleValue >= 100 && grayscaleValue < 200 && grayscaleValue % 10 == 2) {
      'X'
    } else if(grayscaleValue >= 150 && grayscaleValue < 170) {
      'S'
    } else if(grayscaleValue >= 170 && grayscaleValue < 174) {
      '{'
    } else if(grayscaleValue >= 181 && grayscaleValue < 250) {
      '!'
    } else {
      ':'
    }
  }
}
