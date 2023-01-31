package filter.defaults

import filters.defaults.IdentityFilter
import org.scalatest.FunSuite

class IdentityFilterTests extends FunSuite {
  def filtered(item: String): String = new IdentityFilter[String].filter(item)

  test("Identity Filter") {
    assert(filtered("abcdef") == "abcdef")
    assert(filtered("") == "")
    assert(filtered(".dsiojds!DS") == ".dsiojds!DS")
    assert(filtered("\\") == "\\")
    assert(filtered("\"FSP3693F") == "\"FSP3693F")
    assert(filtered("%ˇ32429EUFNID 04Y3YRHBD C02V") == "%ˇ32429EUFNID 04Y3YRHBD C02V")
  }
}
