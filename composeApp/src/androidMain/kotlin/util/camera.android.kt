package util

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.crezent.photoid.image.ComposeFileProvider
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.arrow_left
import photoid.composeapp.generated.resources.camera_guide
import photoid.composeapp.generated.resources.question
import presentation.components.CameraAction
import presentation.components.Preview
import ui.mediumPadding
import ui.white
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


actual class CameraManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launchCamera() {
        onLaunch()
    }
}

@Composable
actual fun rememberCameraManager(
    onPicTaken: (SharedImage?) -> Unit,

    ): CameraManager {
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    var tempPhotoUri by remember {
        mutableStateOf(Uri.EMPTY)
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { successfull ->
            if (successfull) {
                onPicTaken(
                    SharedImage(
                        BitmapUtil.getBitmapFromUri(
                            tempPhotoUri, contentResolver
                        )
                    )
                )
            }

        }
    )
    return remember {
        CameraManager(
            onLaunch = {
                tempPhotoUri = ComposeFileProvider.getImageUri(context)
                cameraLauncher.launch(tempPhotoUri)
            }
        )
    }
}

@Composable
actual fun CameraPreview(
    onPicTaken: (SharedImage?) -> Unit,
    onBackClick: () -> Unit,
    onGalleryClick: () -> Unit

) {
    var lensFacing by remember {
        mutableIntStateOf(CameraSelector.LENS_FACING_FRONT)
    }
    var previewOpacity by remember {
        mutableStateOf(1f)
    }
    val context = LocalContext.current

    var showGuide by remember {
        mutableStateOf(false)
    }

    val imageCapture = remember {
        ImageCapture.Builder().build()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }

    val changeOrientation: () -> Unit = {
        if (lensFacing == CameraSelector.LENS_FACING_FRONT) {
            lensFacing = CameraSelector.LENS_FACING_BACK
        } else if (lensFacing == CameraSelector.LENS_FACING_BACK) {
            lensFacing = CameraSelector.LENS_FACING_FRONT

        }
    }

    val cameraXSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    LaunchedEffect(key1 = lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraXSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Preview(
        modifier = Modifier.fillMaxSize(),
        platformView = {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
        },
        onBackClick = {
            onBackClick()
        },
        captureClick = {
            captureImage(
                imageCapture = imageCapture,
                context = context,
                onImageCapture = onPicTaken
            )

        },
        onOrientationClick = changeOrientation
    ) {

    }
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .clickable(
//                showGuide
//            ) {
//                showGuide = false
//            }
//            .alpha(if (showGuide) 0.5f else 1f),
//    ) {
//
//        AndroidView(
//            factory = { previewView },
//            modifier = Modifier.fillMaxSize()
//        )
//        Icon(
//            modifier = Modifier
//                .padding(mediumPadding)
//                .clickable {
//                    onBackClick()
//                }
//                .align(Alignment.TopStart),
//            painter = painterResource(resource = Res.drawable.arrow_left),
//            contentDescription = null,
//            tint = white
//        )
//
//        Icon(
//            modifier = Modifier
//                .padding(mediumPadding)
//                .clickable {
//                    showGuide = true
//                }
//                .align(Alignment.TopEnd),
//            painter = painterResource(resource = Res.drawable.question),
//            contentDescription = null,
//            tint = white
//        )
//
//        Image(
//            modifier = Modifier.align(Alignment.Center),
//            painter = painterResource(resource = Res.drawable.camera_guide),
//            contentDescription = null,
//        )
//        CameraAction(
//            modifier = Modifier.align(Alignment.BottomCenter),
//            onShutterClick = {
//                captureImage(
//                    imageCapture = imageCapture,
//                    context = context,
//                    onImageCapture = onPicTaken
//                )
//            },
//            changeOrientation = changeOrientation,
//            onGalleryClick = onGalleryClick
//        )
//
//    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider {
    return suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener(
                {
                    continuation.resume(cameraProvider.get())
                }, ContextCompat.getMainExecutor(this)
            )
        }

    }
}

private fun captureImage(
    imageCapture: ImageCapture,
    context: Context,
    onImageCapture: (SharedImage?) -> Unit
) {
    imageCapture.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val imageBitmap = image
                    .toBitmap()

                onImageCapture(
                    SharedImage(imageBitmap)
                )

            }

            override fun onError(exception: ImageCaptureException) {
                Toast.makeText(
                    context, "Failed to capture Image ${exception.message}", Toast.LENGTH_SHORT
                ).show()
            }

        }
    )
}
