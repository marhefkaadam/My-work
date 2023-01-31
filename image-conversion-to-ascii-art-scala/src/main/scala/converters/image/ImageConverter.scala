package converters.image

import asciiArtApp.models.image.Image
import asciiArtApp.models.pixel.Pixel
import converters.Converter

/**
 * Trait is used for converting from one type of image to another type of image.
 * @tparam T - source type, upper bound to Image[Pixel]
 * @tparam S - type to be converted to, upper bound to Image[Pixel]
 */
trait ImageConverter[T <: Image[Pixel], S <: Image[Pixel]] extends Converter[T, S] {
  /**
   * Method converts data from type T do type S. Types are upper bounded to Image[Pixel].
   * @param from - source type
   * @return - converted data of new type S
   */
  def convert(from: T): S
}
