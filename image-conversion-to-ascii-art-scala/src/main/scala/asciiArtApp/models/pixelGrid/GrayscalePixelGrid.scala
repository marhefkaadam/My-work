package asciiArtApp.models.pixelGrid

import asciiArtApp.models.pixel.GrayscalePixel

/**
 * Specific data model for grayscale pixel grid. Consists only of grayscale pixels.
 * @param pixels - 2D sequence of sequence of grayscale pixels
 */
class GrayscalePixelGrid(pixels: Seq[Seq[GrayscalePixel]]) extends PixelGrid[GrayscalePixel] {
  override def width: Int = if(pixels.nonEmpty) pixels.head.length else 0
  override def height: Int = if(pixels.nonEmpty && pixels.head.isEmpty) 0 else pixels.length
  override def getPixel(x: Int, y: Int): GrayscalePixel = pixels(x)(y)
}
