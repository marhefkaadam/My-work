package asciiArtApp.models.image

import asciiArtApp.models.pixel.RGBPixel
import asciiArtApp.models.pixelGrid.RGBPixelGrid
import org.scalatest.FunSuite

class RGBImageTests extends FunSuite {
  private val pixels = Seq(
    Seq(RGBPixel(12, 0, 111), RGBPixel(0, 255, 10)),
    Seq(RGBPixel(12, 0, 111), RGBPixel(0, 255, 10)),
    Seq(RGBPixel(12, 0, 111), RGBPixel(0, 255, 10))
  )
  private val image = new RGBImage(new RGBPixelGrid(pixels))

  private val pixels2 = Seq(
    Seq(RGBPixel(12, 0, 111), RGBPixel(0, 0, 1), RGBPixel(255, 250, 23), RGBPixel(30, 30, 1), RGBPixel(0, 0, 1))
  )
  private val image2 = new RGBImage(new RGBPixelGrid(pixels2))

  private val pixels3 = Seq(
    Seq(RGBPixel(255, 255, 123)),
    Seq(RGBPixel(255, 1, 255)),
    Seq(RGBPixel(10, 1, 9)),
    Seq(RGBPixel(10, 255, 255)),
  )
  private val image3 = new RGBImage(new RGBPixelGrid(pixels3))

  test("Correct rgb image width and height") {
    assert(image.width == 2)
    assert(image.height == 3)
  }

  test("Get exact pixel from rgb image by coordinates") {
    assert(image.getPixel(1, 1) == RGBPixel(0, 255, 10))
    assert(image.getPixel(2, 0) == RGBPixel(12, 0, 111))
    assert(image.getPixel(2, 1) == RGBPixel(0, 255, 10))
  }

  test("Get exact pixel from rgb image out of bounds") {
    assertThrows[IndexOutOfBoundsException](image.getPixel(2, 6))
    assertThrows[IndexOutOfBoundsException](image.getPixel(10, 3))
    assertThrows[IndexOutOfBoundsException](image.getPixel(-2, 10))
  }

  test("Correct rgb image2 width and height") {
    assert(image2.width == 5)
    assert(image2.height == 1)
  }

  test("Get exact pixel from rgb image2 by coordinates") {
    assert(image2.getPixel(0, 0) == RGBPixel(12, 0, 111))
    assert(image2.getPixel(0, 2) == RGBPixel(255, 250, 23))
    assert(image2.getPixel(0, 4) == RGBPixel(0, 0, 1))
  }

  test("Get exact pixel from rgb image2 out of bounds") {
    assertThrows[IndexOutOfBoundsException](image2.getPixel(1, 15))
    assertThrows[IndexOutOfBoundsException](image2.getPixel(0, 5))
    assertThrows[IndexOutOfBoundsException](image2.getPixel(-2, 150))
  }

  test("Correct rgb image3 width and height") {
    assert(image3.width == 1)
    assert(image3.height == 4)
  }

  test("Get exact pixel from rgb image3 by coordinates") {
    assert(image3.getPixel(0, 0) == RGBPixel(255 ,255, 123))
    assert(image3.getPixel(2, 0) == RGBPixel(10, 1, 9))
    assert(image3.getPixel(3, 0) == RGBPixel(10, 255, 255))
  }

  test("Get exact pixel from rgb image3 out of bounds") {
    assertThrows[IndexOutOfBoundsException](image3.getPixel(4, 15))
    assertThrows[IndexOutOfBoundsException](image3.getPixel(0, 1))
    assertThrows[IndexOutOfBoundsException](image3.getPixel(-20, 150))
  }

  test("Height and width of empty rgb image") {
    val pixels = Seq[Seq[RGBPixel]]()
    val image = new RGBImage(new RGBPixelGrid(pixels))
    assert(image.height == 0)
    assert(image.width == 0)

    val pixels2 = Seq(Seq[RGBPixel]())
    val image2 = new RGBImage(new RGBPixelGrid(pixels2))
    assert(image2.height == 0)
    assert(image2.width == 0)
  }
}
