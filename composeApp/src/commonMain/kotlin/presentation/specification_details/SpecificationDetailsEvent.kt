package presentation.specification_details

import domain.model.Specification
import presentation.specification.SpecificationScreenEvent
import util.UploadResult

sealed interface SpecificationDetailsEvent {

    data class UploadImage(
        val image: ByteArray,
        val specification: Specification
    ) : SpecificationDetailsEvent {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as UploadImage

            return image.contentEquals(other.image)
        }

        override fun hashCode(): Int {
            return image.contentHashCode()
        }
    }

    data class SetImageState(val imageState: UploadResult) : SpecificationDetailsEvent


}