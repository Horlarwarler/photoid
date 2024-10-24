package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.AVFoundation.AVAuthorizationStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.Foundation.NSURL
import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

@Composable
actual fun createPermissionManager(permissionCallback: PermissionCallback): PermissionManager {
    return PermissionManager(permissionCallback)
}

actual class PermissionManager actual constructor(
    private val permissionCallback: PermissionCallback
) :
    PermissionHandler {
    @Composable
    override fun askForPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.CAMERA -> {
                val status: AVAuthorizationStatus = remember {
                    AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
                }
                askCameraPermission(
                    status = status,
                    permission = permission,
                    permissionCallback = permissionCallback

                )
            }

            PermissionType.GALLERY -> {
                val status: PHAuthorizationStatus = remember {
                    PHPhotoLibrary.authorizationStatus()
                }
                checkGalleryPermission(
                    permissionType = PermissionType.GALLERY,
                    status = status,
                    permissionCallback = permissionCallback
                )
            }

            else -> {

            }
        }
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.CAMERA -> {
                val status = remember {
                    AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
                }
                status == AVAuthorizationStatusAuthorized
            }

            PermissionType.GALLERY -> {
                val status = remember {
                    PHPhotoLibrary.authorizationStatus()
                }
                status == PHAuthorizationStatusAuthorized
            }

            PermissionType.NOTIFICATION -> {
                return true
            }
        }
    }

    @Composable
    override fun launchApplicationSettings() {
        NSURL.URLWithString(UIApplicationOpenSettingsURLString)?.let {
            UIApplication.sharedApplication.openURL(it)
        }
    }


    private fun askCameraPermission(
        status: AVAuthorizationStatus,
        permissionCallback: PermissionCallback,
        permission: PermissionType
    ) {
        when (status) {
            AVAuthorizationStatusAuthorized -> {
                println("Camera Permission Granted")
                permissionCallback.onPermissionStatus(PermissionStatus.GRANTED, permission)
            }

            AVAuthorizationStatusDenied -> {
                println("Camera Permission Denied")
                permissionCallback.onPermissionStatus(PermissionStatus.DENIED, permission)
            }

            AVAuthorizationStatusNotDetermined -> {
                println("Requesting Camera Permission")
                AVCaptureDevice.Companion.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                    if (granted) {
                        permissionCallback.onPermissionStatus(PermissionStatus.GRANTED, permission)
                    } else {
                        permissionCallback.onPermissionStatus(PermissionStatus.DENIED, permission)
                    }
                }
            }

            else -> {
                error("Unknown Camera Status")
            }
        }
    }

    private fun checkGalleryPermission(
        permissionType: PermissionType,
        permissionCallback: PermissionCallback,
        status: PHAuthorizationStatus
    ) {

        when (status) {
            PHAuthorizationStatusAuthorized -> {
                permissionCallback.onPermissionStatus(
                    status = PermissionStatus.GRANTED,
                    permission = permissionType
                )
            }

            PHAuthorizationStatusDenied -> {
                permissionCallback.onPermissionStatus(
                    status = PermissionStatus.DENIED,
                    permission = permissionType
                )
            }

            PHAuthorizationStatusNotDetermined -> {
                PHPhotoLibrary.Companion.requestAuthorization { newStatus ->
                    checkGalleryPermission(
                        permissionType = permissionType,
                        permissionCallback = permissionCallback,
                        status = newStatus
                    )
                }
            }

            else -> {
                error("UNKNOWN ERROR $status")
            }
        }
    }

}