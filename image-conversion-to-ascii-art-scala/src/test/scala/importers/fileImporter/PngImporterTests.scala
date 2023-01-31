package importers.fileImporter

import org.scalatest.FunSuite

class PngImporterTests extends FunSuite {
  test("Importing correct png file") {
    val importer = new PngImporter("src/test/resources/test-image1.png")
    val RGBImage = importer.importData()

    assert(RGBImage.width == 100)
    assert(RGBImage.height == 61)
    assert(RGBImage.getPixel(0,0).r == 255)
    assert(RGBImage.getPixel(0,0).g == 255)
    assert(RGBImage.getPixel(0,0).b == 255)
  }

  test("Importing non-existing png file") {
    val importer = new PngImporter("src/test/resources/nonononono.png")
    assertThrows[Exception](importer.importData())
  }

  test("Importing corrupted png file") {
    val importer = new PngImporter("src/test/resources/corrupted-image1.png")
    assertThrows[Exception](importer.importData())
  }

  test("Importing png with different image type") {
    val importer = new PngImporter("src/test/resources/corrupted-image1.jpeg")
    assertThrows[IllegalArgumentException](importer.importData())
    val importer2 = new PngImporter("src/test/resources/corrupted-image1.blala")
    assertThrows[IllegalArgumentException](importer2.importData())
  }
}
