package filters.defaults

import filters.Filter

/**
 * Class for filtering which takes data and returns it untouched back.
 * The class doesn't filter the data. It is used as a placeholder when needed.
 * @tparam T - type of data to be filtered
 */
class IdentityFilter[T] extends Filter[T] {
  /**
   * Method takes data which should be filtered and returns it back with no changes.
   * @param item - data to be filtered
   * @return non-filtered data of the same type
   */
  override def filter(item: T): T = item
}
