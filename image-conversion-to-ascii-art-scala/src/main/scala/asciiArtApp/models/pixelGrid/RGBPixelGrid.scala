package asciiArtApp.models.pixelGrid

import asciiArtApp.models.pixel.RGBPixel

/**
 * Specific data model for RGB pixel grid. Consists only of RGB pixels.
 * @param pixels - 2D sequence of sequence of RGB pixels
 */
class RGBPixelGrid(pixels: Seq[Seq[RGBPixel]]) extends PixelGrid[RGBPixel] {
  override def width: Int = if(pixels.nonEmpty) pixels.head.length else 0
  override def height: Int = if(pixels.nonEmpty && pixels.head.isEmpty) 0 else pixels.length
  override def getPixel(x: Int, y: Int): RGBPixel = pixels(x)(y)
}
