package filters.image.specific.flip

import asciiArtApp.models.image.GrayscaleImage
import asciiArtApp.models.pixel.GrayscalePixel
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid

/**
 * Class for filtering grayscale image by flipping it by horizontal axis.
 */
class HorizontalFlipFilter extends FlipFilter {
  /**
   * Method takes grayscale image and flips it's inside pixels by horizontal axis.
   * It's made by creating new image but columns of the image are in reversed order.
   * @param image - grayscale image to be flipped
   * @return new flipped grayscale image
   */
  override def filter(image: GrayscaleImage): GrayscaleImage = {
    var newPixelGrid = Seq[Seq[GrayscalePixel]]()
    for(x <- image.height - 1 to 0 by -1) {
      var newRow = Seq[GrayscalePixel]()

      for (y <- 0 until image.width) {
        newRow = newRow.prepended(image.getPixel(x, y))
      }
      newRow = newRow.reverse

      newPixelGrid = newPixelGrid.prepended(newRow)
    }
    newPixelGrid = newPixelGrid.reverse

    new GrayscaleImage(new GrayscalePixelGrid(newPixelGrid))
  }
}
