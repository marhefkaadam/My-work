package asciiArtApp.models.pixelGrid

import asciiArtApp.models.pixel.Pixel

/**
 * Data model for representing 2D grid of pixels which are inside of every image.
 * @tparam T - type of pixels which are in pixel grid, upper bound to Pixel
 */
trait PixelGrid[+T <: Pixel] {
  def width: Int
  def height: Int
  def getPixel(x: Int, y: Int): T
}
