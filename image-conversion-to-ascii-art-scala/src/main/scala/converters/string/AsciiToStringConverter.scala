package converters.string

import asciiArtApp.models.image.AsciiImage
import converters.Converter

/**
 * Class converts image consisting of ASCII symbols to string.
 */
class AsciiToStringConverter extends Converter[AsciiImage, String] {
  /**
   * Method converts ASCII image to string by going through each pixel and inputting it's value to string.
   * Each row of ASCII image is a new line in the string.
   * @param image - ASCII image to be converted
   * @return string created from ASCII symbol of image's pixels
   */
  override def convert(image: AsciiImage): String = {
    var output: String = ""
    for(x <- 0 until image.height) {
      for(y <- 0 until image.width) {
        output += image.getPixel(x, y).symbol
      }

      output += '\n'
    }

    output
  }
}

