package filters.image.specific.flip

import asciiArtApp.models.image.GrayscaleImage
import asciiArtApp.models.pixel.GrayscalePixel
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid
/**
 * Class for filtering grayscale image by flipping it by vertical axis.
 */
class VerticalFlipFilter extends FlipFilter {
  /**
   * Method takes grayscale image and flips it's inside pixels by vertical axis.
   * It's made by creating new image but rows of the image are in reversed order.
   * @param image - grayscale image to be flipped
   * @return new flipped grayscale image
   */
  override def filter(image: GrayscaleImage): GrayscaleImage = {
    var newPixelGrid = Seq[Seq[GrayscalePixel]]()
    for(x <- 0 until image.height) {
      var newRow = Seq[GrayscalePixel]()

      for (y <- image.width - 1 to 0 by -1) {
        newRow = newRow.prepended(image.getPixel(x, y))
      }
      newRow = newRow.reverse

      newPixelGrid = newPixelGrid.prepended(newRow)
    }
    newPixelGrid = newPixelGrid.reverse

    new GrayscaleImage(new GrayscalePixelGrid(newPixelGrid))
  }
}
