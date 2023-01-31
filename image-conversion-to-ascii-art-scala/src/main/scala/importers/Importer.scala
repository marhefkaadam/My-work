package importers

/**
 * Trait for importing specified types of data
 * @tparam T - type of imported data
 */
trait Importer[T] {
  /**
   * Method is used for importing specified types of data.
   * @return imported data
   */
  def importData(): T
}
