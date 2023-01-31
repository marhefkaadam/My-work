package filters.image.specific.brightness

import asciiArtApp.models.image.GrayscaleImage
import asciiArtApp.models.pixel.GrayscalePixel
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid
import filters.image.ImageFilter

/**
 * Class filters grayscale image by adjusting it's each pixel value by value specified in constructor.
 * @param brightness - value to increase each pixel by
 */
class BrightnessFilter(brightness: Double) extends ImageFilter[GrayscaleImage] {
  /**
   * Method takes grayscale image and adjusts each pixel grayscale value by value specified in constructor.
   * Value can be positive, negative or zero.
   * @param image - grayscale image to be filtered
   * @return filtered grayscale image
   */
  override def filter(image: GrayscaleImage): GrayscaleImage = {
    var newPixelGrid = Seq[Seq[GrayscalePixel]]()
    for(x <- 0 until image.height) {
      var newRow = Seq[GrayscalePixel]()

      for(y <- 0 until image.width) {
        newRow = newRow.prepended(adjustBrightness(image.getPixel(x, y)))
      }
      newRow = newRow.reverse

      newPixelGrid = newPixelGrid.prepended(newRow)
    }
    newPixelGrid = newPixelGrid.reverse

    new GrayscaleImage(new GrayscalePixelGrid(newPixelGrid))
  }

  /**
   * Method takes grayscale pixel and adjusts it's value by brightness value in constructor.
   * Maximum grayscale value of new pixel is 255.
   * Minimum grayscale value of new pixel is 0.
   * @param src - pixel to be adjusted
   * @return
   */
  private def adjustBrightness(src: GrayscalePixel): GrayscalePixel = {
    val newBrightness = src.grayscaleValue + brightness

    if(newBrightness < 0)
      return GrayscalePixel(0)
    else if(newBrightness > 255)
      return GrayscalePixel(255)

    GrayscalePixel(newBrightness)
  }
}
