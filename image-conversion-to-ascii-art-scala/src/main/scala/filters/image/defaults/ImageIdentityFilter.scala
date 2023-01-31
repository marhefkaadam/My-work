package filters.image.defaults

import asciiArtApp.models.image.{GrayscaleImage, Image}
import asciiArtApp.models.pixel.Pixel
import filters.defaults.IdentityFilter
import filters.image.ImageFilter

/**
 * Class for filtering which takes grayscale image and returns it untouched back.
 * The class doesn't filter the data. It is used as a placeholder when needed.
 */
class ImageIdentityFilter extends IdentityFilter[GrayscaleImage] with ImageFilter[GrayscaleImage] {
  /**
   * Method takes image which should be filtered and returns it back with no changes.
   * @param item - grayscale image to be filtered
   * @return non-filtered grayscale image
   */
  override def filter(item: GrayscaleImage): GrayscaleImage = item
}
