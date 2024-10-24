package data.remote

import domain.PhotoApiService
import domain.PhotoRepository
import domain.model.Specification
import domain.model.Uniform
import domain.model.UserPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import util.Result
import util.UploadResult

class PhotoRepositoryImpl(
    private val photoApiService: PhotoApiService,
) : PhotoRepository {
    override fun getPhotosByUser(imei: String): Flow<Result<List<UserPhoto>>> {
        return flow {
            val userPhotos = photoApiService.getPhotosByUser(imei)
            emit(userPhotos)

        }
    }

    override suspend fun getPhotoById(id: String, imei: String): Flow<Result<UserPhoto>> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePhotoById(id: String, imei: String): Flow<Result<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPhotoSpecifications(): Flow<Result<List<Specification>>> {
        return flow {
            val photoSpecifications = photoApiService.getPhotoSpecifications()
            emit(photoSpecifications)
        }
    }

    override suspend fun getSpecificationById(id: String): Flow<Result<Specification>> {
        return flow {
            val photoSpecification = photoApiService.getSpecificationById(id)
            emit(photoSpecification)
        }
    }

    override suspend fun getUniforms(): Flow<Result<List<Uniform>>> {
        return flow {
            val uniforms = photoApiService.getUniforms()
            emit(uniforms)
        }
    }

    override suspend fun applyUniform(
        photoId: String,
        uniformId: String?
    ): Flow<Result<UserPhoto>> {
        return flow {
            val userAppliedUniform = photoApiService.applyUniform(
                photoId = photoId,
                uniformId = uniformId
            )
            emit(userAppliedUniform)
        }
    }

    override suspend fun applyBeauty(photoId: String, beautify: Boolean): Flow<Result<UserPhoto>> {
        return flow {
            val userPhoto = photoApiService.applyBeauty(photoId, beautify)
            emit(userPhoto)
        }
    }

    override suspend fun uploadPhoto(
        uuId: String,
        title: String,
        specificationId: String,

        dpi: Int,
        file: ByteArray
    ): Flow<UploadResult> {
        return photoApiService.uploadPhoto(

            phoneImei = uuId,
            title = title,
            specificationId = specificationId,
            dpi = dpi,
            file = file
        )
    }

}