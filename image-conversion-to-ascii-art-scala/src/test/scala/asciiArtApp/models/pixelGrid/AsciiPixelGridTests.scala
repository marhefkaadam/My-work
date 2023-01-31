package asciiArtApp.models.pixelGrid
import asciiArtApp.models.pixel.AsciiPixel
import org.scalatest.FunSuite

class AsciiPixelGridTests extends FunSuite {
  private val pixels = Seq(
    Seq(AsciiPixel('X'), AsciiPixel('#')),
    Seq(AsciiPixel('X'), AsciiPixel('#')),
    Seq(AsciiPixel('X'), AsciiPixel('#'))
  )
  private val pixelGrid = new AsciiPixelGrid(pixels)

  private val pixels2 = Seq(
    Seq(AsciiPixel('\"'), AsciiPixel('?'), AsciiPixel('\\'), AsciiPixel('#') )
  )
  private val pixelGrid2 = new AsciiPixelGrid(pixels2)

  private val pixels3 = Seq(
    Seq(AsciiPixel('\"')),
    Seq(AsciiPixel('X')),
    Seq(AsciiPixel('X')),
    Seq(AsciiPixel('.'))
  )
  private val pixelGrid3 = new AsciiPixelGrid(pixels3)


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
    assertThrows[IndexOutOfBoundsException](pixelGrid.getPixel(2, 3))
    assertThrows[IndexOutOfBoundsException](pixelGrid.getPixel(10, 3))
    assertThrows[IndexOutOfBoundsException](pixelGrid.getPixel(-2, 10))
  }

  test("Correct pixel grid 2 width and height") {
    assert(pixelGrid2.width == 4)
    assert(pixelGrid2.height == 1)
  }

  test("Get exact pixel by coordinates from pixel grid 2") {
    assert(pixelGrid2.getPixel(0, 0) == pixels2(0)(0))
    assert(pixelGrid2.getPixel(0, 1) == pixels2(0)(1))
    assert(pixelGrid2.getPixel(0, 3) == pixels2(0)(3))
  }

  test("Get exact pixel of pixel grid 2 out of bounds") {
    assertThrows[IndexOutOfBoundsException](pixelGrid2.getPixel(0, 4))
    assertThrows[IndexOutOfBoundsException](pixelGrid2.getPixel(1, 10))
    assertThrows[IndexOutOfBoundsException](pixelGrid2.getPixel(-2, 10))
  }

  test("Correct pixel grid 3 width and height") {
    assert(pixelGrid3.width == 1)
    assert(pixelGrid3.height == 4)
  }

  test("Get exact pixel by coordinates from pixel grid 3") {
    assert(pixelGrid3.getPixel(0, 0) == pixels3(0)(0))
    assert(pixelGrid3.getPixel(1, 0) == pixels3(1)(0))
    assert(pixelGrid3.getPixel(3, 0) == pixels3(3)(0))
  }

  test("Get exact pixel of pixel grid 3 out of bounds") {
    assertThrows[IndexOutOfBoundsException](pixelGrid3.getPixel(4, 0))
    assertThrows[IndexOutOfBoundsException](pixelGrid3.getPixel(10, 1))
    assertThrows[IndexOutOfBoundsException](pixelGrid3.getPixel(-2, 10))
  }

  test("Height and width of empty ascii pixel grid") {
    val pixels = Seq[Seq[AsciiPixel]]()
    val pixelGrid = new AsciiPixelGrid(pixels)
    assert(pixelGrid.height == 0)
    assert(pixelGrid.width == 0)

    val pixels2 = Seq(Seq[AsciiPixel]())
    val pixelGrid2 = new AsciiPixelGrid(pixels2)
    assert(pixelGrid2.height == 0)
    assert(pixelGrid2.width == 0)
  }
}
