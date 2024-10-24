package util

import androidx.compose.runtime.Composable

expect class PermissionManager(permissionCallback: PermissionCallback) : PermissionHandler

interface PermissionCallback {
    fun onPermissionStatus(status: PermissionStatus, permission: PermissionType)
}

enum class PermissionStatus {
    GRANTED,
    DENIED,
    SHOW_RATIONALE
}

enum class PermissionType {
    CAMERA,
    GALLERY,
    NOTIFICATION

}

@Composable
expect fun createPermissionManager(permissionCallback: PermissionCallback): PermissionManager

interface PermissionHandler {
    @Composable
    fun askForPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun launchApplicationSettings()
}