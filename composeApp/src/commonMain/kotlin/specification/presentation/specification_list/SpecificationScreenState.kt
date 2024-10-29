package specification.presentation.specification_list

import domain.model.Specification
import domain.model.SpecificationType

data class SpecificationScreenState(
    val selectedSpecificationType: SpecificationType = SpecificationType.PASSPORT,
    val specifications: List<Specification> = emptyList(),
    val filteredOrSortedSpecification: List<Specification> = emptyList(),
    val errorMessages: List<String> = emptyList(),
    val isLoading: Boolean = true,
    val searchText: String = ""
)
