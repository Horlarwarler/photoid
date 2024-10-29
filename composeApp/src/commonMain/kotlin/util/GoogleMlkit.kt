package util

import androidx.compose.ui.graphics.ImageBitmap


expect object GoogleMlkit {

    fun initMlKit()
    suspend fun getResult(imageBitmap: ImageBitmap): ImageBitmap?

    suspend fun faceDetected(imageBitmap: ImageBitmap): Boolean
}



