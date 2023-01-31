package converters.image.grayscale

import asciiArtApp.models.image.{GrayscaleImage, RGBImage}
import asciiArtApp.models.pixel.{GrayscalePixel, RGBPixel}
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid
import converters.image.ImageConverter

/**
 * Class converts image consisting of RGB values to image of grayscale values.
 */
class RGBToGrayscaleConverter extends ImageConverter[RGBImage, GrayscaleImage] {
  /**
   * Method converts RGB image to grayscale image by converting each pixel and creating new image.
   * @param image - RGB image to be converted
   * @return new converted grayscale image
   */
  override def convert(image: RGBImage): GrayscaleImage = {
    var newPixelGrid = Seq[Seq[GrayscalePixel]]()
    for(x <- 0 until image.height) {
      var newRow = Seq[GrayscalePixel]()

      for(y <- 0 until image.width) {
        newRow = newRow.prepended(getGrayscalePixel(image.getPixel(x, y)))
      }
      newRow = newRow.reverse

      newPixelGrid = newPixelGrid.prepended(newRow)
    }
    newPixelGrid = newPixelGrid.reverse

    new GrayscaleImage(new GrayscalePixelGrid(newPixelGrid))
  }

  /**
   * Method converts RGB pixel values to grayscale pixel value.
   * Grayscale value is computed by counting (0.3 * R) + (0.59 * G) + (0.11 * B) where R, G, B are values of pixel.
   * @param pixel - RGB pixel to be converted
   * @return new grayscale pixel with computed grayscale value
   */
  private def getGrayscalePixel(pixel: RGBPixel): GrayscalePixel = {
    val grayscaleValue: Double = (0.3 * pixel.r) + (0.59 * pixel.g) + (0.11 * pixel.b)
    GrayscalePixel(grayscaleValue)
  }
}

