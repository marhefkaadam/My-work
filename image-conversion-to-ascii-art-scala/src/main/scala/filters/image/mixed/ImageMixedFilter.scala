package filters.image.mixed

import asciiArtApp.models.image.GrayscaleImage
import filters.image.ImageFilter

/**
 * Class for filtering grayscale image using more specified filters at once.
 * @param filters - specified filters which all should be used to filter the image
 */
class ImageMixedFilter(filters: Seq[ImageFilter[GrayscaleImage]]) extends ImageFilter[GrayscaleImage] {
  /**
   * Method takes grayscale image and runs all filters specified sequence on the image.
   * @param item - grayscale image to be filtered
   * @return filtered grayscale image
   */
  override def filter(item: GrayscaleImage): GrayscaleImage = {
    filters.foldLeft(item)((acc, filter) => filter.filter(acc))
  }
}
