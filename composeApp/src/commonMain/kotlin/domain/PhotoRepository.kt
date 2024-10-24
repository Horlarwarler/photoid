package domain

import domain.model.Specification
import domain.model.Uniform
import domain.model.UserPhoto
import kotlinx.coroutines.flow.Flow
import util.Result
import util.UploadResult

interface PhotoRepository {
    fun getPhotosByUser(
        imei: String
    ): Flow<Result<List<UserPhoto>>>

    suspend fun getPhotoById(
        id: String,
        imei: String
    ): Flow<Result<UserPhoto>>

    suspend fun deletePhotoById(
        id: String,
        imei: String
    ): Flow<Result<Unit>>

    suspend fun getPhotoSpecifications(): Flow<Result<List<Specification>>>

    suspend fun getSpecificationById(
        id: String
    ): Flow<Result<Specification>>

    suspend fun getUniforms(): Flow<Result<List<Uniform>>>

    suspend fun applyUniform(
        photoId: String,
        uniformId: String?
    ): Flow<Result<UserPhoto>>

    suspend fun applyBeauty(
        photoId: String,
        beautify: Boolean
    ): Flow<Result<UserPhoto>>

    suspend fun uploadPhoto(
        uuId: String,
        title: String,
        specificationId: String,
        dpi: Int,
        file: ByteArray
    ): Flow<UploadResult>

}