package data.remote.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class UserPhotoRequest(
    val uuId: String,
    val title: String,
    val specificationId: String,
    val dpi: Int,
    val file: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as UserPhotoRequest

        if (uuId != other.uuId) return false
        if (title != other.title) return false
        if (specificationId != other.specificationId) return false
        if (dpi != other.dpi) return false
        if (!file.contentEquals(other.file)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + specificationId.hashCode()
        result = 31 * result + dpi
        result = 31 * result + file.contentHashCode()
        return result
    }

}
