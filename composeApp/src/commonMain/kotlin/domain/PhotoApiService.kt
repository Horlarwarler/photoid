package domain

import domain.model.Specification
import domain.model.Uniform
import domain.model.UserPhoto
import kotlinx.coroutines.flow.Flow
import util.Result
import util.UploadResult

interface PhotoApiService {

    object EndPoint {
        const val BASE_URL = "https://horlwarler.pythonanywhere.com/api/v1/"
        const val USER_PHOTOS = "photos/users"

        // const val USER_PHOTO = "photos/users/"
        const val SPECIFICATION = "specifications"
        const val UNIFORMS = "uniforms"
        const val UPLOAD = "photos/"
        const val APPLY_UNIFORM = "apply/uniform"
        const val APPLY_BEAUTY = "apply/beauty"
    }


    suspend fun getPhotosByUser(
        imei: String
    ): Result<List<UserPhoto>>

    suspend fun getPhotoById(
        id: String,
        imei: String
    ): Result<UserPhoto>

    suspend fun deletePhotoById(
        id: String,
        imei: String
    ): Result<Unit>

    suspend fun getPhotoSpecifications(): Result<List<Specification>>

    suspend fun getSpecificationById(
        id: String
    ): Result<Specification>

    suspend fun getUniforms(): Result<List<Uniform>>

    suspend fun applyUniform(
        photoId: String,
        uniformId: String?
    ): Result<UserPhoto>

    suspend fun applyBeauty(
        photoId: String,
        beautify: Boolean
    ): Result<UserPhoto>

    suspend fun uploadPhoto(
        phoneImei: String,
        title: String,
        specificationId: String,
        dpi: Int,
        file: ByteArray
    ): Flow<UploadResult>

}