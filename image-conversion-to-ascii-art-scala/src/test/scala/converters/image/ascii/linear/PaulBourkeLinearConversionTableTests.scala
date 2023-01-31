package converters.image.ascii.linear

import org.scalatest.FunSuite

class PaulBourkeLinearConversionTableTests extends FunSuite {
  private val conversionTable = new PaulBourkeLinearConversionTable()

  test("Convert image using Paul Bourke table") {
    assert(conversionTable.convert(0) == '$')
    assert(conversionTable.convert(3) == '$')
    assert(conversionTable.convert(15) == '8')
    assert(conversionTable.convert(20) == '&')
    assert(conversionTable.convert(125) == 'n')
    assert(conversionTable.convert(165.1) == '1')
    assert(conversionTable.convert(199) == '~')
    assert(conversionTable.convert(222) == 'I')
    assert(conversionTable.convert(255) == ' ')
  }

  test("Convert image using Paul Bourke table - grayscale values <=0 or >=255") {
    assert(conversionTable.convert(0) == '$')
    assert(conversionTable.convert(-1) == '$')
    assert(conversionTable.convert(-123.3) == '$')
    assert(conversionTable.convert(255) == ' ')
    assert(conversionTable.convert(255.1) == ' ')
    assert(conversionTable.convert(298) == ' ')
  }
}
