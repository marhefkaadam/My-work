package asciiArtApp.models.pixel

import org.scalatest.FunSuite

class GrayscalePixelTests extends FunSuite {
  test("Incorrect grayscale pixel values") {
    assertThrows[IllegalArgumentException](GrayscalePixel(-1))
    assertThrows[IllegalArgumentException](GrayscalePixel(-100))
    assertThrows[IllegalArgumentException](GrayscalePixel(256))
    assertThrows[IllegalArgumentException](GrayscalePixel(400))
    assertThrows[IllegalArgumentException](GrayscalePixel(255.23))
    assertThrows[IllegalArgumentException](GrayscalePixel(-0.11))
  }

  test("Correct instance of Grayscale pixel") {
    val pixel = GrayscalePixel(250)
    assert(pixel.grayscaleValue == 250)
    val pixel2 = GrayscalePixel(0)
    assert(pixel2.grayscaleValue == 0)
    val pixel3 = GrayscalePixel(23.4)
    assert(pixel3.grayscaleValue == 23.4)
    val pixel4 = GrayscalePixel(123.66)
    assert(pixel4.grayscaleValue == 123.66)
    val pixel5 = GrayscalePixel(249.99)
    assert(pixel5.grayscaleValue == 249.99)
  }
}
