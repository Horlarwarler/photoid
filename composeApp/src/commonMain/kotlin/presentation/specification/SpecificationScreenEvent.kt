package presentation.specification

import domain.model.SpecificationType
import util.UploadResult

sealed interface SpecificationScreenEvent {
    data object OnCustomSpecificationClick : SpecificationScreenEvent

    data class OnSearchChange(val search: String) : SpecificationScreenEvent
    data class OnSpecificationTypeClick(val specificationType: SpecificationType) :
        SpecificationScreenEvent


}