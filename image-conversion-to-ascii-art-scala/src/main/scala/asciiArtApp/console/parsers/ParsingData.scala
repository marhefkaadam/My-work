package asciiArtApp.console.parsers

import asciiArtApp.models.image.{GrayscaleImage, RGBImage}
import converters.image.ascii.ConversionTable
import exporters.text.TextExporter
import filters.image.ImageFilter
import importers.Importer

/**
 * Case class encapsulates data gathered then parsing arguments inputted by user.
 * Encapsulated data is immutable.
 * @param importers - sequence of parsed importers from input
 * @param filters - sequence of parsed filters from input
 * @param conversionTables - sequence of parsed conversion tables from input
 * @param exporters - sequence of parsed exporters from input
 */
case class ParsingData(importers: Seq[Importer[RGBImage]] = Seq.empty,
                       filters: Seq[ImageFilter[GrayscaleImage]] = Seq.empty,
                       conversionTables: Seq[ConversionTable] = Seq.empty,
                       exporters: Seq[TextExporter] = Seq.empty) {

  def addImporter(value: Importer[RGBImage]): ParsingData = ParsingData(importers.appended(value), filters, conversionTables, exporters)

  def addFilter(value: ImageFilter[GrayscaleImage]): ParsingData = ParsingData(importers, filters.appended(value), conversionTables, exporters)

  def addConversionTable(value: ConversionTable): ParsingData = ParsingData(importers, filters, conversionTables.appended(value), exporters)

  def addExporter(value: TextExporter): ParsingData = ParsingData(importers, filters, conversionTables, exporters.appended(value))
}
