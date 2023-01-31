package asciiArtApp.models.pixel

/**
 * Specific data model for RGB pixel. Consists of value R, G, B which represents the amount of colors in each pixel.
 * R, G or B values can be from 0 to 255.
 * @param r - value red
 * @param g - value green
 * @param b - value blue
 */
case class RGBPixel(r: Int, g: Int, b: Int) extends Pixel {
  require(r >= 0 && r <= 255, "Invalid r value of RGB.")
  require(g >= 0 && g <= 255, "Invalid g value of RGB.")
  require(b >= 0 && b <= 255, "Invalid b value of RGB.")
}
