package asciiArtApp.models.pixel

/**
 * Specific data model for ASCII pixel.
 * ASCII pixel can have integer value from 0 do 127.
 * @param symbol - char value of ASCII symbol
 */
case class AsciiPixel(symbol: Char) extends Pixel {
  require(symbol.toInt <= 127, "Invalid ascii value.")
}
