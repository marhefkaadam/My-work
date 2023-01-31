package exporters

/**
 * Trait for exporting data to specified type.
 * @tparam T - type of data to which the export should be made
 */
trait Exporter[T] {
  /**
   * Method is used to export data to specified type.
   * @param item - data to be exported
   */
  def export(item: T): Unit
}
