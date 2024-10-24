package util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import cocoapods.GoogleMLKit.MLKFaceDetector
import cocoapods.GoogleMLKit.MLKFaceDetectorClassificationModeAll
import cocoapods.GoogleMLKit.MLKFaceDetectorLandmarkModeAll
import cocoapods.GoogleMLKit.MLKFaceDetectorOptions
import cocoapods.GoogleMLKit.MLKFaceDetectorPerformanceModeAccurate
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIImage
import cocoapods.GoogleMLKit.MLKFace
import platform.CoreMedia.CMSampleBufferRef
//import cocoapods.GoogleMLKit.Vision
import kotlin.coroutines.suspendCoroutine
import platform.UIKit.UIApplication


actual object GoogleMlkit {
    actual suspend fun getResult(imageBitmap: ImageBitmap): ImageBitmap? {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun faceDetected(imageBitmap: ImageBitmap): Boolean {
        val options = MLKFaceDetectorOptions()

        options.classificationMode = MLKFaceDetectorClassificationModeAll
        options.performanceMode = MLKFaceDetectorPerformanceModeAccurate
        options.landmarkMode = MLKFaceDetectorLandmarkModeAll

        val detector = MLKFaceDetector.faceDetectorWithOptions(options)


        //  val image = Vision


        return suspendCoroutine {

//            detector.processImage(
//                image = ,
//                completion = {
//                    images, error ->
//
//                }
//            )

        }


    }

    actual fun initMlKit() {

    }


}