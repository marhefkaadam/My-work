package exporters.text

import org.scalatest.FunSuite

import java.io.File
import scala.io.Source

class FileOutputExporterTests extends FunSuite {
  test("Testing correct string export to file") {
    val exporter = new FileOutputExporter(new File("src/test/resources/file-output-test.txt"))

    val outputString = "Testing export to file..."
    exporter.export(outputString)

    val testFile = Source.fromFile("src/test/resources/file-output-test.txt")
    assert(outputString == testFile.getLines().mkString)
    testFile.close()
  }

  test("Testing correct string export to file 2") {
    val exporter = new FileOutputExporter(new File("src/test/resources/file-output-test2.txt"))

    val outputString = "DAISDAS\\oidnf!:\"?/PDSD"
    exporter.export(outputString)

    val testFile = Source.fromFile("src/test/resources/file-output-test2.txt")
    assert(outputString == testFile.getLines().mkString)
    testFile.close()
  }

  test("Testing correct string export to file - empty string") {
    val exporter = new FileOutputExporter(new File("src/test/resources/file-output-test-empty.txt"))

    val outputString = ""
    exporter.export(outputString)

    val testFile = Source.fromFile("src/test/resources/file-output-test-empty.txt")
    assert(outputString == testFile.getLines().mkString)
    testFile.close()
  }
}
