package presentation.specification_details

import SharedViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.PhotoIdPreference
import domain.PhotoRepository
import domain.model.Specification
import domain.model.SpecificationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.Result
import util.UploadResult
import util.addToList

class SpecificationDetailsViewModel : SharedViewModel() {

    private val _specificationDetailsState: MutableStateFlow<SpecificationDetailsState> =
        MutableStateFlow(
            SpecificationDetailsState()
        )
    val photoIdPref: PhotoIdPreference by inject()

    val specificationDetailsState = _specificationDetailsState.asStateFlow()


    private val photoRepository: PhotoRepository by inject()

    fun event(event: SpecificationDetailsEvent) {
        when (event) {
            is SpecificationDetailsEvent.UploadImage -> {
                upload(event.image, event.specification)
            }

            is SpecificationDetailsEvent.SetImageState -> setImageState(event.imageState)
        }
    }

    private fun upload(
        image: ByteArray,
        specification: Specification
    ) {
        viewModelScope.launch {
            val uuid = photoIdPref.getUUID()!!

            photoRepository.uploadPhoto(
                uuId = uuid,
                title = generateTitle(uuid),
                dpi = specification.dpi,
                specificationId = specification.id,
                file = image
            )
                .collectLatest { uploadResult ->
                    _specificationDetailsState.update {
                        specificationDetailsState.value.copy(
                            imageState = uploadResult
                        )
                    }
                }
        }
    }

    private fun setImageState(
        imageState: UploadResult
    ) {
        _specificationDetailsState.update {
            specificationDetailsState.value.copy(
                imageState = imageState
            )
        }
    }

    private fun generateTitle(
        uuid: String,
    ): String {
        val time = Clock.System.now().toEpochMilliseconds()
        return "$uuid.$time"
    }

    fun getSpecification(
        specificationId: String
    ) {
        viewModelScope.launch {
            photoRepository.getSpecificationById(specificationId)

                .onCompletion {
                    _specificationDetailsState.update {
                        specificationDetailsState.value.copy(specificationLoading = false)
                    }
                }
                .catch { errorMessage ->
                    println("ERROR IN CATCH SPECIFICATION ${errorMessage.message}")

                    val errors = specificationDetailsState.value.errorMessage.addToList(
                        item = errorMessage.message ?: "Unknown Error"
                    )
                    _specificationDetailsState.update {
                        specificationDetailsState.value.copy(
                            specificationLoading = false,
                            errorMessage = errors
                        )
                    }
                }
                .collectLatest { result ->

                    when (result) {

                        is Result.Success -> {
                            _specificationDetailsState.update {
                                specificationDetailsState.value.copy(
                                    specificationLoading = false,
                                    //specification = result.data!!
                                )
                            }
                        }

                        is Result.Error -> {
                            println("ERROR IN SPECIFICATION ${result.message}")
                            val errors = specificationDetailsState.value.errorMessage.addToList(
                                item = result.message ?: "Unknown Error"
                            )
                            _specificationDetailsState.update {
                                specificationDetailsState.value.copy(
                                    specificationLoading = false,
                                    errorMessage = errors
                                )
                            }
                        }
                    }
                }

        }
    }
}