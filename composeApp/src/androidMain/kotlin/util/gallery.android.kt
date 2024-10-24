package util

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
actual fun rememberGalleryManager(onImagePicked: (SharedImage?) -> Unit): GalleryManager {
    val context = LocalContext.current
    val contentResolver = context.contentResolver

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { result ->
            result?.let { uri ->
                Log.d("IMAGE", "URI PICKED ")

                onImagePicked(null)
                //temp fix
                Log.d("IMAGE", "URI PICKED NULL")
                val sharedImage = SharedImage(
                    bitmap = BitmapUtil.getBitmapFromUri(
                        uri = uri,
                        contentResolver = contentResolver
                    )
                )
                Log.d("IMAGE", "SHARED IMAGE GOTTEN")

                onImagePicked(
                    sharedImage
                )
                Log.d("IMAGE", "IMAGE PICKED")

            }
        }

    return GalleryManager {
        galleryLauncher.launch(
            PickVisualMediaRequest(
                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }
}

actual class GalleryManager actual constructor(private val onLaunched: () -> Unit) {
    actual fun launch() {
        onLaunched()
    }
}
