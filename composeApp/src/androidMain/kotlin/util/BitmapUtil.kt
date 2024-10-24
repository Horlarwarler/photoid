package util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.InputStream

object BitmapUtil {
    fun getBitmapFromUri(
        uri: Uri,
        contentResolver: ContentResolver
    ): Bitmap? {

        Log.d("IMAGE", "IMAGE BITMAP START")
        val inputStream: InputStream?
        try {
            inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            Log.d("IMAGE", "IMAGE BITMAP FINISH")

            return bitmap

        } catch (error: Exception) {
            error.printStackTrace()
            return null
        }


    }
}