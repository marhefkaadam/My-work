package asciiArtApp.models.pixelGrid

import asciiArtApp.models.pixel.RGBPixel
import org.scalatest.FunSuite

class RGBPixelGridTests extends FunSuite {
  private val pixels = Seq(
    Seq(RGBPixel(12, 0, 111), RGBPixel(0, 255, 10)),
    Seq(RGBPixel(12, 0, 111), RGBPixel(0, 255, 10)),
    Seq(RGBPixel(12, 0, 111), RGBPixel(0, 255, 10))
  )
  private val pixelGrid = new RGBPixelGrid(pixels)

  private val pixels2 = Seq(
    Seq(RGBPixel(12, 0, 111), RGBPixel(0, 0, 1), RGBPixel(255, 250, 23), RGBPixel(30, 30, 1), RGBPixel(0, 0, 1))
  )
  private val pixelGrid2 = new RGBPixelGrid(pixels2)

  private val pixels3 = Seq(
    Seq(RGBPixel(255, 255, 123)),
    Seq(RGBPixel(255, 1, 255)),
    Seq(RGBPixel(10, 1, 9)),
    Seq(RGBPixel(10, 255, 255)),
  )
  private val pixelGrid3 = new RGBPixelGrid(pixels3)

  test("Correct pixel grid width and height") {
    assert(pixelGrid.width == 2)
    assert(pixelGrid.height == 3)
  }

  test("Get exact pixel from pixel grid by coordinates") {
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
    assert(pixelGrid2.width == 5)
    assert(pixelGrid2.height == 1)
  }

  test("Get exact pixel from pixel grid 2 by coordinates") {
    assert(pixelGrid2.getPixel(0, 0) == pixels2(0)(0))
    assert(pixelGrid2.getPixel(0, 2) == pixels2(0)(2))
    assert(pixelGrid2.getPixel(0, 4) == pixels2(0)(4))
  }

  test("Get exact pixel of pixel grid 2 out of bounds") {
    assertThrows[IndexOutOfBoundsException](pixelGrid2.getPixel(1, 15))
    assertThrows[IndexOutOfBoundsException](pixelGrid2.getPixel(0, 5))
    assertThrows[IndexOutOfBoundsException](pixelGrid2.getPixel(-2, 150))
  }

  test("Correct pixel grid 3 width and height") {
    assert(pixelGrid3.width == 1)
    assert(pixelGrid3.height == 4)
  }

  test("Get exact pixel from pixel grid 3 by coordinates") {
    assert(pixelGrid3.getPixel(0, 0) == pixels3(0)(0))
    assert(pixelGrid3.getPixel(2, 0) == pixels3(2)(0))
    assert(pixelGrid3.getPixel(3, 0) == pixels3(3)(0))
  }

  test("Get exact pixel of pixel grid 3 out of bounds") {
    assertThrows[IndexOutOfBoundsException](pixelGrid3.getPixel(4, 15))
    assertThrows[IndexOutOfBoundsException](pixelGrid3.getPixel(0, 1))
    assertThrows[IndexOutOfBoundsException](pixelGrid3.getPixel(-20, 150))
  }

  test("Height and width of empty rgb pixel grid") {
    val pixels = Seq[Seq[RGBPixel]]()
    val pixelGrid = new RGBPixelGrid(pixels)
    assert(pixelGrid.height == 0)
    assert(pixelGrid.width == 0)

    val pixels2 = Seq(Seq[RGBPixel]())
    val pixelGrid2 = new RGBPixelGrid(pixels2)
    assert(pixelGrid2.height == 0)
    assert(pixelGrid2.width == 0)
  }
}
