package asciiArtApp.console

import asciiArtApp.console.parsers.{ConsoleParser, Parser}
import asciiArtApp.facades.AsciiArtFacade
import converters.image.ascii.GrayscaleToAsciiConverter
import converters.image.grayscale.RGBToGrayscaleConverter

object Main extends App {
  try {
    // specifying parser and facade for program
    val parser: Parser = new ConsoleParser(args)
    val asciiArtFacade = new AsciiArtFacade()

    // parsing and validating inputted arguments
    val (importer, filter, conversionTable, exporters) = parser.parse()

    val RGBToGrayscaleConverter = new RGBToGrayscaleConverter()
    val grayscaleToAsciiConverter = new GrayscaleToAsciiConverter(conversionTable)

    // executing application logic based on inputted arguments
    asciiArtFacade.importFilterConvertExport(importer, RGBToGrayscaleConverter, filter, grayscaleToAsciiConverter, exporters)
  } catch {
    case e: Exception => println("Error: " + e.getMessage)
  }
}
