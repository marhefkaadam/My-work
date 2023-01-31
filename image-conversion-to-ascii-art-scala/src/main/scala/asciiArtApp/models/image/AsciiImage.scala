package asciiArtApp.models.image

import asciiArtApp.models.pixel.AsciiPixel
import asciiArtApp.models.pixelGrid.AsciiPixelGrid

/**
 * Specific data model for ASCII image. Consists of pixel grid made by ASCII pixels.
 * @param pixelGrid - 2D array of pixels in image
 */
class AsciiImage(pixelGrid: AsciiPixelGrid) extends Image[AsciiPixel] {
  override def width: Int = pixelGrid.width
  override def height: Int = pixelGrid.height
  override def getPixel(x: Int, y: Int): AsciiPixel = pixelGrid.getPixel(x, y)
}
