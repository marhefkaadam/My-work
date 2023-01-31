package asciiArtApp.models.pixelGrid

import asciiArtApp.models.pixel.AsciiPixel

/**
 * Specific data model for ASCII pixel grid. Consists only of ASCII pixels.
 * @param pixels - 2D sequence of sequence of ASCII pixels
 */
class AsciiPixelGrid(pixels: Seq[Seq[AsciiPixel]]) extends PixelGrid[AsciiPixel] {
  override def width: Int = if(pixels.nonEmpty) pixels.head.length else 0
  override def height: Int = if(pixels.nonEmpty && pixels.head.isEmpty) 0 else pixels.length
  override def getPixel(x: Int, y: Int): AsciiPixel = pixels(x)(y)
}
