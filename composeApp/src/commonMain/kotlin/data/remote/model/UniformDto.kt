package data.remote.model

import domain.model.Uniform
import kotlinx.serialization.Serializable


@Serializable
data class UniformDto(
    val uniformId: String,
    val uniformUrl: String,
    val uniformCategory: String
)

fun UniformDto.toUniform(): Uniform {

    return Uniform(
        uniformId = uniformId,
        uniformCategory = uniformCategory,
        uniformUrl = uniformUrl
    )
}