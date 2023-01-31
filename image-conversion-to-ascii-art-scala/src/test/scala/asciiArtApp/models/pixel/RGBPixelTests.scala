package asciiArtApp.models.pixel

import org.scalatest.FunSuite

class RGBPixelTests extends FunSuite {
  test("Incorrect RGB pixel values") {
    assertThrows[IllegalArgumentException](RGBPixel(0, 1, 300))
    assertThrows[IllegalArgumentException](RGBPixel(5, 1, -100))
    assertThrows[IllegalArgumentException](RGBPixel(10, -1, 100))
    assertThrows[IllegalArgumentException](RGBPixel(0, 400, 100))
    assertThrows[IllegalArgumentException](RGBPixel(256, 1, 300))
    assertThrows[IllegalArgumentException](RGBPixel(-10, 1, 300))
  }

  test("Correct instance of RGB pixel") {
    val pixel = RGBPixel(0, 120, 255)
    assert(pixel.r == 0 && pixel.g == 120 && pixel.b == 255)
    val pixel2 = RGBPixel(120, 233, 1)
    assert(pixel2.r == 120 && pixel2.g == 233 && pixel2.b == 1)
    val pixel3 = RGBPixel(255, 10, 1)
    assert(pixel3.r == 255 && pixel3.g == 10 && pixel3.b == 1)
    val pixel4 = RGBPixel(234, 15, 99)
    assert(pixel4.r == 234 && pixel4.g == 15 && pixel4.b == 99)
    val pixel5 = RGBPixel(0, 0, 0)
    assert(pixel5.r == 0 && pixel5.g == 0 && pixel5.b == 0)
  }
}
