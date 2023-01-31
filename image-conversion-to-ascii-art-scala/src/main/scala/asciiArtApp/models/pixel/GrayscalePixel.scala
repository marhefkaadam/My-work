package asciiArtApp.models.pixel

/**
 * Specific data model for grayscale pixel. Consists of double value which represents grayscale value.
 * Grayscale value can be from 0 to 255 and it represents the amount of black in RGB pixel.
 * @param grayscaleValue - value of pixel
 */
case class GrayscalePixel(grayscaleValue: Double) extends Pixel {
  require(grayscaleValue >= 0 && grayscaleValue <= 255, "Invalid grayscale value.")
}
