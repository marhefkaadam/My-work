package filters.image.specific.flip

import asciiArtApp.models.image.GrayscaleImage
import filters.image.ImageFilter

/**
 * Trait for filtering grayscale image by the use of flipping the whole image.
 */
trait FlipFilter extends ImageFilter[GrayscaleImage] {
}
