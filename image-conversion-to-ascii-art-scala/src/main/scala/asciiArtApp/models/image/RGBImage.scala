package asciiArtApp.models.image

import asciiArtApp.models.pixel.RGBPixel
import asciiArtApp.models.pixelGrid.RGBPixelGrid

/**
 * Specific data model for RGB image. Consists of pixel grid made by RGB pixels.
 * @param pixelGrid - 2D array of pixels in image
 */
class RGBImage(pixelGrid: RGBPixelGrid) extends Image[RGBPixel] {
  override def width: Int = pixelGrid.width
  override def height: Int = pixelGrid.height
  override def getPixel(x: Int, y: Int): RGBPixel = pixelGrid.getPixel(x, y)
}
