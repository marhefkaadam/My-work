package importers.fileImporter

import asciiArtApp.models.image.RGBImage
import asciiArtApp.models.pixel.RGBPixel
import asciiArtApp.models.pixelGrid.RGBPixelGrid
import importers.Importer

import java.io.{File, IOException}
import javax.imageio.ImageIO


/**
 * Trait for importing various file types.
 * @tparam T - type of imported data
 */
trait FileImporter[T] extends Importer[T] {
  /**
   * Method is used for importing various file types.
   * @return imported data
   */
  override def importData(): T

  /**
   * Method imports image of type RGB from specified path.
   * @param path - path from where to import the file
   * @returns imported RGBImage
   */
  def importWithImageIO(path: String): RGBImage = {
    try {
      val bufferedImage = ImageIO.read(new File(path))

      if(bufferedImage == null) {
        throw new Exception("Image is corrupted.")
      }

      var newPixelGrid = Seq[Seq[RGBPixel]]()
      for (y <- 0 until bufferedImage.getHeight()) {
        var newRow = Seq[RGBPixel]()
        for (x <- 0 until bufferedImage.getWidth()) {
          val rgb = bufferedImage.getRGB(x, y)
          // values of r, g, b are coded on different bytes of imported value
          val r = (rgb >> 16) & 0x000000FF
          val g = (rgb >> 8) & 0x000000FF
          val b = rgb & 0x000000FF

          newRow = newRow.prepended(RGBPixel(r,g,b))
        }
        newRow = newRow.reverse

        newPixelGrid = newPixelGrid.prepended(newRow)
      }
      newPixelGrid = newPixelGrid.reverse

      new RGBImage(new RGBPixelGrid(newPixelGrid))
    } catch {
      case e: IOException => throw new Exception("Problem with reading of text.")
    }
  }
}
