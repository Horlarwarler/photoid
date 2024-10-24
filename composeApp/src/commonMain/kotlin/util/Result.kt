package util

import domain.model.UserPhoto


sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(message: String) : Result<T>(message = message)

    //data class Loading<T>(val isLoading:Boolean)  : Result<T>()
}

sealed class UploadResult() {
    data object Initial : UploadResult()
    data class Uploading(val percent: String) : UploadResult()
    data object Processing : UploadResult()
    data class Completed(val userPhoto: UserPhoto) : UploadResult()
    data class Error(val errorMessage: String) : UploadResult()
}

//sealed class ApiRes