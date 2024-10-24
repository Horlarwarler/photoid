package presentation.home

import domain.model.Specification

data class HomeScreenState(
    val popularSpecification: List<Specification> = emptyList()
)