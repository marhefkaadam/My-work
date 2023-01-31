package converters.image.ascii.linear

/**
 * Class stores value of Paul Bourke linear conversion table which is used for conversion of image.
 * Paul Bourke table - http://paulbourke.net/dataformats/asciiart/
 */
class PaulBourkeLinearConversionTable() extends LinearConversionTable {
  override def table: Seq[Char] = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ".toList
}
