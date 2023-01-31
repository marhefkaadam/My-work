package exporters.text

import java.io.OutputStream

/**
 * Class exports data to string to specified stream.
 * @param outputStream - stream to which the string should be exported
 */
  class StreamTextExporter(outputStream: OutputStream) extends TextExporter {
  /** Indicates if the stream is closed or not. */
  private var closed = false

  override def export(item: String): Unit = exportToStream(item)

  /**
   * Method exports string to specified stream using UTF-8.
   * @param text - string to be exported
   * @throws Exception is the stream is already closed
   */
  protected def exportToStream(text: String): Unit ={
    if (closed)
      throw new Exception("The stream is already closed")

    outputStream.write(text.getBytes("UTF-8"))
    outputStream.flush()
  }

  /**
   * Method closes stream specified in constructor.
   */
  def close(): Unit = {
    if (closed)
      return

    outputStream.close()
    closed = true
  }
}
