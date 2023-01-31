package importers.fileImporter

import asciiArtApp.models.image.RGBImage

/**
 * Class for importing jpg files.
 * @param path - path from where to import the file
 */
class JpgImporter(path: String) extends FileImporter[RGBImage] {
  /**
   * Method imports jpg file using method from predecessor.
   * Method checks if the file type specified in path is correct.
   * @return imported RGBImage
   */
  override def importData(): RGBImage = {
    if(! path.endsWith(".jpg"))
      throw new IllegalArgumentException("Incorrect file type: allowed is .jpg")

    super.importWithImageIO(path)
  }
}
