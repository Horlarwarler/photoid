
import Foundation
import MLKitFaceDetection
import MLKitVision
class FaceDetectionManager{
    static func detectFace(image:UIImage,completion: @escaping (Bool) -> Void){
    let options = FaceDetectorOptions()
    options.performanceMode = .accurate
    options.landmarkMode = .all
    options.classificationMode = .all
    let faceDetector = FaceDetector(options: options)
    let image = VisionImage(image: UIImage)
    weak var weakSelf = self
    faceDetector.process(image) { faces, error in
        guard let strongSelf = weakSelf else {
            return
        }
        if let error = error {
            print("Failed with error: \(error.localizedDescription)")
            return
        }
        guard let faces = faces, !faces.isEmpty else {
            print("Face detector failed to detect any faces.")
            return
        }
        completion(true)

    }

    }
}