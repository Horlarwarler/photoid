package presentation.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.PhotoRepository
import domain.model.Uniform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.Result

class EditorViewModel() : ViewModel(), KoinComponent {
    private val _editorScreenState = MutableStateFlow(EditorScreenState())
    val editorScreenState: StateFlow<EditorScreenState> = _editorScreenState

    private val photoRepository: PhotoRepository by inject()

    private var uniforms: List<Uniform> = emptyList()

    init {
        getAllUniforms()
    }


    fun event(event: EditorEvent) {
        when (event) {
            is EditorEvent.SetUserPhoto -> {
                _editorScreenState.value =
                    _editorScreenState.value.copy(userPhoto = event.userPhoto)
            }

            is EditorEvent.SetSpecification -> {
                _editorScreenState.value =
                    _editorScreenState.value.copy(specification = event.specification)
            }

            is EditorEvent.SetSelectedUniform -> setSelectedUniform(event.uniformId)

            is EditorEvent.SetSelectedColorIndex -> {
                _editorScreenState.value =
                    _editorScreenState.value.copy(selectedColorIndex = event.index)
            }

            is EditorEvent.SetSelectedUniformCategory -> setSelectedUniformCategory(event.categoryIndex)

            is EditorEvent.SetBeautifyImage -> beautifyImage(beautify = event.beautify)
        }
    }

    private fun setSelectedUniform(
        selectedUniform: String?
    ) {
        if (selectedUniform == editorScreenState.value.selectedUniformId) {
            return
        }
        _editorScreenState.value =
            _editorScreenState.value.copy(selectedUniformId = selectedUniform)
        viewModelScope.launch {
            photoRepository.applyUniform(
                uniformId = selectedUniform,
                photoId = _editorScreenState.value.userPhoto!!.id
            )
                .onStart {
                    _editorScreenState.value = _editorScreenState.value.copy(isLoading = true)
                }
                .catch {
                    println("ERROR ${it.message}")
                    _editorScreenState.value = _editorScreenState.value.copy(isLoading = false)

                }
                .collectLatest { photoResult ->
                    when (photoResult) {
                        is Result.Success -> {
                            _editorScreenState.value = _editorScreenState.value.copy(
                                isLoading = false,
                                userPhoto = photoResult.data!!
                            )

                        }

                        is Result.Error -> {
                            _editorScreenState.value =
                                _editorScreenState.value.copy(isLoading = false)
                            println("ERROR ${photoResult.message}")
                        }
                    }
                }
        }
    }

    private fun setSelectedUniformCategory(index: Int) {
        val categoryToString = when (index) {
            0 -> "men"
            1 -> "women"
            else -> {
                "children"
            }
        }
        _editorScreenState.value = _editorScreenState.value.copy(
            selectedUniformCategory = index,
            uniforms = uniforms.filter {
                it.uniformCategory.lowercase() == categoryToString
            }
        )
    }

    private fun beautifyImage(
        beautify: Boolean
    ) {
        if (beautify == editorScreenState.value.beautifyImage) {
            return
        }
        _editorScreenState.value =
            _editorScreenState.value.copy(beautifyImage = beautify)
        viewModelScope.launch {
            photoRepository.applyBeauty(
                photoId = _editorScreenState.value.userPhoto!!.id,
                beautify = beautify
            )
                .onStart {
                    _editorScreenState.value = _editorScreenState.value.copy(isLoading = true)
                }
                .catch {
                    println("ERROR ${it.message}")
                    _editorScreenState.value = _editorScreenState.value.copy(isLoading = false)

                }
                .collectLatest { photoResult ->
                    when (photoResult) {
                        is Result.Success -> {
                            _editorScreenState.value = _editorScreenState.value.copy(
                                isLoading = false,
                                userPhoto = photoResult.data!!
                            )

                        }

                        is Result.Error -> {
                            _editorScreenState.value =
                                _editorScreenState.value.copy(isLoading = false)
                            println("ERROR ${photoResult.message}")
                        }
                    }
                }
        }
    }

    private fun getAllUniforms() {
        viewModelScope.launch {
            photoRepository.getUniforms()
                .onStart {

                    _editorScreenState.value =
                        _editorScreenState.value.copy(uniformIsLoading = true)
                }
                .catch {
                    println("ERROR ${it.message}")
                    _editorScreenState.value =
                        _editorScreenState.value.copy(uniformIsLoading = false)
                }
                .collectLatest { uniformsResult ->
                    when (uniformsResult) {
                        is Result.Success -> {
                            val categoryToString =
                                when (editorScreenState.value.selectedUniformCategory) {
                                    0 -> "men"
                                    1 -> "women"
                                    else -> {
                                        "children"
                                    }
                                }
                            uniforms = uniformsResult.data!!
                            _editorScreenState.value = _editorScreenState.value.copy(
                                uniformIsLoading = false,
                                uniforms = uniforms.filter {
                                    it.uniformCategory.lowercase() == categoryToString
                                }
                            )
                        }

                        is Result.Error -> {
                            _editorScreenState.value =
                                _editorScreenState.value.copy(uniformIsLoading = false)
                            println("ERROR ${uniformsResult.message}")
                        }
                    }
                }
        }
    }


}