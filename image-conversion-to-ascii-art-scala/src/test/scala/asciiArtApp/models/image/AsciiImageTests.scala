package asciiArtApp.models.image

import asciiArtApp.models.pixel.AsciiPixel
import asciiArtApp.models.pixelGrid.AsciiPixelGrid
import org.scalatest.FunSuite

class AsciiImageTests extends FunSuite {
  private val pixels = Seq(
    Seq(AsciiPixel('X'), AsciiPixel('#')),
    Seq(AsciiPixel('X'), AsciiPixel('#')),
    Seq(AsciiPixel('X'), AsciiPixel('#'))
  )
  private val image = new AsciiImage(new AsciiPixelGrid(pixels))

  private val pixels2 = Seq(
    Seq(AsciiPixel('\"'), AsciiPixel('?'), AsciiPixel('\\'), AsciiPixel('#') )
  )
  private val image2 = new AsciiImage(new AsciiPixelGrid(pixels2))

  private val pixels3 = Seq(
    Seq(AsciiPixel('\"')),
    Seq(AsciiPixel('X')),
    Seq(AsciiPixel('X')),
    Seq(AsciiPixel('.'))
  )
  private val image3 = new AsciiImage(new AsciiPixelGrid(pixels3))

  test("Correct ascii image width and height") {
    assert(image.width == 2)
    assert(image.height == 3)
  }

  test("Get exact pixel from ascii image by coordinates") {
    assert(image.getPixel(1, 1) == AsciiPixel('#'))
    assert(image.getPixel(2, 0) == AsciiPixel('X'))
    assert(image.getPixel(2, 1) == AsciiPixel('#'))
  }

  test("Get exact pixel from ascii image out of bounds") {
    assertThrows[IndexOutOfBoundsException](image.getPixel(2, 6))
    assertThrows[IndexOutOfBoundsException](image.getPixel(10, 3))
    assertThrows[IndexOutOfBoundsException](image.getPixel(-2, 10))
  }

  test("Correct ascii image2 width and height") {
    assert(image2.width == 4)
    assert(image2.height == 1)
  }

  test("Get exact pixel from ascii image2 by coordinates") {
    assert(image2.getPixel(0, 0) == AsciiPixel('\"'))
    assert(image2.getPixel(0, 1) == AsciiPixel('?'))
    assert(image2.getPixel(0, 3) == AsciiPixel('#'))
  }

  test("Get exact pixel from ascii image2 out of bounds") {
    assertThrows[IndexOutOfBoundsException](image2.getPixel(0, 4))
    assertThrows[IndexOutOfBoundsException](image2.getPixel(1, 10))
    assertThrows[IndexOutOfBoundsException](image2.getPixel(-2, 10))
  }

  test("Correct ascii image3 width and height") {
    assert(image3.width == 1)
    assert(image3.height == 4)
  }

  test("Get exact pixel from ascii image3 by coordinates") {
    assert(image3.getPixel(0, 0) == AsciiPixel('\"'))
    assert(image3.getPixel(1, 0) == AsciiPixel('X'))
    assert(image3.getPixel(3, 0) == AsciiPixel('.'))
  }

  test("Get exact pixel from ascii image3 out of bounds") {
    assertThrows[IndexOutOfBoundsException](image3.getPixel(4, 0))
    assertThrows[IndexOutOfBoundsException](image3.getPixel(10, 1))
    assertThrows[IndexOutOfBoundsException](image3.getPixel(-2, 10))
  }

  test("Height and width of empty ascii image") {
    val pixels = Seq[Seq[AsciiPixel]]()
    val image = new AsciiImage(new AsciiPixelGrid(pixels))
    assert(image.height == 0)
    assert(image.width == 0)

    val pixels2 = Seq(Seq[AsciiPixel]())
    val image2 = new AsciiImage(new AsciiPixelGrid(pixels2))
    assert(image2.height == 0)
    assert(image2.width == 0)
  }
}
