package exporters.text

import java.io.{File, FileOutputStream}

/**
 * Class exports string to file using FileOutputStream and by extending StreamTextExporter class.
 * @param file - file to which the data should be exported
 */
class FileOutputExporter(file: File) extends StreamTextExporter(new FileOutputStream(file)) {
}
