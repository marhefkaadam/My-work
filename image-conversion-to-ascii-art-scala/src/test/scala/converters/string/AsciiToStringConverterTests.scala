package converters.string

import asciiArtApp.models.image.AsciiImage
import asciiArtApp.models.pixel.AsciiPixel
import asciiArtApp.models.pixelGrid.AsciiPixelGrid
import org.scalatest.FunSuite

class AsciiToStringConverterTests extends FunSuite {
  test("Convert ascii image to string 1") {
    val pixels = Seq(
      Seq(AsciiPixel('X'), AsciiPixel('#')),
      Seq(AsciiPixel('#'), AsciiPixel('#')),
      Seq(AsciiPixel('A'), AsciiPixel('.'))
    )
    val image = new AsciiImage(new AsciiPixelGrid(pixels))
    val converter = new AsciiToStringConverter()

    assert(converter.convert(image) == "X#\n##\nA.\n")
  }

  test("Convert ascii image to string 2") {
    val pixels = Seq(
      Seq(AsciiPixel('.'), AsciiPixel('3'))
    )
    val image = new AsciiImage(new AsciiPixelGrid(pixels))
    val converter = new AsciiToStringConverter()

    assert(converter.convert(image) == ".3\n")
  }

  test("Convert ascii image to string 3") {
    val pixels = Seq(
      Seq(AsciiPixel('\"'), AsciiPixel('?'), AsciiPixel('\\'))
    )
    val image = new AsciiImage(new AsciiPixelGrid(pixels))
    val converter = new AsciiToStringConverter()

    assert(converter.convert(image) == "\"?\\\n")
  }

  test("Convert ascii image to string 4") {
    val pixels = Seq(
      Seq(AsciiPixel('\"'))
    )
    val image = new AsciiImage(new AsciiPixelGrid(pixels))
    val converter = new AsciiToStringConverter()

    assert(converter.convert(image) == "\"\n")
  }

  test("Convert empty ascii image to string") {
    val pixels = Seq[Seq[AsciiPixel]]()
    val image = new AsciiImage(new AsciiPixelGrid(pixels))
    val converter = new AsciiToStringConverter()

    assert(converter.convert(image) == "")
  }
}
