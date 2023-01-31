package asciiArtApp.console.parsers

import asciiArtApp.models.image.{GrayscaleImage, RGBImage}
import converters.image.ascii.ConversionTable
import exporters.text.TextExporter
import filters.image.ImageFilter
import importers.Importer

/**
 * Trait used for implementing various types of argument parsing.
 */
trait Parser {
  /**
   * Method is used to parse and validate arguments which were inputted by user.
   * @return data parsed from the user which is used for running the application
   */
  def parse(): (Importer[RGBImage], ImageFilter[GrayscaleImage], ConversionTable, Seq[TextExporter])
}
