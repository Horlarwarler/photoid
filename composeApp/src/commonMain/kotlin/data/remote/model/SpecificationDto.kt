package data.remote.model

import domain.model.Specification
import domain.model.SpecificationType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpecificationDto(
    val id: String,
    val name: String,
    val height: Int,
    val width: Int,
    val description: String?,
    @SerialName("type")
    val specificationType: String
)


fun SpecificationDto.mapToSpecification(): Specification {

    return Specification(
        id = id,
        width = width,
        height = height,
        name = name,
        description = description,
        specificationType = SpecificationType.valueOf(specificationType.uppercase())
    )
}