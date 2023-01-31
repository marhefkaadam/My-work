package converters.image.ascii

import asciiArtApp.models.image.{AsciiImage, GrayscaleImage}
import asciiArtApp.models.pixel.{AsciiPixel, GrayscalePixel}
import asciiArtApp.models.pixelGrid.{AsciiPixelGrid, GrayscalePixelGrid}
import org.mockito.Mockito.when
import org.scalatest.FunSuite
import org.mockito.MockitoSugar.mock

class GrayscaleToAsciiConverterTests extends FunSuite {
  test("Conversion from grayscale to ascii image 1") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val asciiPixels = Seq(
      Seq(AsciiPixel('C'), AsciiPixel('.')),
      Seq(AsciiPixel('C'), AsciiPixel('.')),
      Seq(AsciiPixel('C'), AsciiPixel('.'))
    )
    val asciiImage = new AsciiImage(new AsciiPixelGrid(asciiPixels))

    val conversionTable = mock[ConversionTable]
    when(conversionTable.convert(15.81)).thenReturn('C')
    when(conversionTable.convert(0.11)).thenReturn('.')

    val converter = new GrayscaleToAsciiConverter(conversionTable)
    val convertedImage = converter.convert(grayscaleImage)

    assert(convertedImage.width == grayscaleImage.width)
    assert(convertedImage.height == grayscaleImage.height)
    assert(convertedImage.width == 2)
    assert(convertedImage.height == 3)

    for(x <- 0 until convertedImage.height) {
      for(y <- 0 until convertedImage.width) {
        assert(asciiImage.getPixel(x, y).symbol == convertedImage.getPixel(x, y).symbol)
      }
    }
  }

  test("Conversion from grayscale to ascii image 2") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(132.33), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val asciiPixels = Seq(
      Seq(AsciiPixel('\"'), AsciiPixel('\\'))
    )
    val asciiImage = new AsciiImage(new AsciiPixelGrid(asciiPixels))

    val conversionTable = mock[ConversionTable]
    when(conversionTable.convert(132.33)).thenReturn('\"')
    when(conversionTable.convert(255)).thenReturn('\\')

    val converter = new GrayscaleToAsciiConverter(conversionTable)
    val convertedImage = converter.convert(grayscaleImage)

    assert(convertedImage.width == grayscaleImage.width)
    assert(convertedImage.height == grayscaleImage.height)
    assert(convertedImage.width == 2)
    assert(convertedImage.height == 1)

    for(x <- 0 until convertedImage.height) {
      for(y <- 0 until convertedImage.width) {
        assert(asciiImage.getPixel(x, y).symbol == convertedImage.getPixel(x, y).symbol)
      }
    }
  }

  test("Conversion from grayscale to ascii image 3") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(254.3))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val asciiPixels = Seq(
      Seq(AsciiPixel('?'))
    )
    val asciiImage = new AsciiImage(new AsciiPixelGrid(asciiPixels))

    val conversionTable = mock[ConversionTable]
    when(conversionTable.convert(254.3)).thenReturn('?')

    val converter = new GrayscaleToAsciiConverter(conversionTable)
    val convertedImage = converter.convert(grayscaleImage)

    assert(convertedImage.width == grayscaleImage.width)
    assert(convertedImage.height == grayscaleImage.height)
    assert(convertedImage.width == 1)
    assert(convertedImage.height == 1)

    for(x <- 0 until convertedImage.height) {
      for(y <- 0 until convertedImage.width) {
        assert(asciiImage.getPixel(x, y).symbol == convertedImage.getPixel(x, y).symbol)
      }
    }
  }

  test("Conversion from grayscale to ascii image 4") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11), GrayscalePixel(123.33), GrayscalePixel(30.3), GrayscalePixel(0.11))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val asciiPixels = Seq(
      Seq(AsciiPixel('C'), AsciiPixel('.'), AsciiPixel('F'), AsciiPixel('5'), AsciiPixel('.'))
    )
    val asciiImage = new AsciiImage(new AsciiPixelGrid(asciiPixels))

    val conversionTable = mock[ConversionTable]
    when(conversionTable.convert(15.81)).thenReturn('C')
    when(conversionTable.convert(0.11)).thenReturn('.')
    when(conversionTable.convert(123.33)).thenReturn('F')
    when(conversionTable.convert(30.3)).thenReturn('5')

    val converter = new GrayscaleToAsciiConverter(conversionTable)
    val convertedImage = converter.convert(grayscaleImage)

    assert(convertedImage.width == grayscaleImage.width)
    assert(convertedImage.height == grayscaleImage.height)
    assert(convertedImage.width == 5)
    assert(convertedImage.height == 1)

    for(x <- 0 until convertedImage.height) {
      for(y <- 0 until convertedImage.width) {
        assert(asciiImage.getPixel(x, y).symbol == convertedImage.getPixel(x, y).symbol)
      }
    }
  }

  test("Conversion from grayscale to ascii image - empty image") {
    val grayscalePixels = Seq[Seq[GrayscalePixel]]()
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val conversionTable = mock[ConversionTable]
    val converter = new GrayscaleToAsciiConverter(conversionTable)
    val convertedImage = converter.convert(grayscaleImage)

    assert(convertedImage.width == grayscaleImage.width)
    assert(convertedImage.height == grayscaleImage.height)
    assert(convertedImage.width == 0)
    assert(convertedImage.height == 0)
  }
}
