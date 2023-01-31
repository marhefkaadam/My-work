package asciiArtApp.models.pixel

import org.scalatest.FunSuite

class AsciiPixelTests extends FunSuite {
  test("Incorrect Ascii pixel values") {
    assertThrows[IllegalArgumentException](AsciiPixel(128.toChar))
    assertThrows[IllegalArgumentException](AsciiPixel(255.toChar))
    assertThrows[IllegalArgumentException](AsciiPixel(2323.toChar))
    assertThrows[IllegalArgumentException](AsciiPixel(129.toChar))
    assertThrows[IllegalArgumentException](AsciiPixel(200.toChar))
  }

  test("Correct instance of Ascii pixel") {
    val pixel = AsciiPixel('X')
    assert(pixel.symbol == 'X')
    val pixel2 = AsciiPixel(127.toChar)
    assert(pixel2.symbol == 127.toChar)
    val pixel3 = AsciiPixel(0.toChar)
    assert(pixel3.symbol == 0.toChar)
    val pixel4 = AsciiPixel('\\')
    assert(pixel4.symbol == '\\')
    val pixel5 = AsciiPixel('F')
    assert(pixel5.symbol == 70.toChar)
  }
}
