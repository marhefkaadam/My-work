package filters

/**
 * Trait for filtering various data with specified data type.
 * @tparam T - type of data to be filtered
 */
trait Filter[T] {
  /**
   * Method is used to filter data of specified type.
   * @param item - data to be filtered
   * @return filtered data of the same type
   */
  def filter(item: T): T
}
