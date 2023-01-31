package converters.image.ascii.nonLinear

import org.scalatest.FunSuite

class CrazyNonLinearTransformationTableTests extends FunSuite {
  private val conversionTable = new CrazyNonLinearConversionTable()

  test("Convert image using crazy non linear table") {
    assert(conversionTable.convert(0) == ':')
    assert(conversionTable.convert(24) == '.')
    assert(conversionTable.convert(67) == 'A')
    assert(conversionTable.convert(152) == 'X')
    assert(conversionTable.convert(169) == 'S')
    assert(conversionTable.convert(172.54) == '{')
    assert(conversionTable.convert(199) == '!')
    assert(conversionTable.convert(123.33) == ':')
    assert(conversionTable.convert(11.23) == '&')
  }

  test("Convert image using crazy non linear - grayscale values <=0 or >=255") {
    assert(conversionTable.convert(0) == ':')
    assert(conversionTable.convert(-1) == ':')
    assert(conversionTable.convert(-123.3) == ':')
    assert(conversionTable.convert(255) == ':')
    assert(conversionTable.convert(255.1) == ':')
    assert(conversionTable.convert(298) == ':')
  }
}
