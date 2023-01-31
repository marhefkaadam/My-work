package importers.fileGenerator

import importers.Importer

/**
 * Trait for generating files of specified type.
 * @tparam T - specified type of generated data
 */
trait FileGenerator[T] extends Importer[T] {
  /**
   * Method is used to import generated data
   * @return specified type of generated data
   */
  override def importData(): T
}
