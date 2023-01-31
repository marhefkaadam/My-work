package converters.image.ascii

import asciiArtApp.models.image.{AsciiImage, GrayscaleImage}
import asciiArtApp.models.pixel.AsciiPixel
import asciiArtApp.models.pixelGrid.AsciiPixelGrid
import converters.image.ImageConverter

/**
 * Class converts image consisting of grayscale values to image of ASCII symbols.
 * Conversion is determined by conversion table.
 * Conversion table has ASCII values and logic for how to convert each pixel.
 * @param conversionTable - table used for converting specific grayscale values
 */
class GrayscaleToAsciiConverter(conversionTable: ConversionTable) extends ImageConverter[GrayscaleImage, AsciiImage] {
  /**
   * Method converts grayscale image to ASCII image using specified conversion table.
   * @param image - grayscale image to be converted
   * @return new converted ASCII image
   */
  override def convert(image: GrayscaleImage): AsciiImage = {
    var newPixelGrid = Seq[Seq[AsciiPixel]]()
    for(x <- 0 until image.height) {
      var newRow = Seq[AsciiPixel]()

      for(y <- 0 until image.width) {
        val charValue: Char = conversionTable.convert(image.getPixel(x, y).grayscaleValue)
        newRow = newRow.prepended(AsciiPixel(charValue))
      }
      newRow = newRow.reverse

      newPixelGrid = newPixelGrid.prepended(newRow)
    }
    newPixelGrid = newPixelGrid.reverse

    new AsciiImage(new AsciiPixelGrid(newPixelGrid))
  }
}
