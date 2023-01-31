package converters.image.ascii.linear

import org.scalatest.FunSuite

class CustomLinearConversionTableTests extends FunSuite {
  test("Convert image using custom linear table") {
    val conversionTable = new CustomLinearConversionTable("ABCDEFGHIJ!".toList)

    assert(conversionTable.convert(0) == 'A')
    assert(conversionTable.convert(3) == 'A')
    assert(conversionTable.convert(40) == 'B')
    assert(conversionTable.convert(125.33) == 'F')
    assert(conversionTable.convert(155.12) == 'G')
    assert(conversionTable.convert(178.34) == 'H')
    assert(conversionTable.convert(200) == 'I')
    assert(conversionTable.convert(230.33) == 'J')
    assert(conversionTable.convert(255) == '!')
    assert(conversionTable.convert(270) == '!')
  }

  test("Convert image using custom linear table - one symbol") {
    val conversionTable = new CustomLinearConversionTable(".".toList)

    assert(conversionTable.convert(0) == '.')
    assert(conversionTable.convert(3) == '.')
    assert(conversionTable.convert(20) == '.')
    assert(conversionTable.convert(125.33) == '.')
    assert(conversionTable.convert(155.12) == '.')
    assert(conversionTable.convert(255) == '.')
    assert(conversionTable.convert(270) == '.')
  }

  test("Empty custom linear conversion table - grayscale values <=0 or >=255") {
    val conversionTable = new CustomLinearConversionTable(".#!".toList)

    assert(conversionTable.convert(0) == '.')
    assert(conversionTable.convert(-1) == '.')
    assert(conversionTable.convert(-123.3) == '.')
    assert(conversionTable.convert(255) == '!')
    assert(conversionTable.convert(255.1) == '!')
    assert(conversionTable.convert(298) == '!')
  }

  test("Empty custom linear conversion table") {
    assertThrows[IllegalArgumentException](new CustomLinearConversionTable("".toList))
  }
}
