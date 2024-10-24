package util

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

@Composable
actual fun createPermissionManager(permissionCallback: PermissionCallback): PermissionManager {
    return PermissionManager(permissionCallback)
}

actual class PermissionManager actual constructor(private val permissionCallback: PermissionCallback) :
    PermissionHandler {
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun askForPermission(permission: PermissionType) {
        val lifecycleOwner = LocalLifecycleOwner.current
        when (permission) {
            PermissionType.CAMERA -> {
                val permissionState =
                    rememberPermissionState(permission = android.Manifest.permission.CAMERA)
                AskForCameraPermission(lifecycleOwner, permissionCallback, permissionState)
            }

            PermissionType.GALLERY -> {
                permissionCallback.onPermissionStatus(
                    status = PermissionStatus.GRANTED,
                    permission = PermissionType.GALLERY
                )
            }

            PermissionType.NOTIFICATION -> {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    permissionCallback.onPermissionStatus(
                        status = PermissionStatus.GRANTED,
                        permission = PermissionType.NOTIFICATION
                    )
                    return
                }
                val permissionState =
                    rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
                AskForNotificationPermission(lifecycleOwner, permissionCallback, permissionState)
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        when (permission) {
            PermissionType.CAMERA -> {
                val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
                return cameraPermissionState.status.isGranted
            }

            PermissionType.GALLERY -> {
                return true
            }

            PermissionType.NOTIFICATION -> {
                return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    true
                } else {
                    val cameraPermissionState =
                        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
                    cameraPermissionState.status.isGranted
                }
            }
        }
    }

    @Composable
    override fun launchApplicationSettings() {
        val context = LocalContext.current
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).also {
            context.startActivity(it)
        }

    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun AskForCameraPermission(
        lifecycleOwner: LifecycleOwner,
        permissionCallback: PermissionCallback,
        cameraPermission: PermissionState
    ) {
        LaunchedEffect(key1 = cameraPermission) {
            val permissionStatus = cameraPermission.status
            if (!permissionStatus.isGranted) {
                if (permissionStatus.shouldShowRationale) {
                    permissionCallback.onPermissionStatus(
                        PermissionStatus.SHOW_RATIONALE,
                        PermissionType.CAMERA
                    )
                } else {
                    lifecycleOwner.lifecycleScope.launch {
                        cameraPermission.launchPermissionRequest()
                    }
                }
            } else {
                permissionCallback.onPermissionStatus(
                    permission = PermissionType.CAMERA,
                    status = PermissionStatus.GRANTED
                )
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun AskForNotificationPermission(
        lifecycleOwner: LifecycleOwner,
        permissionCallback: PermissionCallback,
        notificationPermission: PermissionState,
    ) {
        val permissionStatus = notificationPermission.status

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            permissionCallback.onPermissionStatus(
                status = PermissionStatus.GRANTED,
                permission = PermissionType.NOTIFICATION
            )
            return
        }
        if (!permissionStatus.isGranted) {
            if (permissionStatus.shouldShowRationale) {
                permissionCallback.onPermissionStatus(
                    PermissionStatus.SHOW_RATIONALE,
                    PermissionType.NOTIFICATION
                )
            } else {
                lifecycleOwner.lifecycleScope.launch {
                    notificationPermission.launchPermissionRequest()
                }
            }
        } else {
            permissionCallback.onPermissionStatus(
                permission = PermissionType.NOTIFICATION,
                status = PermissionStatus.GRANTED
            )
        }
    }

}