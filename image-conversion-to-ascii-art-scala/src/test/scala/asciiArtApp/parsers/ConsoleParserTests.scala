package asciiArtApp.parsers

import asciiArtApp.console.parsers.ConsoleParser
import converters.image.ascii.linear.{CustomLinearConversionTable, PaulBourkeLinearConversionTable}
import converters.image.ascii.nonLinear.CrazyNonLinearConversionTable
import exporters.text.{FileOutputExporter, StdOutputExporter}
import filters.image.defaults.ImageIdentityFilter
import filters.image.mixed.ImageMixedFilter
import importers.fileGenerator.RandomImageGenerator
import importers.fileImporter.{JpgImporter, PngImporter}
import org.scalatest.FunSuite

class ConsoleParserTests extends FunSuite {
  test("Parser - empty argument array") {
    val args: Array[String] = Array()
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - too many image arguments") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image2.jpg",
      "--image-random",
      "--output-console"
    )
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - too many image arguments 2") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image2.jpg",
      "--image", "src/test/resources/test-image1.png",
      "--output-console"
    )
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - non existing argument passed") {
    val args: Array[String] = Array(
      "--lalala"
    )
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - incorrect image file format") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image2.xml",
      "--output-console"
    )
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - no image or image random argument specified") {
    val args: Array[String] = Array(
      "--invert",
      "--output-console"
    )
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - too many conversion tables specified") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image2.xml",
      "--table", "PaulBourke",
      "--table", "CrazyNonLinear",
      "--output-console"
    )
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - custom and pre-made table, too many conversion tables") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image1.png",
      "--custom-table", "D",
      "--table", "PaulBourke",
      "--output-file", "src/test/resources/test-image1-png-customTable-export.txt"
    )
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - output method not specified") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image2.jpg",
      "--invert"
    )
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - specified conversion table not found") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image2.jpg",
      "--table", "NonExistingTable",
      "--output-console"
    )
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - illegal flip axis specified") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image2.jpg",
      "--flip", "z",
      "--output-console"
    )
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - import jpg image, default conversion table, output to file") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image2.jpg",
      "--output-file", "src/test/resources/test-image2-jpg-export.txt"
    )
    val parser = new ConsoleParser(args)

    val (importer, filter, conversionTable, exporters) = parser.parse()
    assert(importer.isInstanceOf[JpgImporter])
    assert(filter.isInstanceOf[ImageIdentityFilter])
    assert(conversionTable.isInstanceOf[PaulBourkeLinearConversionTable])
    assert(exporters.head.isInstanceOf[FileOutputExporter])
  }

  test("Parser - import png image, default conversion table, output to file") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image1.png",
      "--output-file", "src/test/resources/test-image1-png-export.txt"
    )
    val parser = new ConsoleParser(args)

    val (importer, filter, conversionTable, exporters) = parser.parse()
    assert(importer.isInstanceOf[PngImporter])
    assert(filter.isInstanceOf[ImageIdentityFilter])
    assert(conversionTable.isInstanceOf[PaulBourkeLinearConversionTable])
    assert(exporters.head.isInstanceOf[FileOutputExporter])
  }

  test("Parser - import png image, conversion table PaulBourke, output to file") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image1.png",
      "--table", "PaulBourke",
      "--output-file", "src/test/resources/test-image1-png-PaulBourke-export.txt"
    )
    val parser = new ConsoleParser(args)

    val (importer, filter, conversionTable, exporters) = parser.parse()
    assert(importer.isInstanceOf[PngImporter])
    assert(filter.isInstanceOf[ImageIdentityFilter])
    assert(conversionTable.isInstanceOf[PaulBourkeLinearConversionTable])
    assert(exporters.head.isInstanceOf[FileOutputExporter])
  }

  test("Parser - import png image, conversion table CrazyNonLinear, output to file") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image1.png",
      "--table", "CrazyNonLinear",
      "--output-file", "src/test/resources/test-image1-png-CrazyNonLinear-export.txt"
    )
    val parser = new ConsoleParser(args)

    val (importer, filter, conversionTable, exporters) = parser.parse()
    assert(importer.isInstanceOf[PngImporter])
    assert(filter.isInstanceOf[ImageIdentityFilter])
    assert(conversionTable.isInstanceOf[CrazyNonLinearConversionTable])
    assert(exporters.head.isInstanceOf[FileOutputExporter])
  }

  test("Parser - import png image, empty custom conversion table, output to file") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image1.png",
      "--custom-table", "",
      "--output-file", "src/test/resources/test-image1-png-customTable-export.txt"
    )
    val parser = new ConsoleParser(args)

    assertThrows[IllegalArgumentException](parser.parse())
  }

  test("Parser - import png image, custom conversion table, no filters, output to file") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image1.png",
      "--custom-table", "#&!.>dg)",
      "--output-file", "src/test/resources/test-image1-png-customTable-export.txt"
    )
    val parser = new ConsoleParser(args)

    val (importer, filter, conversionTable, exporters) = parser.parse()
    assert(importer.isInstanceOf[PngImporter])
    assert(filter.isInstanceOf[ImageIdentityFilter])
    assert(conversionTable.isInstanceOf[CustomLinearConversionTable])
    assert(exporters.head.isInstanceOf[FileOutputExporter])
  }

  test("Parser - import png image, custom conversion table, filters 1, output to file and console") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image1.png",
      "--flip", "x",
      "--flip", "y",
      "--custom-table", "#&!.>dg)",
      "--output-file", "src/test/resources/test-image1-png-2-export.txt",
      "--output-console"
    )
    val parser = new ConsoleParser(args)

    val (importer, filter, conversionTable, exporters) = parser.parse()
    assert(importer.isInstanceOf[PngImporter])
    assert(filter.isInstanceOf[ImageMixedFilter])
    assert(conversionTable.isInstanceOf[CustomLinearConversionTable])
    assert(exporters.head.isInstanceOf[FileOutputExporter])
    assert(exporters(1).isInstanceOf[StdOutputExporter])
  }

  test("Parser - import png image, custom conversion table, filters 2, output to file and console") {
    val args: Array[String] = Array(
      "--image", "src/test/resources/test-image1.png",
      "--flip", "x",
      "--flip", "y",
      "--invert",
      "--brightness", "20",
      "--custom-table", "#&!.>dg)",
      "--output-file", "src/test/resources/test-image1-png-3-export.txt",
      "--output-console"
    )
    val parser = new ConsoleParser(args)

    val (importer, filter, conversionTable, exporters) = parser.parse()
    assert(importer.isInstanceOf[PngImporter])
    assert(filter.isInstanceOf[ImageMixedFilter])
    assert(conversionTable.isInstanceOf[CustomLinearConversionTable])
    assert(exporters.head.isInstanceOf[FileOutputExporter])
    assert(exporters(1).isInstanceOf[StdOutputExporter])
  }

  test("Parser - random image, CrazyNonLinear table, filters 2, output to file and console") {
    val args: Array[String] = Array(
      "--image-random",
      "--flip", "x",
      "--flip", "y",
      "--invert",
      "--brightness", "20",
      "--table", "CrazyNonLinear",
      "--output-file", "src/test/resources/test-image-random-export.txt",
      "--output-console"
    )
    val parser = new ConsoleParser(args)

    val (importer, filter, conversionTable, exporters) = parser.parse()
    assert(importer.isInstanceOf[RandomImageGenerator])
    assert(filter.isInstanceOf[ImageMixedFilter])
    assert(conversionTable.isInstanceOf[CrazyNonLinearConversionTable])
    assert(exporters.head.isInstanceOf[FileOutputExporter])
    assert(exporters(1).isInstanceOf[StdOutputExporter])
  }

  test("Parser - random image, default table, brightness filters, output to console") {
    val args: Array[String] = Array(
      "--image-random",
      "--brightness", "20",
      "--brightness", "-100",
      "--output-console"
    )
    val parser = new ConsoleParser(args)

    val (importer, filter, conversionTable, exporters) = parser.parse()
    assert(importer.isInstanceOf[RandomImageGenerator])
    assert(filter.isInstanceOf[ImageMixedFilter])
    assert(conversionTable.isInstanceOf[PaulBourkeLinearConversionTable])
    assert(exporters.head.isInstanceOf[StdOutputExporter])
  }
}
