package domain.model

data class UserPhoto(
    val id: String,
    val title: String,
    val file: String,
    val dateCreated: String,
    val expirationDate: String
)
