package filters.image.specific.invert

import asciiArtApp.models.image.GrayscaleImage
import asciiArtApp.models.pixel.GrayscalePixel
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid
import filters.image.ImageFilter

/**
 * Class filters grayscale image by inverting it's each pixel value.
 */
class InvertFilter extends ImageFilter[GrayscaleImage] {
  /**
   * Method takes grayscale image and adjusts each pixel grayscale value inverting it.
   * @param image - grayscale image to has it's values inverted
   * @return filtered grayscale image
   */
  override def filter(image: GrayscaleImage): GrayscaleImage = {
    var newPixelGrid = Seq[Seq[GrayscalePixel]]()
    for(x <- 0 until image.height) {
      var newRow = Seq[GrayscalePixel]()

      for(y <- 0 until image.width) {
        newRow = newRow.prepended(invertGrayscaleValue(image.getPixel(x, y)))
      }
      newRow = newRow.reverse

      newPixelGrid = newPixelGrid.prepended(newRow)
    }
    newPixelGrid = newPixelGrid.reverse

    new GrayscaleImage(new GrayscalePixelGrid(newPixelGrid))
  }

  /**
   * Method takes grayscale pixel and inverts it.
   * Inversion is made by decrementing the pixel grayscale value from 255.
   * @param src - pixel to be inverted
   * @return new inverted grayscale pixel
   */
  private def invertGrayscaleValue(src: GrayscalePixel): GrayscalePixel = {
    GrayscalePixel(255 - src.grayscaleValue)
  }
}
