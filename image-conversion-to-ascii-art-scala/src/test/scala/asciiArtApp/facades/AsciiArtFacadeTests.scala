package asciiArtApp.facades

import asciiArtApp.models.image.{AsciiImage, GrayscaleImage, RGBImage}
import asciiArtApp.models.pixel.{AsciiPixel, GrayscalePixel, RGBPixel}
import asciiArtApp.models.pixelGrid.{AsciiPixelGrid, GrayscalePixelGrid, RGBPixelGrid}
import converters.image.ImageConverter
import exporters.text.TextExporter
import filters.image.ImageFilter
import importers.Importer
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.{doNothing, reset, times, verify, when}
import org.mockito.MockitoSugar.mock
import org.scalatest.{BeforeAndAfter, FunSuite}


class AsciiArtFacadeTests extends FunSuite with BeforeAndAfter {
  private val importer = mock[Importer[RGBImage]]
  private val filter = mock[ImageFilter[GrayscaleImage]]
  private val RGBToGrayscaleConverter = mock[ImageConverter[RGBImage, GrayscaleImage]]
  private val grayscaleToAsciiConverter = mock[ImageConverter[GrayscaleImage, AsciiImage]]
  private val exporter = mock[TextExporter]
  private val facade = new AsciiArtFacade()

  private val RGBPixels = Seq(
    Seq(RGBPixel(12, 0, 111))
  )
  private val RGBImage = new RGBImage(new RGBPixelGrid(RGBPixels))

  private val grayscalePixels = Seq(
    Seq(GrayscalePixel(15.81))
  )
  private val grayscaleImage = new GrayscaleImage(new GrayscalePixelGrid(grayscalePixels))

  private val asciiPixels = Seq(
    Seq(AsciiPixel('#'))
  )
  private val asciiImage = new AsciiImage(new AsciiPixelGrid(asciiPixels))

  before {
    reset(importer)
    reset(RGBToGrayscaleConverter)
    reset(filter)
    reset(grayscaleToAsciiConverter)
    reset(exporter)

    when(importer.importData()).thenReturn(RGBImage)
    when(RGBToGrayscaleConverter.convert(RGBImage)).thenReturn(grayscaleImage)
    when(filter.filter(grayscaleImage)).thenReturn(grayscaleImage)
    when(grayscaleToAsciiConverter.convert(grayscaleImage)).thenReturn(asciiImage)
    doNothing().when(exporter).export(anyString())
  }

  test("Facade calls import method") {
    facade.importFilterConvertExport(importer, RGBToGrayscaleConverter, filter, grayscaleToAsciiConverter, Seq(exporter))
    verify(importer, times(1)).importData()
  }

  test("Facade calls convert RGB image to grayscale image method") {
    facade.importFilterConvertExport(importer, RGBToGrayscaleConverter, filter, grayscaleToAsciiConverter, Seq(exporter))
    verify(RGBToGrayscaleConverter, times(1)).convert(RGBImage)
  }

  test("Facade calls method for applying of filters") {
    facade.importFilterConvertExport(importer, RGBToGrayscaleConverter, filter, grayscaleToAsciiConverter, Seq(exporter))
    verify(filter, times(1)).filter(grayscaleImage)
  }

  test("Facade calls grayscale image to ascii image conversion method") {
    facade.importFilterConvertExport(importer, RGBToGrayscaleConverter, filter, grayscaleToAsciiConverter, Seq(exporter))
    verify(grayscaleToAsciiConverter, times(1)).convert(grayscaleImage)
  }

  test("Facade calls method for exporting") {
    facade.importFilterConvertExport(importer, RGBToGrayscaleConverter, filter, grayscaleToAsciiConverter, Seq(exporter))
    verify(exporter, times(1)).export(anyString())
  }
}
