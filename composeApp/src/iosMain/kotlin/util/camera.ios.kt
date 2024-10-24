package util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.window.ComposeUIViewController
import cocoapods.GoogleMLKit.MLKFaceDetector
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureDevicePosition
import platform.AVFoundation.AVCaptureDevicePositionBack
import platform.AVFoundation.AVCaptureDevicePositionFront
import platform.AVFoundation.AVCaptureFlashModeAuto
import platform.AVFoundation.AVCaptureInput
import platform.AVFoundation.AVCapturePhoto
import platform.AVFoundation.AVCapturePhotoCaptureDelegateProtocol
import platform.AVFoundation.AVCapturePhotoOutput
import platform.AVFoundation.AVCapturePhotoSettings
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionPresetPhoto
import platform.AVFoundation.AVCaptureStillImageOutput
import platform.AVFoundation.AVCaptureVideoOrientationPortrait
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.AVVideoCodecJPEG
import platform.AVFoundation.AVVideoCodecKey
import platform.AVFoundation.fileDataRepresentation
import platform.AVFoundation.position
import platform.CoreGraphics.CGRect
import platform.Foundation.NSError
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import presentation.components.CameraView
import presentation.components.Preview


actual class CameraManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launchCamera() {
        onLaunch()
    }
}

@Composable
actual fun rememberCameraManager(onPicTaken: (SharedImage?) -> Unit): CameraManager {
    val imagePicker = UIImagePickerController()
    val cameraDelegate = remember {
        object : NSObject(), UIImagePickerControllerDelegateProtocol,
            UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                val image = didFinishPickingMediaWithInfo.getValue(
                    UIImagePickerControllerEditedImage
                ) as? UIImage

                onPicTaken(SharedImage(image))
                picker.dismissViewControllerAnimated(true, null)
            }
        }

    }
    return remember {
        CameraManager {
            imagePicker.delegate = cameraDelegate
            imagePicker.sourceType =
                UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            imagePicker.allowsEditing = true
            imagePicker.setCameraOverlayView(cameraViewController().view)
            imagePicker.setCameraCaptureMode(UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto)
            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                imagePicker,
                true,
                null
            )
        }
    }
}

fun cameraViewController(): UIViewController {
    return ComposeUIViewController {
        CameraView()
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CameraPreview(
    onPicTaken: (SharedImage?) -> Unit,
    onBackClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    var cameraPosition by remember {
        mutableStateOf(AVCaptureDevicePositionFront)
    }
    var useFrontCamera by remember {
        mutableStateOf(true)
    }
    var stillImage: UIImage? = null


    var loadPreview by remember {
        mutableStateOf(false)
    }

    var uiView by remember {
        mutableStateOf<UIView?>(null)
    }
    var stillImageOutput by remember {
        mutableStateOf<AVCapturePhotoOutput?>(null)
    }


    val backFacingDevice by remember {
        val device = getDevice(AVCaptureDevicePositionBack)
        mutableStateOf(device)
    }
    val frontFacingDevice by remember {
        val device = getDevice(AVCaptureDevicePositionFront)
        mutableStateOf(device)
    }
    var currentDevice by remember {
        mutableStateOf<AVCaptureDevice?>(null)
    }
    var captureSession by remember {
        mutableStateOf<AVCaptureSession?>(null)
    }

    var videoPreviewLayer by remember {
        mutableStateOf<AVCaptureVideoPreviewLayer?>(null)
    }

    fun loadCamera() {
        CoroutineScope(Dispatchers.IO).launch {
            currentDevice = if (useFrontCamera) frontFacingDevice else backFacingDevice
            if (currentDevice == null) {
                return@launch
            }
            if (captureSession == null) {
                captureSession = AVCaptureSession()
                captureSession!!.sessionPreset = AVCaptureSessionPresetPhoto
            }

            val deviceInput = AVCaptureDeviceInput.deviceInputWithDevice(
                currentDevice!!,
                null
            ) as AVCaptureDeviceInput
            captureSession!!.stopRunning()
            for (input in captureSession!!.inputs) {
                captureSession!!.removeInput(input as AVCaptureInput)
            }

            if (!captureSession!!.canAddInput(deviceInput)) {
                return@launch
            }
            captureSession!!.addInput(deviceInput)
            videoPreviewLayer = AVCaptureVideoPreviewLayer(session = captureSession!!)
            if (stillImageOutput != null) {
                captureSession!!.removeOutput(stillImageOutput!!)

            }
            stillImageOutput = AVCapturePhotoOutput()
            captureSession!!.addOutput(stillImageOutput!!)
            videoPreviewLayer!!.videoGravity = AVLayerVideoGravityResizeAspectFill
            videoPreviewLayer!!.orientation = AVCaptureVideoOrientationPortrait

            withContext(Dispatchers.Main) {
                val container = UIView()
                container.layer.addSublayer(videoPreviewLayer!!)
                uiView = container
            }
            CoroutineScope(Dispatchers.IO).launch {

                captureSession!!.startRunning()
                loadPreview = true

            }
        }

    }


    LaunchedEffect(key1 = Unit) {
        loadCamera()
    }

    Preview(
        showPlatform = loadPreview,
        modifier = Modifier.fillMaxSize(),
        platformView = {

            println("CAPTURE PLATFORM VIEW IS OPEN")
            UIKitView(
                modifier = Modifier.fillMaxSize(),
                background = Color.Transparent,
                factory = {

                    println("CAPTURE WILL LOAD VIEW")

                    // container
                    uiView!!

                },
                onResize = { container: UIView, rect: CValue<CGRect> ->
                    CATransaction.begin()
                    CATransaction.setValue(true, kCATransactionDisableActions)
                    container.layer.setFrame(rect)
                    videoPreviewLayer!!.setFrame(rect)
                    CATransaction.commit()
                },
                onRelease = {

                    uiView?.removeFromSuperview()
                    uiView = null
                    videoPreviewLayer = null

                    println("UI VIEW RELEASE")
                },
                update = {
                    println("UI VIEW UPDATED")
                }
            )

        },
        onBackClick = onBackClick,
        onGalleryClick = onGalleryClick,
        onOrientationClick = {
            useFrontCamera = !useFrontCamera
            loadPreview = false
            loadCamera()
        },
        captureClick = {
            captureDevice(onPicTaken, stillImageOutput!!)
        }
    )


}

private fun getDevice(position: AVCaptureDevicePosition): AVCaptureDevice {
    val device = AVCaptureDevice.devicesWithMediaType(AVMediaTypeVideo).firstOrNull { device ->
        (device as AVCaptureDevice).position == position
    }!! as AVCaptureDevice
    return device
}


@ExperimentalForeignApi
private fun captureDevice(
    onImageCapture: (SharedImage?) -> Unit,
    output: AVCapturePhotoOutput
) {

    val photoSettings =
        AVCapturePhotoSettings.photoSettingsWithFormat(mapOf(AVVideoCodecKey to AVVideoCodecJPEG))

    photoSettings.setHighResolutionPhotoEnabled(true)
    photoSettings.setFlashMode(AVCaptureFlashModeAuto)
    output.setHighResolutionCaptureEnabled(true)
    output.capturePhotoWithSettings(
        photoSettings,
        delegate = object : NSObject(), AVCapturePhotoCaptureDelegateProtocol {
            override fun captureOutput(
                output: AVCapturePhotoOutput,
                didFinishProcessingPhoto: AVCapturePhoto,
                error: NSError?
            ) {
                val imageData = didFinishProcessingPhoto.fileDataRepresentation()
                if (imageData != null) {
                    val image = UIImage(data = imageData)
                    onImageCapture(SharedImage(image))
                } else {
                    println("Error capturing image")
                }

            }

        }
    )

}
