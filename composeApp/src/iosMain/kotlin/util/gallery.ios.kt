package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject


@Composable
actual fun rememberGalleryManager(onImagePicked: (SharedImage?) -> Unit): GalleryManager {
    val imagePicker = UIImagePickerController()
    val galleryDelegate = remember {
        object : NSObject(), UIImagePickerControllerDelegateProtocol,
            UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingMediaWithInfo: Map<Any?, *>,
            ) {
                val image =
                    didFinishPickingMediaWithInfo[UIImagePickerControllerEditedImage] as? UIImage
                        ?: didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
                onImagePicked(SharedImage(image))
                picker.dismissViewControllerAnimated(
                    true, null
                )

            }

        }
    }
    return GalleryManager {
        imagePicker.setSourceType(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary)
        imagePicker.setAllowsEditing(true)
        imagePicker.setDelegate(galleryDelegate)
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            imagePicker, true, null
        )
    }
}

actual class GalleryManager actual constructor(
    private val onLaunched: () -> Unit
) {
    actual fun launch() {
        onLaunched()
    }
}