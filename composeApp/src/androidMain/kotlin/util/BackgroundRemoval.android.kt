package util

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.segmentation.subject.SubjectSegmentation
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenterOptions
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


actual object GoogleMlkit {

    actual suspend fun getResult(imageBitmap: ImageBitmap): ImageBitmap? {

        val context = AppContext.getContext()

        val isAvalaible = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        Log.d("BACKGROUND", "is available $isAvalaible")

        val options = SubjectSegmenterOptions.Builder()
            .enableForegroundBitmap()
            .enableForegroundConfidenceMask()
            .build()

        val segmenter = SubjectSegmentation.getClient(options)
        val inputImage = InputImage.fromBitmap(
            imageBitmap.asAndroidBitmap(), 0
        )
        Log.d("IMAGE", "IMAGE CONTINUE TO PROCESS")


        return suspendCoroutine { continuation ->
            segmenter.process(inputImage)
                .addOnSuccessListener { result ->
                    //  Log.d("IMAGE", "SUCCESS")

                    val image = result.foregroundBitmap?.asImageBitmap()
                    continuation.resume(image)

                }
                .addOnFailureListener { error ->
                    continuation.resume(null)
                    error.printStackTrace()
                    // Log.d("IMAGE", "IMAGE ERROR ${error.message}")
                }
        }


    }


    actual suspend fun faceDetected(imageBitmap: ImageBitmap): Boolean {
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        val inputImage = InputImage.fromBitmap(imageBitmap.asAndroidBitmap(), 0)
        val detector = FaceDetection.getClient(highAccuracyOpts)

        return suspendCoroutine { continuation ->
            detector.process(inputImage)
                .addOnSuccessListener { faces ->

                    if (faces.isNotEmpty()) {
                        continuation.resume(true)
                        //  Log.d("IMAGE", "IMAGE FOUND")

                    } else {
                        continuation.resume(false)
                        ///  Log.d("IMAGE", "IMAGE NOT FOUND")


                    }

                }
                .addOnFailureListener { error ->
                    continuation.resume(false)
                    error.printStackTrace()
                    // Log.d("IMAGE", "IMAGE NOT FOUND ${error.message}")

                }

        }


    }

    actual fun initMlKit() {
        val context = AppContext.getContext()

        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)

        if (resultCode != ConnectionResult.SUCCESS) {
            Log.d("PLAY", "NO google play available")
            return
        }
        Log.d("PLAY", " google play available")


        val moduleInstallClient = ModuleInstall.getClient(context)
        val segmentationApiModule =
            SubjectSegmentation.getClient(SubjectSegmenterOptions.Builder().build())
        val faceDetectionApi = FaceDetection.getClient()
        moduleInstallClient.areModulesAvailable(
            segmentationApiModule, faceDetectionApi
        )
            .addOnSuccessListener { result ->
                val modulesAreAvailable = result.areModulesAvailable()
                if (modulesAreAvailable) {
                    Log.d("PLAY", " modules  available")
                } else {
                    Log.d("PLAY", " modules  not available")

                }

                //moduleInstallClient.
            }
    }

}