package asciiArtApp.console.parsers

import asciiArtApp.console.parsers.ConsoleParser.{BrightnessArgument, CrazyNonLinearTableName, CustomTableArgument, FlipArgument, HorizontalAxis, ImageArgument, ImageRandomArgument, InvertArgument, OutputConsoleArgument, OutputFileArgument, PaulBourkeTableName, TableArgument, VerticalAxis}
import asciiArtApp.models.image.{GrayscaleImage, RGBImage}
import converters.image.ascii.ConversionTable
import converters.image.ascii.linear.{CustomLinearConversionTable, PaulBourkeLinearConversionTable}
import converters.image.ascii.nonLinear.CrazyNonLinearConversionTable
import exporters.text.{FileOutputExporter, StdOutputExporter, TextExporter}
import filters.image.ImageFilter
import filters.image.defaults.ImageIdentityFilter
import filters.image.mixed.ImageMixedFilter
import filters.image.specific.brightness.BrightnessFilter
import filters.image.specific.flip.{HorizontalFlipFilter, VerticalFlipFilter}
import filters.image.specific.invert.InvertFilter
import importers.Importer
import importers.fileGenerator.RandomImageGenerator
import importers.fileImporter.{FileImporter, JpgImporter, PngImporter}

import java.io.File
import scala.annotation.tailrec

/**
 * Class used for parsing arguments inputted to console by user.
 * @param args - arguments on input
 */
class ConsoleParser(args: Array[String]) extends Parser {
  /**
   * Method calls for parsing of data and then validates if parsed data is correct.
   * @return data parsed from the user which is used for running the application
   * @throws IllegalArgumentException if there are problems with validation of parsed input, e.g. too many
   *                                  image arguments or conversion tables, output method not specified or
   *                                  0 arguments passed on input
   */
  override def parse(): (Importer[RGBImage], ImageFilter[GrayscaleImage], ConversionTable, Seq[TextExporter]) = {
    if(args.isEmpty) {
      throw new IllegalArgumentException("There were 0 arguments specified.")
    }

    val argList = args.toList

    var parsedData = parseArgument(argList, ParsingData())

    if(parsedData.importers.length != 1) {
      throw new IllegalArgumentException("None or too many --image* arguments found. There must be exactly one --image* argument.")
    } else if (parsedData.conversionTables.length > 1) {
      throw new IllegalArgumentException("Too many conversion tables specified.")
    } else if (parsedData.exporters.isEmpty) {
      throw new IllegalArgumentException("Output method was not specified.")
    }

    // sets default conversion table
    if(parsedData.conversionTables.isEmpty) {
      parsedData = parsedData.addConversionTable(new PaulBourkeLinearConversionTable())
    }

    // sets default filter - which doesn't really filters the picture
    if(parsedData.filters.isEmpty) {
      return (parsedData.importers.head, new ImageIdentityFilter(), parsedData.conversionTables.head, parsedData.exporters)
    }

    (parsedData.importers.head, new ImageMixedFilter(parsedData.filters), parsedData.conversionTables.head, parsedData.exporters)
  }

  /**
   * Method uses tail recursion to parse inputted arguments. Using pattern matching each argument is matched
   * for it's string value and then handled by creating appropriate object to be used to execute this command
   * further in the program.
   * @param args - arguments which are left to parse
   * @param parseData - already parsed data defined by objects used to execute this command
   * @return all parsed data from the input
   * @throws IllegalArgumentException if an incorrect argument was passed on input
   */
  @tailrec
  private def parseArgument(args: Seq[String], parseData: ParsingData): ParsingData = {
    args match {
      case ImageArgument :: path :: tail => parseArgument(tail, parseData.addImporter(getFileImporter(path)))
      case ImageRandomArgument :: tail => parseArgument(tail, parseData.addImporter(new RandomImageGenerator))

      case InvertArgument :: tail => parseArgument(tail, parseData.addFilter(new InvertFilter))
      case FlipArgument :: axis :: tail => parseArgument(tail, parseData.addFilter(getFlipFilter(axis)))
      case BrightnessArgument :: brightness :: tail => parseArgument(tail, parseData.addFilter(new BrightnessFilter(brightness.toInt)))

      case TableArgument :: tableName :: tail => parseArgument(tail, parseData.addConversionTable(getConversionTable(tableName)))
      case CustomTableArgument :: characters :: tail => parseArgument(tail, parseData.addConversionTable(new CustomLinearConversionTable(characters.toList)))

      case OutputFileArgument :: path :: tail => parseArgument(tail, parseData.addExporter(new FileOutputExporter(new File(path))))
      case OutputConsoleArgument :: tail => parseArgument(tail, parseData.addExporter(new StdOutputExporter))

      case Nil => parseData
      case _ => throw new IllegalArgumentException("Incorrect argument passed.")
    }
  }

  /**
   * Method matches inputted path and returns valid file importer based on the file type specified in path.
   * @param path - inputted path
   * @return appropriate file importer
   * @throws IllegalArgumentException if the inputted file format is not supported
   */
  private def getFileImporter(path: String): FileImporter[RGBImage] = {
    path match {
      case _ if path.endsWith(".png") => new PngImporter(path)
      case _ if path.endsWith(".jpg") => new JpgImporter(path)
      case _ => throw new IllegalArgumentException("Unsupported file format. Supported formats are: JPG, PNG.")
    }
  }

  /**
   * Method matches inputted table name and returns valid conversion table.
   * @param tableName - inputted table name
   * @return conversion table type based on defined table names
   * @throws IllegalArgumentException if specified conversion table name was not found
   */
  private def getConversionTable(tableName: String): ConversionTable = {
    tableName match {
      case PaulBourkeTableName => new PaulBourkeLinearConversionTable()
      case CrazyNonLinearTableName => new CrazyNonLinearConversionTable()
      case _ => throw new IllegalArgumentException("Specified conversion table name not found.")
    }
  }

  /**
   * Method matches inputted axis and return appropriate flip filter.
   * @param axis - inputted axis of flip filter
   * @return flip filter based on inputted axis
   * @throws IllegalArgumentException is inputted axis is not supported
   */
  private def getFlipFilter(axis: String): ImageFilter[GrayscaleImage] = {
    axis match {
      case HorizontalAxis => new HorizontalFlipFilter()
      case VerticalAxis => new VerticalFlipFilter()
      case _ => throw new IllegalArgumentException("Incorrect axis for flip filter specified.")
    }
  }
}

object ConsoleParser {
  val ImageArgument = "--image"
  val ImageRandomArgument = "--image-random"
  val InvertArgument = "--invert"
  val FlipArgument = "--flip"
  val BrightnessArgument = "--brightness"
  val TableArgument = "--table"
  val CustomTableArgument = "--custom-table"
  val OutputFileArgument = "--output-file"
  val OutputConsoleArgument = "--output-console"

  val PaulBourkeTableName = "PaulBourke"
  val CrazyNonLinearTableName = "CrazyNonLinear"
  val HorizontalAxis = "x"
  val VerticalAxis = "y"
}
