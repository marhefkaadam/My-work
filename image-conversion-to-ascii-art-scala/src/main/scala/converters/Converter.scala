package converters

/**
 * Trait is used for converting from one type to another.
 * @tparam T - source type
 * @tparam S - type to be converted to
 */
trait Converter[T, S] {
  /**
   * Method converts data from type T do type S.
   * @param from - source type
   * @return - converted data of new type S
   */
  def convert(from: T): S
}
