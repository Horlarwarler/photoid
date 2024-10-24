package util

import io.ktor.http.HttpStatusCode
import org.jetbrains.compose.resources.StringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.invalid_photo
import photoid.composeapp.generated.resources.no_image
import photoid.composeapp.generated.resources.no_internet
import photoid.composeapp.generated.resources.server_error
import photoid.composeapp.generated.resources.unknown_error

sealed class CustomException(
    val errorCode: HttpStatusCode = HttpStatusCode.BadRequest,
    val errorMessage: StringResource
) : Exception() {


    //    Res.string.no
    data object NetworkException :
        CustomException(HttpStatusCode.BadRequest, Res.string.no_internet)

    //
    data object ServerException :
        CustomException(HttpStatusCode.InternalServerError, Res.string.server_error)

    //
    data object UnknownException :
        CustomException(HttpStatusCode.BadRequest, Res.string.unknown_error)

    //
    data object BadImage : CustomException(HttpStatusCode.BadRequest, Res.string.invalid_photo)
    data object NoImage : CustomException(HttpStatusCode.BadRequest, Res.string.no_image)

}

