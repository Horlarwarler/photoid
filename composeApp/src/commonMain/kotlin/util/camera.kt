package util

import androidx.compose.runtime.Composable

@Composable
expect fun rememberCameraManager(
    onPicTaken: (SharedImage?) -> Unit,

    ): CameraManager

expect class CameraManager(
    onLaunch: () -> Unit
) {
    fun launchCamera()
}

@Composable
expect fun CameraPreview(
    onPicTaken: (SharedImage?) -> Unit,
    onBackClick: () -> Unit,
    onGalleryClick: () -> Unit


)


