package presentation.editor

import domain.model.Specification
import domain.model.Uniform
import domain.model.UserPhoto

data class EditorScreenState(
    val userPhoto: UserPhoto? = null,
    val specification: Specification? = null,
    val selectedUniformId: String? = null,
    val selectedColorIndex: Int = 0,
    val selectedUniformCategory: Int = 0,
    val beautifyImage: Boolean = false,
    val isLoading: Boolean = false,
    val uniformIsLoading: Boolean = false,
    val uniforms: List<Uniform> = emptyList()
)
