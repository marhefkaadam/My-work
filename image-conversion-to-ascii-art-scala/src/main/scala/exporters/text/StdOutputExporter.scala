package exporters.text

/**
 * Class exports string to System.out stream by extending StreamTextExporter class.
 */
class StdOutputExporter extends StreamTextExporter(System.out) {
}
