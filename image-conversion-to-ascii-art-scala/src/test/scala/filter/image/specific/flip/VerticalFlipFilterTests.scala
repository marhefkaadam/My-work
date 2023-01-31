package filter.image.specific.flip

import asciiArtApp.models.image.GrayscaleImage
import asciiArtApp.models.pixel.GrayscalePixel
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid
import filters.image.specific.flip.VerticalFlipFilter
import org.scalatest.FunSuite

class VerticalFlipFilterTests extends FunSuite {
  test("Flip image - vertical, even width no. 1") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(11.0)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(18.51), GrayscalePixel(0.11))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(11.0), GrayscalePixel(15.81)),
      Seq(GrayscalePixel(0.11), GrayscalePixel(15.81)),
      Seq(GrayscalePixel(0.11), GrayscalePixel(18.51))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))


    val filter = new VerticalFlipFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Flip image - vertical, even width no. 2") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(11.0), GrayscalePixel(11.0), GrayscalePixel(11.0))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(11.0), GrayscalePixel(11.0), GrayscalePixel(11.0), GrayscalePixel(15.81))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))


    val filter = new VerticalFlipFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Flip image - vertical, even width no. 3") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(255), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(255), GrayscalePixel(255)),
      Seq(GrayscalePixel(18.51), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(0.11), GrayscalePixel(255)),
      Seq(GrayscalePixel(255), GrayscalePixel(255)),
      Seq(GrayscalePixel(255), GrayscalePixel(18.51))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))


    val filter = new VerticalFlipFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Flip image - vertical, odd width no. 1") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(0), GrayscalePixel(255), GrayscalePixel(255)),
      Seq(GrayscalePixel(0), GrayscalePixel(255), GrayscalePixel(255)),
      Seq(GrayscalePixel(0), GrayscalePixel(0), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(255), GrayscalePixel(255), GrayscalePixel(0)),
      Seq(GrayscalePixel(255), GrayscalePixel(255), GrayscalePixel(0)),
      Seq(GrayscalePixel(255), GrayscalePixel(0), GrayscalePixel(0))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))

    val filter = new VerticalFlipFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Flip image - vertical, odd width no. 2") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(11.0), GrayscalePixel(11.0), GrayscalePixel(200), GrayscalePixel(11.0))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(11.0), GrayscalePixel(200), GrayscalePixel(11.0), GrayscalePixel(11.0), GrayscalePixel(15.81))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))

    val filter = new VerticalFlipFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Flip image - vertical, odd width no. 3") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(255)),
      Seq(GrayscalePixel(255)),
      Seq(GrayscalePixel(18.51))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(255)),
      Seq(GrayscalePixel(255)),
      Seq(GrayscalePixel(18.51))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))

    val filter = new VerticalFlipFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Flip image - vertical, empty image") {
    val grayscalePixels = Seq[Seq[GrayscalePixel]]()
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new VerticalFlipFilter()
    val filteredImage = filter.filter(grayscaleImage)

    assert(filteredImage.width == grayscaleImage.width)
    assert(filteredImage.height == grayscaleImage.height)
    assert(filteredImage.width == 0)
    assert(filteredImage.height == 0)
  }
}
