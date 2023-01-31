package filter.image.mixed

import asciiArtApp.models.image.GrayscaleImage
import asciiArtApp.models.pixel.GrayscalePixel
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid
import filters.image.defaults.ImageIdentityFilter
import filters.image.mixed.ImageMixedFilter
import filters.image.specific.brightness.BrightnessFilter
import filters.image.specific.flip.{HorizontalFlipFilter, VerticalFlipFilter}
import filters.image.specific.invert.InvertFilter
import org.scalatest.FunSuite

class ImageMixedFilterTests extends FunSuite {
  test("Mixed filter - image identity filter") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(120), GrayscalePixel(10.4)),
      Seq(GrayscalePixel(120), GrayscalePixel(10.4)),
      Seq(GrayscalePixel(120), GrayscalePixel(10.4))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new ImageMixedFilter(
      Seq(new ImageIdentityFilter())
    )
    val filteredImage = filter.filter(grayscaleImage)

    assert(grayscaleImage.width == filteredImage.width)
    assert(grayscaleImage.height == filteredImage.height)

    for (x <- 0 until filteredImage.height) {
      for (y <- 0 until filteredImage.width) {
        assert(grayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Mixed filter - horizontal flip filter") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(15.81), GrayscalePixel(11.0)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(18.51), GrayscalePixel(0.11))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(18.51), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(0.11)),
      Seq(GrayscalePixel(15.81), GrayscalePixel(11.0))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))


    val filter = new ImageMixedFilter(
      Seq(new HorizontalFlipFilter())
    )
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Mixed filter - vertical flip filter") {
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

    val filter = new ImageMixedFilter(
      Seq(new VerticalFlipFilter())
    )
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Mixed filter - vertical and horizontal flip filter") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(0), GrayscalePixel(255), GrayscalePixel(255)),
      Seq(GrayscalePixel(0), GrayscalePixel(255), GrayscalePixel(255)),
      Seq(GrayscalePixel(12.34), GrayscalePixel(0), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(255), GrayscalePixel(0), GrayscalePixel(12.34)),
      Seq(GrayscalePixel(255), GrayscalePixel(255), GrayscalePixel(0)),
      Seq(GrayscalePixel(255), GrayscalePixel(255), GrayscalePixel(0))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))

    val filter = new ImageMixedFilter(
      Seq(new VerticalFlipFilter(), new HorizontalFlipFilter())
    )
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }


  test("Mixed filter - vertical and vertical flip filter") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(0), GrayscalePixel(255), GrayscalePixel(255)),
      Seq(GrayscalePixel(0), GrayscalePixel(255), GrayscalePixel(255)),
      Seq(GrayscalePixel(12.34), GrayscalePixel(0), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new ImageMixedFilter(
      Seq(new VerticalFlipFilter(), new VerticalFlipFilter())
    )
    val filteredImage = filter.filter(grayscaleImage)

    assert(grayscaleImage.width == filteredImage.width)
    assert(grayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(grayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Mixed filter - vertical flip and invert filter") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(0), GrayscalePixel(255), GrayscalePixel(255)),
      Seq(GrayscalePixel(0), GrayscalePixel(255), GrayscalePixel(255)),
      Seq(GrayscalePixel(12.34), GrayscalePixel(0), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(0), GrayscalePixel(0), GrayscalePixel(255)),
      Seq(GrayscalePixel(0), GrayscalePixel(0), GrayscalePixel(255)),
      Seq(GrayscalePixel(0), GrayscalePixel(255), GrayscalePixel(242.66))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))

    val filter = new ImageMixedFilter(
      Seq(new VerticalFlipFilter(), new InvertFilter())
    )
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Mixed filter - brightness and invert filter") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(0), GrayscalePixel(240), GrayscalePixel(240)),
      Seq(GrayscalePixel(0), GrayscalePixel(100), GrayscalePixel(255)),
      Seq(GrayscalePixel(12.34), GrayscalePixel(0), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(235), GrayscalePixel(0), GrayscalePixel(0)),
      Seq(GrayscalePixel(235), GrayscalePixel(135), GrayscalePixel(0)),
      Seq(GrayscalePixel(222.66), GrayscalePixel(235), GrayscalePixel(0))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))

    val filter = new ImageMixedFilter(
      Seq(new BrightnessFilter(+20), new InvertFilter())
    )
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Mixed filter - brightness and invert filter, horizontal flip") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(0), GrayscalePixel(240), GrayscalePixel(240)),
      Seq(GrayscalePixel(0), GrayscalePixel(100), GrayscalePixel(255)),
      Seq(GrayscalePixel(12.34), GrayscalePixel(0), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(222.66), GrayscalePixel(235), GrayscalePixel(0)),
      Seq(GrayscalePixel(235), GrayscalePixel(135), GrayscalePixel(0)),
      Seq(GrayscalePixel(235), GrayscalePixel(0), GrayscalePixel(0))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))

    val filter = new ImageMixedFilter(
      Seq(new BrightnessFilter(+20), new InvertFilter(), new HorizontalFlipFilter())
    )
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Mixed filter - identity, brightness, invert, horizontal and vertical flip filter") {
    val grayscalePixels = Seq(
      Seq(GrayscalePixel(0), GrayscalePixel(240), GrayscalePixel(240)),
      Seq(GrayscalePixel(0), GrayscalePixel(100), GrayscalePixel(255)),
      Seq(GrayscalePixel(12.34), GrayscalePixel(0), GrayscalePixel(255))
    )
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val refGrayscalePixels = Seq(
      Seq(GrayscalePixel(0), GrayscalePixel(235), GrayscalePixel(222.66)),
      Seq(GrayscalePixel(0), GrayscalePixel(135), GrayscalePixel(235)),
      Seq(GrayscalePixel(0), GrayscalePixel(0), GrayscalePixel(235))
    )
    val refGrayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(refGrayscalePixels))

    val filter = new ImageMixedFilter(
      Seq(new BrightnessFilter(+20),
          new InvertFilter(),
          new HorizontalFlipFilter(),
          new VerticalFlipFilter(),
          new ImageIdentityFilter())
    )
    val filteredImage = filter.filter(grayscaleImage)

    assert(refGrayscaleImage.width == filteredImage.width)
    assert(refGrayscaleImage.height == filteredImage.height)

    for(x <- 0 until filteredImage.height) {
      for(y <- 0 until filteredImage.width) {
        assert(refGrayscaleImage.getPixel(x, y).grayscaleValue == filteredImage.getPixel(x, y).grayscaleValue)
      }
    }
  }

  test("Mixed filter - all filters with empty image") {
    val grayscalePixels = Seq[Seq[GrayscalePixel]]()
    val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

    val filter = new ImageMixedFilter(
      Seq(new BrightnessFilter(+20),
        new InvertFilter(),
        new HorizontalFlipFilter(),
        new VerticalFlipFilter(),
        new ImageIdentityFilter())
    )
    val filteredImage = filter.filter(grayscaleImage)

    assert(filteredImage.width == grayscaleImage.width)
    assert(filteredImage.height == grayscaleImage.height)
    assert(filteredImage.width == 0)
    assert(filteredImage.height == 0)
  }
}
