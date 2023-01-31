package filter.image.specific.invert

import asciiArtApp.models.image.GrayscaleImage
import asciiArtApp.models.pixel.GrayscalePixel
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid
import filters.image.specific.invert.InvertFilter
import org.scalatest.FunSuite

class InvertFilterTests extends FunSuite {
  test("Invert image brightness no. 1") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new InvertFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(grayscaleImage.width == filteredImage.width)
    assert(grayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(255 - grayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Invert image brightness no. 2") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(255), GrayscalePixel(0))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new InvertFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(grayscaleImage.width == filteredImage.width)
    assert(grayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(255 - grayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Invert image brightness no. 3") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(132.3), GrayscalePixel(249.9), GrayscalePixel(0.11))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new InvertFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(grayscaleImage.width == filteredImage.width)
    assert(grayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(255 - grayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Invert image brightness - min/max grayscale values") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(0), GrayscalePixel(255)),
      Seq(GrayscalePixel(0), GrayscalePixel(255)),
      Seq(GrayscalePixel(0), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new InvertFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(grayscaleImage.width == filteredImage.width)
    assert(grayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(255 - grayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Invert image brightness - empty image") {
    val grayscalePixels = Seq[Seq[GrayscalePixel]]()
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new InvertFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(filteredImage.width == grayscaleImage.width)
    assert(filteredImage.height == grayscaleImage.height)
    assert(filteredImage.width == 0)
    assert(filteredImage.height == 0)
  }
}
