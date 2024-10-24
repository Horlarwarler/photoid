package util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import org.jetbrains.skia.Image
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation

actual class SharedImage(
    private val image: UIImage?
) {
    @OptIn(ExperimentalForeignApi::class)
    actual fun toByteArray(): ByteArray? {
        if (image == null) return null
        val imageData =
            UIImageJPEGRepresentation(image, COMPRESSION_QUALITY) ?: throw IllegalArgumentException(
                "Failed to convert image to JPEG as image is null"
            )
        val bytes = imageData.bytes ?: throw IllegalArgumentException(" image bytes is null")
        val length = imageData.length
        val data: CPointer<ByteVar> = bytes.reinterpret()
        val byteArray = ByteArray(length.toInt()) { index ->
            data[index]
        }
        return byteArray

    }

    actual fun toImageBitmap(): ImageBitmap? {
        val byteArray = toByteArray() ?: return null
        return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
    }

    companion object {
        private const val COMPRESSION_QUALITY = 0.99
    }
}