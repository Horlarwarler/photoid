package util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

actual class SharedImage(
    private val bitmap: Bitmap?
) {
    actual fun toByteArray(): ByteArray? {

        if (bitmap == null) {
            return null
        }
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(
            Bitmap.CompressFormat.PNG,
            100,
            byteArrayOutputStream
        )
        return byteArrayOutputStream.toByteArray()

    }

    actual fun toImageBitmap(): ImageBitmap? {
        val byteArray = toByteArray() ?: return null
        return BitmapFactory.decodeByteArray(
            byteArray, 0, byteArray.size
        ).asImageBitmap()

    }
}