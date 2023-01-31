package importers.fileImporter

import org.scalatest.FunSuite

class JpgImporterTests extends FunSuite {
  test("Importing correct jpg file") {
    val importer = new JpgImporter("src/test/resources/test-image2.jpg")
    val RGBImage = importer.importData()

    assert(RGBImage.width == 100)
    assert(RGBImage.height == 61)
    assert(RGBImage.getPixel(0,0).r == 63)
    assert(RGBImage.getPixel(0,0).g == 72)
    assert(RGBImage.getPixel(0,0).b == 204)
  }

  test("Importing non-existing jpg file") {
    val importer = new JpgImporter("src/test/resources/nonononono.jpg")
    assertThrows[Exception](importer.importData())
  }

  test("Importing corrupted jpg file") {
    val importer = new JpgImporter("src/test/resources/corrupted-image2.jpg")
    assertThrows[Exception](importer.importData())
  }

  test("Importing png with different image type") {
    val importer = new JpgImporter("src/test/resources/corrupted-image1.png")
    assertThrows[IllegalArgumentException](importer.importData())
    val importer2 = new JpgImporter("src/test/resources/corrupted-image1.blala")
    assertThrows[IllegalArgumentException](importer2.importData())
  }
}
