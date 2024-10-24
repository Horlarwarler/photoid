package presentation.specification_details

import domain.model.ImageProcessState
import domain.model.Specification
import util.UploadResult

data class SpecificationDetailsState(
    // val specification:Specification? = null,
    val imageIsUploading: Boolean = false,
    val specificationLoading: Boolean = true,
    val errorMessage: List<String> = emptyList(),
    val imageState: UploadResult = UploadResult.Initial,
    val imageProcessingState: ImageProcessState = ImageProcessState.INITIAL
    //val
)
