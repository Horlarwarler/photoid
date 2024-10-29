package domain.model

import androidx.compose.ui.graphics.ImageBitmap

sealed interface ImageProcessState {
    data class Processed(val image: ImageBitmap) : ImageProcessState
    data object Processing : ImageProcessState
    data object SelectingImage : ImageProcessState
    data class Error(val errorMessage: String) : ImageProcessState
    data object Initial : ImageProcessState

}