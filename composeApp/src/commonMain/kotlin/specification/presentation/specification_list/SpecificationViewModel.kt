package specification.presentation.specification_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.SpecificationType
import domain.usecase.SpecificationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.Result
import util.addToList

class SpecificationViewModel : ViewModel(), KoinComponent {

    private val specificationUseCase: SpecificationUseCase by inject()

    private val _specificationState: MutableStateFlow<SpecificationScreenState> = MutableStateFlow(
        SpecificationScreenState()
    )
    val specificationScreenState = _specificationState.asStateFlow()

    private var searchJob: Job? = null

    init {
        getAllSpecifications()
    }

    fun handleSpecificationEvent(
        event: SpecificationScreenEvent
    ) {
        when (event) {
            SpecificationScreenEvent.OnCustomSpecificationClick -> {

            }


            is SpecificationScreenEvent.OnSearchChange -> searchChange(event.search)
            is SpecificationScreenEvent.OnSpecificationTypeClick -> selectSpecification(event.specificationType)
        }
    }


    private fun getAllSpecifications(
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            specificationUseCase.invoke()
                .onStart {
                    println("START SPECIFICATION")

                    _specificationState.update {
                        specificationScreenState.value.copy(
                            isLoading = true
                        )
                    }
                }
                .onCompletion {
                    println("FINISH SPECIFICATION")

                    _specificationState.update {
                        specificationScreenState.value.copy(
                            isLoading = false
                        )
                    }
                }
                .catch { error ->
                    println("ERROR SPECIFICATION ${error.message}")
                    _specificationState.update {
                        specificationScreenState.value.copy(
                            isLoading = false,
                            errorMessages = specificationScreenState.value.errorMessages.addToList(
                                error.message ?: "Unknown Error"
                            )
                        )
                    }
                }
                .collectLatest { result ->

                    when (result) {


                        is Result.Success -> {

                            val specifications = result.data!!
                            _specificationState.update {
                                specificationScreenState.value.copy(
                                    specifications = specifications,
                                    filteredOrSortedSpecification = specifications.filter { specification ->
                                        specification.specificationType == it.selectedSpecificationType
                                    }
                                )

                            }
                        }


                        is Result.Error -> {

                            println("ERRROR ${result.message}")
                        }
                    }
                }

        }
    }

    private fun selectSpecification(specificationType: SpecificationType) {
        val specifications = specificationScreenState.value.specifications
        _specificationState.update {
            specificationScreenState.value.copy(
                selectedSpecificationType = specificationType,
                filteredOrSortedSpecification = specifications.filter {
                    it.specificationType == specificationType
                }
            )
        }
    }

    private fun searchChange(searchText: String) {
        val specifications = specificationScreenState.value.specifications
        //  val specification
        searchJob?.cancel()
        searchJob = Job()
        _specificationState.update {
            specificationScreenState.value.copy(
                searchText = searchText
            )
        }
        CoroutineScope(Dispatchers.Default + searchJob!!).launch {
            _specificationState.update {
                specificationScreenState.value.copy(
                    filteredOrSortedSpecification = specifications.filter {
                        it.name.lowercase().contains(searchText.lowercase())
                    }
                )
            }

        }

    }
}