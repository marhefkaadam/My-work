package asciiArtApp.models.image

import asciiArtApp.models.pixel.GrayscalePixel
import asciiArtApp.models.pixelGrid.GrayscalePixelGrid
import org.scalatest.FunSuite

class GrayscaleImageTests extends FunSuite {
  private val pixels = Seq(
    Seq(GrayscalePixel(120), GrayscalePixel(10.4)),
    Seq(GrayscalePixel(120), GrayscalePixel(10.4)),
    Seq(GrayscalePixel(120), GrayscalePixel(10.4))
  )
  private val image = new GrayscaleImage(new GrayscalePixelGrid(pixels))

  private val pixels2 = Seq(
    Seq(GrayscalePixel(254.3)),
    Seq(GrayscalePixel(254.3)),
    Seq(GrayscalePixel(0)),
    Seq(GrayscalePixel(100.3))
  )
  private val image2 = new GrayscaleImage(new GrayscalePixelGrid(pixels2))

  private val pixels3 = Seq(
    Seq(GrayscalePixel(15.81), GrayscalePixel(0.11), GrayscalePixel(123.33), GrayscalePixel(30.3), GrayscalePixel(0.11))
  )
  private val image3 = new GrayscaleImage(new GrayscalePixelGrid(pixels3))


  test("Correct grayscale image width and height") {
    assert(image.width == 2)
    assert(image.height == 3)
  }

  test("Get exact pixel from grayscale image by coordinates") {
    assert(image.getPixel(1, 1) == GrayscalePixel(10.4))
    assert(image.getPixel(2, 0) == GrayscalePixel(120))
    assert(image.getPixel(2, 1) == GrayscalePixel(10.4))
  }

  test("Get exact pixel from grayscale image out of bounds") {
    assertThrows[IndexOutOfBoundsException](image.getPixel(2, 6))
    assertThrows[IndexOutOfBoundsException](image.getPixel(10, 3))
    assertThrows[IndexOutOfBoundsException](image.getPixel(-2, 10))
  }

  test("Correct grayscale image2 width and height") {
    assert(image2.width == 1)
    assert(image2.height == 4)
  }

  test("Get exact pixel from grayscale image2 by coordinates") {
    assert(image2.getPixel(0, 0) == GrayscalePixel(254.3))
    assert(image2.getPixel(2, 0) == GrayscalePixel(0))
    assert(image2.getPixel(3, 0) == GrayscalePixel(100.3))
  }

  test("Get exact pixel from grayscale image2 out of bounds") {
    assertThrows[IndexOutOfBoundsException](image2.getPixel(2, 10))
    assertThrows[IndexOutOfBoundsException](image2.getPixel(0, 1))
    assertThrows[IndexOutOfBoundsException](image2.getPixel(-2, 100))
  }

  test("Correct grayscale image3 width and height") {
    assert(image3.width == 5)
    assert(image3.height == 1)
  }

  test("Get exact pixel from grayscale image3 by coordinates") {
    assert(image3.getPixel(0, 0) == GrayscalePixel(15.81))
    assert(image3.getPixel(0, 1) == GrayscalePixel(0.11))
    assert(image3.getPixel(0, 3) == GrayscalePixel(30.3))
  }

  test("Get exact pixel from grayscale image3 out of bounds") {
    assertThrows[IndexOutOfBoundsException](image3.getPixel(1, 6))
    assertThrows[IndexOutOfBoundsException](image3.getPixel(0, 5))
    assertThrows[IndexOutOfBoundsException](image3.getPixel(-10, 10))
  }

  test("Height and width of empty grayscale image") {
    val pixels = Seq[Seq[GrayscalePixel]]()
    val image = new GrayscaleImage(new GrayscalePixelGrid(pixels))
    assert(image.height == 0)
    assert(image.width == 0)

    val pixels2 = Seq(Seq[GrayscalePixel]())
    val image2 = new GrayscaleImage(new GrayscalePixelGrid(pixels2))
    assert(image2.height == 0)
    assert(image2.width == 0)
  }
}
