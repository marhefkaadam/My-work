package asciiArtApp.models.image

import asciiArtApp.models.pixel.Pixel

/**
 * Data model for representing image.
 * @tparam T - type of pixels of image, upper bound to Pixel
 */
trait Image[+T <: Pixel] {
  def width: Int
  def height: Int
  def getPixel(x: Int, y: Int): T
}
