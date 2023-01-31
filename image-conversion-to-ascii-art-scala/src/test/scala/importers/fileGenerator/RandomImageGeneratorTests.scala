package importers.fileGenerator

import asciiArtApp.models.pixel.RGBPixel
import org.scalatest.FunSuite

class RandomImageGeneratorTests extends FunSuite {
  test("Random generator generates valid image from 0 to 800px, seed 123456") {
    val seed = 123456
    val generator = new RandomImageGenerator(seed)
    val RGBImage = generator.importData()

    assert(RGBImage.height >= 0 && RGBImage.height <= 800)
    assert(RGBImage.width >= 0 && RGBImage.width <= 800)
  }

  test("Random generator generates image with valid RGB values, seed 123456") {
    val seed = 123456
    val generator = new RandomImageGenerator(seed)
    val RGBImage = generator.importData()

    for(x <- 0 until RGBImage.height) {
      for(y <- 0 until RGBImage.width) {
        assert(RGBImage.getPixel(x, y).isInstanceOf[RGBPixel])
        assert(RGBImage.getPixel(x, y).r >= 0 && RGBImage.getPixel(x, y).r <= 255)
        assert(RGBImage.getPixel(x, y).g >= 0 && RGBImage.getPixel(x, y).g <= 255)
        assert(RGBImage.getPixel(x, y).b >= 0 && RGBImage.getPixel(x, y).b <= 255)
      }
    }
  }

  test("Random generator generates valid image from 0 to 800px, seed 52432") {
    val seed = 52432
    val generator = new RandomImageGenerator(seed)
    val RGBImage = generator.importData()

    assert(RGBImage.height >= 0 && RGBImage.height <= 800)
    assert(RGBImage.width >= 0 && RGBImage.width <= 800)
  }

  test("Random generator generates image with valid RGB values, seed 52432") {
    val seed = 52432
    val generator = new RandomImageGenerator(seed)
    val RGBImage = generator.importData()

    for(x <- 0 until RGBImage.height) {
      for(y <- 0 until RGBImage.width) {
        assert(RGBImage.getPixel(x, y).isInstanceOf[RGBPixel])
        assert(RGBImage.getPixel(x, y).r >= 0 && RGBImage.getPixel(x, y).r <= 255)
        assert(RGBImage.getPixel(x, y).g >= 0 && RGBImage.getPixel(x, y).g <= 255)
        assert(RGBImage.getPixel(x, y).b >= 0 && RGBImage.getPixel(x, y).b <= 255)
      }
    }
  }

  test("Random generator generates valid image from 0 to 800px, seed 0") {
    val seed = 0
    val generator = new RandomImageGenerator(seed)
    val RGBImage = generator.importData()

    assert(RGBImage.height >= 0 && RGBImage.height <= 800)
    assert(RGBImage.width >= 0 && RGBImage.width <= 800)
  }

  test("Random generator generates image with valid RGB values, seed 0") {
    val seed = 0
    val generator = new RandomImageGenerator(seed)
    val RGBImage = generator.importData()

    for(x <- 0 until RGBImage.height) {
      for(y <- 0 until RGBImage.width) {
        assert(RGBImage.getPixel(x, y).isInstanceOf[RGBPixel])
        assert(RGBImage.getPixel(x, y).r >= 0 && RGBImage.getPixel(x, y).r <= 255)
        assert(RGBImage.getPixel(x, y).g >= 0 && RGBImage.getPixel(x, y).g <= 255)
        assert(RGBImage.getPixel(x, y).b >= 0 && RGBImage.getPixel(x, y).b <= 255)
      }
    }
  }
}
