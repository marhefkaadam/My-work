package importers.fileImporter

import asciiArtApp.models.image.RGBImage

/**
 * Class for importing png files.
 * @param path - path from where to import the file
 */
class PngImporter(path: String) extends FileImporter[RGBImage] {
  /**
   * Method imports png file using method from predecessor.
   * Method checks if the file type specified in path is correct.
   * @return imported RGBImage
   */
  override def importData(): RGBImage = {
    if(! path.endsWith(".png"))
      throw new IllegalArgumentException("Incorrect file type: allowed is .png")

    super.importWithImageIO(path)
  }
}
