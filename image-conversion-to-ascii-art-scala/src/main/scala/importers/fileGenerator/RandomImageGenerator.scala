package importers.fileGenerator

import asciiArtApp.models.image.RGBImage
import asciiArtApp.models.pixel.RGBPixel
import asciiArtApp.models.pixelGrid.RGBPixelGrid

import scala.util.Random

/**
 * Class generates RGBImage using scala.util.Random functions to generate random values.
 * The seed may be specified in the class attribute or default random seed is selected.
 * Image has random width, height and each pixel has random values of r, g, b.
 */
class RandomImageGenerator(seed: Long = Random.nextLong()) extends FileGenerator[RGBImage] {
  /**
   * Method is used to import generated RGBImage using scala.util.Random functions with specified seed.
   * @return randomly generated RGBImage
   */
  override def importData(): RGBImage = {
    val MaxHeight = 800
    val MaxWidth = 800

    // call randomGenerator.nextInt(value) returns random number between 0 and value-1
    val randomGenerator = new Random(seed)
    val randomHeight = randomGenerator.nextInt(MaxHeight + 1)
    val randomWidth = randomGenerator.nextInt(MaxWidth + 1)

    var newPixelGrid = Seq[Seq[RGBPixel]]()
    for (_ <- 0 until randomHeight) {
      var newRow = Seq[RGBPixel]()
      for (_ <- 0 until randomWidth) {
        val r = randomGenerator.nextInt(256)
        val g = randomGenerator.nextInt(256)
        val b = randomGenerator.nextInt(256)
        
        newRow = newRow.prepended(RGBPixel(r,g,b))
      }
      newRow = newRow.reverse

      newPixelGrid = newPixelGrid.prepended(newRow)
    }
    newPixelGrid = newPixelGrid.reverse

    new RGBImage(new RGBPixelGrid(newPixelGrid))
  }

}
