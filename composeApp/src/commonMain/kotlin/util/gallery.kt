package util

import androidx.compose.runtime.Composable

@Composable
expect fun rememberGalleryManager(
    onImagePicked: (SharedImage?) -> Unit
): GalleryManager


expect class GalleryManager(
    onLaunched: () -> Unit
) {
    fun launch()
}