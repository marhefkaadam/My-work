package filter.image.specific.brightness

import asciiArtApp.models.image.GrayscaleImage
import asciiArtApp.models.pixel.GrayscalePixel
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid
import filters.image.specific.brightness.BrightnessFilter
import org.scalatest.FunSuite

class BrightnessFilterTests extends FunSuite {
  test("Change image brightness - positive number") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new BrightnessFilter(50)
    val filteredImage = filter.filter(grayscaleImage)

    assert(grayscaleImage.width == filteredImage.width)
    assert(grayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(grayscaleImage.getPixel(x, y).grayscaleValue + 50 == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Change image brightness - negative number") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(151.33), GrayscalePixel(230)),
      Seq(GrayscalePixel(151.33), GrayscalePixel(230)),
      Seq(GrayscalePixel(151.33), GrayscalePixel(230))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new BrightnessFilter(-100.25)
    val filteredImage = filter.filter(grayscaleImage)

    assert(grayscaleImage.width == filteredImage.width)
    assert(grayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(grayscaleImage.getPixel(x, y).grayscaleValue - 100.25 == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Change image brightness - no change of value") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new BrightnessFilter(0)
    val filteredImage = filter.filter(grayscaleImage)

    assert(grayscaleImage.width == filteredImage.width)
    assert(grayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(grayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Change image brightness - grayscale value >255") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(151.33), GrayscalePixel(230)),
      Seq(GrayscalePixel(151.33), GrayscalePixel(230)),
      Seq(GrayscalePixel(151.33), GrayscalePixel(230))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new BrightnessFilter(+100)
    val filteredImage = filter.filter(grayscaleImage)

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(251.33), GrayscalePixel(255)),
      Seq(GrayscalePixel(251.33), GrayscalePixel(255)),
      Seq(GrayscalePixel(251.33), GrayscalePixel(255))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Change image brightness - grayscale value <0") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(151.33), GrayscalePixel(30)),
      Seq(GrayscalePixel(151.33), GrayscalePixel(30)),
      Seq(GrayscalePixel(151.33), GrayscalePixel(30))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new BrightnessFilter(-150.33)
    val filteredImage = filter.filter(grayscaleImage)

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(1), GrayscalePixel(0)),
      Seq(GrayscalePixel(1), GrayscalePixel(0)),
      Seq(GrayscalePixel(1), GrayscalePixel(0))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Change image brightness - empty image") {
    val grayscalePixels = Seq[Seq[GrayscalePixel]]()
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new BrightnessFilter(-150.33)
    val filteredImage = filter.filter(grayscaleImage)

    assert(filteredImage.width == grayscaleImage.width)
    assert(filteredImage.height == grayscaleImage.height)
    assert(filteredImage.width == 0)
    assert(filteredImage.height == 0)
  }
}
