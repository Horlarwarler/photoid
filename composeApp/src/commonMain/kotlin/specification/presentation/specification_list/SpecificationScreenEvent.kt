package specification.presentation.specification_list

import domain.model.SpecificationType

sealed interface SpecificationScreenEvent {
    data object OnCustomSpecificationClick : SpecificationScreenEvent

    data class OnSearchChange(val search: String) : SpecificationScreenEvent
    data class OnSpecificationTypeClick(val specificationType: SpecificationType) :
        SpecificationScreenEvent


}