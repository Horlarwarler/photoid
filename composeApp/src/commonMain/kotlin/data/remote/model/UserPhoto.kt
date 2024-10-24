package data.remote.model

import androidx.compose.runtime.Immutable
import domain.model.UserPhoto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class UserPhotoDto(
    val id: String,
    val title: String,
    val file: String,
    @SerialName("date_created")
    val dateCreated: String,
    @SerialName("date_modified")
    val expirationDate: String,
    @SerialName("user")
    val uuid: String
)


fun UserPhotoDto.mapToUserPhoto(): UserPhoto {

    return UserPhoto(
        id = id,
        title = title,
        file = file,
        dateCreated = dateCreated,
        expirationDate = expirationDate

    )
}