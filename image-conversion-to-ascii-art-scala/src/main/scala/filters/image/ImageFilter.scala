package filters.image

import asciiArtApp.models.image.Image
import asciiArtApp.models.pixel.Pixel
import filters.Filter

/**
 * Trait for filtering image with specified data type upper bounded to Image[Pixel].
 * @tparam T - type of data to be filtered
 */
trait ImageFilter[T <: Image[Pixel]] extends Filter[T] {
  /**
   * Method is used to filter data of specified type.
   * @param item - data to be filtered
   * @return filtered data of the same type
   */
  def filter(item: T): T
}
