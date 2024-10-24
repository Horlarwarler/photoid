package data.remote.api

import data.remote.model.ApplyUniformRequest
import data.remote.model.SpecificationDto
import data.remote.model.UniformDto
import data.remote.model.UserPhotoDto
import data.remote.model.mapToSpecification
import data.remote.model.mapToUserPhoto
import data.remote.model.toUniform
import domain.PhotoApiService
import domain.PhotoApiService.EndPoint.APPLY_BEAUTY
import domain.PhotoApiService.EndPoint.APPLY_UNIFORM
import domain.PhotoApiService.EndPoint.BASE_URL
import domain.PhotoApiService.EndPoint.SPECIFICATION
import domain.PhotoApiService.EndPoint.UNIFORMS
import domain.PhotoApiService.EndPoint.UPLOAD
import domain.PhotoApiService.EndPoint.USER_PHOTOS
import domain.model.Specification
import domain.model.Uniform
import domain.model.UserPhoto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.serialization.SerializationException
import util.CustomException
import util.Result
import util.UploadResult


class KtorPhotoApi(
    private val httpClient: HttpClient
) : PhotoApiService {

    //TODO("REMOVE THIS")
    private fun categoryToString(index: Int) = when (index) {
        0 -> "men"
        1 -> "women"
        else -> {
            "children"
        }
    }

    private fun categoryToUrl(index: Int) = when (index) {
        0 -> "https://i.pinimg.com/564x/38/0f/eb/380febcb98451f7b8c2b13838dfc91ab.jpg"
        1 -> "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQD5vVEmmLTJ1z22ED_5du9eFC_ZnWxcpobwQ&s"
        else -> {
            "https://upload.wikimedia.org/wikipedia/en/f/f2/KSHS_Boys_Uniform.png"
        }
    }


    private val tempUserPhoto = UserPhoto(
        id = "",
        dateCreated = "",
        expirationDate = "",
        file = "https://plus.unsplash.com/premium_photo-1670148434900-5f0af77ba500?q=80&w=2487&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        //file = "https://horlwarler.pythonanywhere.com/media/photos/3F118670-F194-41BB-A6BC-5E1749936B18/3F118670-F194-41BB-A6BC-5E1749936B18.1723925320052.png",
        title = ""
    )
    private val uniforms = List(30) {
        Uniform(
            uniformId = "uniformId$it",
            uniformCategory = categoryToString(it % 3),
            uniformUrl = categoryToUrl(it % 3)

        )
    }

    override suspend fun getPhotosByUser(imei: String): Result<List<UserPhoto>> {
        val photosResult = httpClient.safeRequest<List<UserPhotoDto>> {
            url(USER_PHOTOS)
            parameter("imei", imei)
        }
        if (photosResult is Result.Success) {
            val photos = photosResult
                .data!!
                .map {
                    it.mapToUserPhoto()
                }
            return Result.Success(photos)
        } else {
            return Result.Error(message = photosResult.message!!)
        }


    }

    override suspend fun getPhotoById(id: String, imei: String): Result<UserPhoto> {

        val photosResult = httpClient.safeRequest<UserPhotoDto> {
            url(USER_PHOTOS)
            parameter("imei", imei)
            parameter("id", id)
        }
        if (photosResult is Result.Success) {
            val photo = photosResult.data!!.mapToUserPhoto()

            return Result.Success(photo)
        } else {
            return Result.Error(message = photosResult.message!!)
        }


    }

    override suspend fun deletePhotoById(id: String, imei: String): Result<Unit> {
        val deleteResult = httpClient.safeRequest<Unit> {
            method = HttpMethod.Delete
            parameter("imei", imei)
            parameter("id", id)
        }

        return if (deleteResult is Result.Success) {
            Result.Success(Unit)
        } else {
            Result.Error(message = deleteResult.message!!)
        }


    }

    override suspend fun getPhotoSpecifications(): Result<List<Specification>> {
        val specificationResult = httpClient
            .safeRequest<List<SpecificationDto>> {
                url(SPECIFICATION)
                parameter("type", null)
            }
        if (specificationResult is Result.Success) {
            val specification = specificationResult
                .data!!
                .map {
                    it.mapToSpecification()
                }
            return Result.Success(specification)
        } else {
            return Result.Error(message = specificationResult.message!!)
        }


    }

    override suspend fun getSpecificationById(id: String): Result<Specification> {

        val specificationResult = httpClient
            .safeRequest<SpecificationDto> {
                parameter("id", id)
                url(SPECIFICATION)
                //  parameter("id", id)
            }
        if (specificationResult is Result.Success) {
            println("Specification result is ${specificationResult.data!!}")
            val specification = specificationResult
                .data!!
                .mapToSpecification()
            return Result.Success(specification)
        } else {
            return Result.Error(message = specificationResult.message!!)
        }

    }

    override suspend fun getUniforms(): Result<List<Uniform>> {
        val uniformResult = httpClient
            .safeRequest<List<UniformDto>> {
                url(UNIFORMS)
            }
        return Result.Success(uniforms)
        TODO()
        if (uniformResult is Result.Success) {
            val uniforms = uniformResult
                .data!!
                .map {
                    it.toUniform()
                }
            return Result.Success(uniforms)
        } else {
            return Result.Error(message = uniformResult.message!!)
        }

    }

    override suspend fun applyUniform(photoId: String, uniformId: String?): Result<UserPhoto> {
        val uniformRequest = ApplyUniformRequest(
            photoId = photoId,
            uniformId = uniformId
        )

        return Result.Success(tempUserPhoto)
        TODO()
        val uniformResult = httpClient
            .safeRequest<UserPhotoDto> {
                url(APPLY_UNIFORM)
                setBody(uniformRequest)
            }
        if (uniformResult is Result.Success) {
            val specification = uniformResult.data!!.mapToUserPhoto()

            return Result.Success(specification)
        } else {
            return Result.Error(message = uniformResult.message!!)
        }

    }

    override suspend fun applyBeauty(photoId: String, beautify: Boolean): Result<UserPhoto> {

        return Result.Success(tempUserPhoto)


        val uniformResult = httpClient
            .safeRequest<UserPhotoDto> {
                url(APPLY_BEAUTY)
                parameter("photoId", photoId)
            }
        if (uniformResult is Result.Success) {
            val specification = uniformResult.data!!.mapToUserPhoto()

            return Result.Success(specification)
        } else {
            return Result.Error(message = uniformResult.message!!)
        }


    }

    override suspend fun uploadPhoto(
        uuid: String,
        title: String,
        specificationId: String,
        dpi: Int,
        file: ByteArray
    ): Flow<UploadResult> {
        return channelFlow {
            send(UploadResult.Initial)

            send(UploadResult.Processing)
            // delay(1000)
            send(
                UploadResult.Completed(
                    userPhoto = UserPhoto(
                        id = "",
                        dateCreated = "",
                        expirationDate = "",
                        file = "https://plus.unsplash.com/premium_photo-1670148434900-5f0af77ba500?q=80&w=2487&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        //file = "https://horlwarler.pythonanywhere.com/media/photos/3F118670-F194-41BB-A6BC-5E1749936B18/3F118670-F194-41BB-A6BC-5E1749936B18.1723925320052.png",
                        title = ""
                    )
                )
            )
            delay(1000)
            send(UploadResult.Initial)

            return@channelFlow

            TODO("REMOVE TEMPORARY UPLOAD")
            val photoResult = httpClient

                .safeRequest<UserPhotoDto> {
                    url(UPLOAD)
                    method = HttpMethod.Post
                    setBody(
                        MultiPartFormDataContent(
                            formData {
                                append("user_uuid", uuid)
                                append("title", title)
                                append("dpi", dpi.toString())
                                append("specification", specificationId)

                                append(
                                    "file",
                                    file,
                                    Headers.build {
                                        append(HttpHeaders.ContentType, "image/png")
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "filename=\"$title.png\""
                                        )
                                    }
                                )
                            }
                        )
                    )
                    onUpload { bytesSentTotal, contentLength ->
                        val percent = (bytesSentTotal.toFloat() / contentLength.toFloat()) * 100
                        send(UploadResult.Uploading(percent.toString()))
                        println("UPLOADING $percent")

                        if (bytesSentTotal == contentLength) {
                            println("FINISH UPLOADING $percent")

                            send(UploadResult.Processing)
                        }
                    }
                }

            if (photoResult is Result.Success) {
                println("COMPLETED ${photoResult.data}")

                val photo = photoResult.data!!.mapToUserPhoto()
                send(UploadResult.Completed(photo))

            } else {
                println("ERROR UPLOADING PHOTO ${photoResult.message}")
                send(UploadResult.Error(photoResult.message!!))
            }
        }

    }


    private fun HttpRequestBuilder.endPoint(path: String) {

        url {
            takeFrom(BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)

        }
    }


    private suspend inline fun <reified T> HttpClient.safeRequest(
        block: HttpRequestBuilder.() -> Unit
    ): Result<T> {
        return try {
            val request = request {
                block()
            }
            val statusCode = request.status.value
            if (statusCode in listOf(200, 201, 202)) {
                val responseBody = request.body<T>()
                println("Response body $responseBody")
                Result.Success(responseBody)
            } else {
                //val errorMessage = request.toErrorMessage()
                val message = request.bodyAsText()
                Result.Error(message = message)
            }


        } catch (error: ClientRequestException) {

            Result.Error("Check your internet connection")
        } catch (error: ServerResponseException) {
            Result.Error("Please try again")
        } catch (error: IOException) {
            Result.Error("IO exceptions")
        } catch (error: SerializationException) {
            Result.Error("Serialization Exception Occurs")
        }
    }

    private fun HttpResponse.toException(): CustomException {
        return when (status) {

            HttpStatusCode.BadRequest -> CustomException.BadImage
            HttpStatusCode.NotFound -> CustomException.NoImage
            else -> CustomException.UnknownException
        }
    }

    private fun HttpResponse.toErrorMessage(): String {
        println("STATUS CODE IS  $status MESSAGE IS ${status.description}")
        return when (status) {

            HttpStatusCode.BadRequest -> "Please use a new photo"
            HttpStatusCode.NotFound -> "Item not found"
            HttpStatusCode.InternalServerError -> "Server Error Occurs"
            else -> "Unknown Exception"
        }
    }


}