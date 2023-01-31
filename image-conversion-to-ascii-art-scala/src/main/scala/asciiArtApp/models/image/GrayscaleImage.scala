package asciiArtApp.models.image

import asciiArtApp.models.pixel.GrayscalePixel
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid

/**
 * Specific data model for grayscale image. Consists of pixel grid made by grayscale pixels.
 * @param pixelGrid - 2D array of pixels in image
 */
class GrayscaleImage(pixelGrid: GrayscalePixelGrid) extends Image[GrayscalePixel] {
  override def width: Int = pixelGrid.width
  override def height: Int = pixelGrid.height
  override def getPixel(x: Int, y: Int): GrayscalePixel = pixelGrid.getPixel(x, y)
}
