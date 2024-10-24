package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApplyUniformRequest(
    val photoId: String,
    val uniformId: String?
)
