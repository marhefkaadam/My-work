package asciiArtApp.models.pixelGrid

import asciiArtApp.models.pixel.GrayscalePixel
import org.scalatest.FunSuite

class GrayscalePixelGridTests extends FunSuite {
  private val pixels = Seq(
    Seq(GrayscalePixel(120), GrayscalePixel(10.4)),
    Seq(GrayscalePixel(120), GrayscalePixel(10.4)),
    Seq(GrayscalePixel(120), GrayscalePixel(10.4))
  )
  private val pixelGrid = new GrayscalePixelGrid(pixels)

  private val pixels2 = Seq(
    Seq(GrayscalePixel(254.3)),
    Seq(GrayscalePixel(254.3)),
    Seq(GrayscalePixel(0)),
    Seq(GrayscalePixel(100.3))
  )
  private val pixelGrid2 = new GrayscalePixelGrid(pixels2)

  private val pixels3 = Seq(
    Seq(GrayscalePixel(15.81), GrayscalePixel(0.11), GrayscalePixel(123.33), GrayscalePixel(30.3), GrayscalePixel(0.11))
  )
  private val pixelGrid3 = new GrayscalePixelGrid(pixels3)

  test("Correct pixel grid width and height") {
    assert(pixelGrid.width == 2)
    assert(pixelGrid.height == 3)
  }

  test("Get exact pixel by coordinates from pixel grid") {
    assert(pixelGrid.getPixel(0, 0) == pixels(0)(0))
    assert(pixelGrid.getPixel(1, 0) == pixels(1)(0))
    assert(pixelGrid.getPixel(2, 1) == pixels(2)(1))
  }

  test("Get exact pixel of pixel grid out of bounds") {
    assertThrows[IndexOutOfBoundsException](pixelGrid.getPixel(2, 6))
    assertThrows[IndexOutOfBoundsException](pixelGrid.getPixel(10, 3))
    assertThrows[IndexOutOfBoundsException](pixelGrid.getPixel(-2, 10))
  }

  test("Correct pixel grid 2 width and height") {
    assert(pixelGrid2.width == 1)
    assert(pixelGrid2.height == 4)
  }

  test("Get exact pixel by coordinates from pixel grid 2") {
    assert(pixelGrid2.getPixel(0, 0) == pixels2(0)(0))
    assert(pixelGrid2.getPixel(2, 0) == pixels2(2)(0))
    assert(pixelGrid2.getPixel(3, 0) == pixels2(3)(0))
  }

  test("Get exact pixel of pixel grid 2 out of bounds") {
    assertThrows[IndexOutOfBoundsException](pixelGrid2.getPixel(2, 10))
    assertThrows[IndexOutOfBoundsException](pixelGrid2.getPixel(0, 1))
    assertThrows[IndexOutOfBoundsException](pixelGrid2.getPixel(-2, 100))
  }

  test("Correct pixel grid 3 width and height") {
    assert(pixelGrid3.width == 5)
    assert(pixelGrid3.height == 1)
  }

  test("Get exact pixel by coordinates from pixel grid 3") {
    assert(pixelGrid3.getPixel(0, 0) == pixels3(0)(0))
    assert(pixelGrid3.getPixel(0, 1) == pixels3(0)(1))
    assert(pixelGrid3.getPixel(0, 3) == pixels3(0)(3))
  }

  test("Get exact pixel of pixel grid 3 out of bounds") {
    assertThrows[IndexOutOfBoundsException](pixelGrid3.getPixel(1, 6))
    assertThrows[IndexOutOfBoundsException](pixelGrid3.getPixel(0, 5))
    assertThrows[IndexOutOfBoundsException](pixelGrid3.getPixel(-10, 10))
  }

  test("Height and width of empty grayscale pixel grid") {
    val pixels = Seq[Seq[GrayscalePixel]]()
    val pixelGrid = new GrayscalePixelGrid(pixels)
    assert(pixelGrid.height == 0)
    assert(pixelGrid.width == 0)

    val pixels2 = Seq(Seq[GrayscalePixel]())
    val pixelGrid2 = new GrayscalePixelGrid(pixels2)
    assert(pixelGrid2.height == 0)
    assert(pixelGrid2.width == 0)
  }
}
