package filter.image.defaults

import asciiArtApp.models.image.GrayscaleImage
import asciiArtApp.models.pixel.GrayscalePixel
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid
import filters.image.defaults.ImageIdentityFilter
import org.scalatest.FunSuite

class ImageIdentityFilterTests extends FunSuite {
  test("Image identity filter 1 - check values") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(120), GrayscalePixel(10.4)),
      Seq(GrayscalePixel(120), GrayscalePixel(10.4)),
      Seq(GrayscalePixel(120), GrayscalePixel(10.4))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new ImageIdentityFilter()

    val filteredImage = filter.filter(grayscaleImage)
    for (x <- 0 until filteredImage.height) {
      for (y <- 0 until filteredImage.width) {
        assert(grayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Image identity filter 1 - check height and weight") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(120), GrayscalePixel(10.4)),
      Seq(GrayscalePixel(120), GrayscalePixel(10.4)),
      Seq(GrayscalePixel(120), GrayscalePixel(10.4))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new ImageIdentityFilter()

    val filteredImage = filter.filter(grayscaleImage)
    assert(filteredImage.width == grayscaleImage.width)
    assert(filteredImage.height == grayscaleImage.height)
    assert(filteredImage.width == 2)
    assert(filteredImage.height == 3)
  }

  test("Image identity filter 2 - check values") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(120), GrayscalePixel(10.4), GrayscalePixel(100), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new ImageIdentityFilter()

    val filteredImage = filter.filter(grayscaleImage)
    for (x <- 0 until filteredImage.height) {
      for (y <- 0 until filteredImage.width) {
        assert(grayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Image identity filter 2 - check height and weight") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(120), GrayscalePixel(10.4), GrayscalePixel(100), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new ImageIdentityFilter()

    val filteredImage = filter.filter(grayscaleImage)
    assert(filteredImage.width == grayscaleImage.width)
    assert(filteredImage.height == grayscaleImage.height)
    assert(filteredImage.width == 4)
    assert(filteredImage.height == 1)
  }

  test("Image identity filter - empty image") {
    val grayscalePixels = Seq[Seq[GrayscalePixel]]()
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))
    val filter = new ImageIdentityFilter()

    val filteredImage = filter.filter(grayscaleImage)

    assert(filteredImage.width == grayscaleImage.width)
    assert(filteredImage.height == grayscaleImage.height)
    assert(filteredImage.width == 0)
    assert(filteredImage.height == 0)
  }
}
