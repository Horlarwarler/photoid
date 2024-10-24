package presentation.editor

import domain.model.Specification
import domain.model.Uniform
import domain.model.UserPhoto

sealed interface EditorEvent {
    data class SetUserPhoto(val userPhoto: UserPhoto) : EditorEvent
    data class SetSpecification(val specification: Specification) : EditorEvent
    data class SetSelectedUniform(val uniformId: String?) : EditorEvent
    data class SetSelectedColorIndex(val index: Int) : EditorEvent
    data class SetSelectedUniformCategory(val categoryIndex: Int) : EditorEvent
    data class SetBeautifyImage(val beautify: Boolean) : EditorEvent
}