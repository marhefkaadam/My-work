package exporters.text

import org.scalatest.FunSuite

import java.io.ByteArrayOutputStream

class StreamTextExporterTests extends FunSuite {
  test("Correct export to stream") {
    val byteStream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(byteStream)

    exporter.export("ABCDEFGHIJ!")
    assert(byteStream.toString("UTF-8") == "ABCDEFGHIJ!")
  }

  test("Correct export to stream 2") {
    val byteStream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(byteStream)

    exporter.export("FAOJQ\\\"\\?D/):")
    assert(byteStream.toString("UTF-8") == "FAOJQ\\\"\\?D/):")
  }

  test("Correct export to stream 3") {
    val byteStream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(byteStream)

    exporter.export("")
    assert(byteStream.toString("UTF-8") == "")
  }

  test("Stream already closed exception") {
    val byteStream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(byteStream)

    exporter.close()
    assertThrows[Exception](exporter.export("ABCDEFGHIJ!"))
    exporter.close()
    assertThrows[Exception](exporter.export("ABCDEFGHIJ!"))
  }
}
