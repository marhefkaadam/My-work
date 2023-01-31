package converters.image.grayscale

import asciiArtApp.models.image.{GrayscaleImage, RGBImage}
import asciiArtApp.models.pixel.{GrayscalePixel, RGBPixel}
import asciiArtApp.models.pixelGrid.{GrayscalePixelGrid, RGBPixelGrid}
import org.scalatest.FunSuite

class RGBToGrayscaleConverterTests extends FunSuite {
  test("Convert rgb image to grayscale image 1") {
    val RGBPixels = Seq(
      Seq(RGBPixel(12, 0, 111), RGBPixel(0, 0, 1)),
      Seq(RGBPixel(12, 0, 111), RGBPixel(0, 0, 1)),
      Seq(RGBPixel(12, 0, 111), RGBPixel(0, 0, 1))
    )
    val RGBImage = new RGBImage(new RGBPixelGrid(RGBPixels))

    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val converter = new RGBToGrayscaleConverter()
    val convertedImage = converter.convert(RGBImage)

    assert(convertedImage.width == RGBImage.width)
    assert(convertedImage.height == RGBImage.height)
    assert(convertedImage.width == 2)
    assert(convertedImage.height == 3)

    for(x <- 0 until convertedImage.height) {
      for(y <- 0 until convertedImage.width) {
        assert(grayscaleImage.getPixel(x, y).grayscaleValue == convertedImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Convert rgb image to grayscale image 2") {
    val RGBPixels = Seq(
      Seq(RGBPixel(0, 0, 0), RGBPixel(120, 10, 250))
    )
    val RGBImage = new RGBImage(new RGBPixelGrid(RGBPixels))

    val grayscalePixels = Seq(
      Seq(GrayscalePixel(0), GrayscalePixel(69.4))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val converter = new RGBToGrayscaleConverter()
    val convertedImage = converter.convert(RGBImage)

    assert(convertedImage.width == RGBImage.width)
    assert(convertedImage.height == RGBImage.height)
    assert(convertedImage.width == 2)
    assert(convertedImage.height == 1)

    for(x <- 0 until convertedImage.height) {
      for(y <- 0 until convertedImage.width) {
        assert(grayscaleImage.getPixel(x, y).grayscaleValue == convertedImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Convert rgb image to grayscale image 3") {
    val RGBPixels = Seq(
      Seq(RGBPixel(255, 255, 255))
    )
    val RGBImage = new RGBImage(new RGBPixelGrid(RGBPixels))

    val grayscalePixels = Seq(
      Seq(GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val converter = new RGBToGrayscaleConverter()
    val convertedImage = converter.convert(RGBImage)

    assert(convertedImage.width == RGBImage.width)
    assert(convertedImage.height == RGBImage.height)
    assert(convertedImage.width == 1)
    assert(convertedImage.height == 1)

    for(x <- 0 until convertedImage.height) {
      for(y <- 0 until convertedImage.width) {
        assert(grayscaleImage.getPixel(x, y).grayscaleValue == convertedImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Convert rgb image to grayscale image 4") {
    val RGBPixels = Seq(
      Seq(RGBPixel(12, 0, 111), RGBPixel(0, 0, 1), RGBPixel(255, 250, 23), RGBPixel(30, 30, 1), RGBPixel(0, 0, 1))
    )
    val RGBImage = new RGBImage(new RGBPixelGrid(RGBPixels))

    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11), GrayscalePixel(226.53), GrayscalePixel(26.81), GrayscalePixel(0.11))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val converter = new RGBToGrayscaleConverter()
    val convertedImage = converter.convert(RGBImage)

    assert(convertedImage.width == RGBImage.width)
    assert(convertedImage.height == RGBImage.height)
    assert(convertedImage.width == 5)
    assert(convertedImage.height == 1)

    for(x <- 0 until convertedImage.height) {
      for(y <- 0 until convertedImage.width) {
        assert(grayscaleImage.getPixel(x, y).grayscaleValue == convertedImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Convert empty rgb image to grayscale image") {
    val RGBPixels = Seq[Seq[RGBPixel]]()
    val RGBImage = new RGBImage(new RGBPixelGrid(RGBPixels))

    val converter = new RGBToGrayscaleConverter()
    val convertedImage = converter.convert(RGBImage)

    assert(convertedImage.width == RGBImage.width)
    assert(convertedImage.height == RGBImage.height)
    assert(convertedImage.width == 0)
    assert(convertedImage.height == 0)
  }
}
