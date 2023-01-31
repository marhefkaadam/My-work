package asciiArtApp.facades

import asciiArtApp.models.image.{AsciiImage, GrayscaleImage, RGBImage}
import converters.image.ImageConverter
import converters.string.AsciiToStringConverter
import exporters.text.TextExporter
import filters.image.ImageFilter
import importers.Importer

/**
 * Class uses facade pattern to execute steps of the application in defined order.
 */
class AsciiArtFacade {
    /**
     * Method takes arguments and applies them in defined order which makes
     * the whole transformation of and image form importing through converting to grayscale,
     * filtering, converting to ascii and in the end exporting.
     * @param importer - object used for importing/generating RGB image data
     * @param RGBToGrayscaleConverter - object used for converting RGB image to grayscale image
     * @param filter - object used for filtering imported/generated image, e.g. inverting image's pixel values
     * @param grayscaleToAsciiConverter - object used for converting grayscale image to ASCII image based on
     *                                  defined linear/non-linear conversion table
     * @param exporters - sequence of objects used for exporting ASCII image to text by specified method
     */
    def importFilterConvertExport(importer: Importer[RGBImage],
                                  RGBToGrayscaleConverter: ImageConverter[RGBImage, GrayscaleImage],
                                  filter: ImageFilter[GrayscaleImage],
                                  grayscaleToAsciiConverter: ImageConverter[GrayscaleImage, AsciiImage],
                                  exporters: Seq[TextExporter]) :Unit = {
        // importing image
        val importedImage = importer.importData()

        // converting to grayscale
        var grayscaleImage = RGBToGrayscaleConverter.convert(importedImage)

        // applying of filters
        grayscaleImage = filter.filter(grayscaleImage)

        // converting to ascii
        val asciiImage = grayscaleToAsciiConverter.convert(grayscaleImage)

        // exporting image
        val convertToString = new AsciiToStringConverter()
        val output = convertToString.convert(asciiImage)
        for(e <- exporters) {
            e.export(output)
        }
    }
}
