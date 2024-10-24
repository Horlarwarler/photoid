package domain.usecase

import domain.PhotoRepository
import domain.model.Specification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import util.Result

class SpecificationUseCase(
    private val photoRepository: PhotoRepository
) {

    operator fun invoke(): Flow<Result<List<Specification>>> {
        return channelFlow {

            photoRepository.getPhotoSpecifications()
                .collectLatest { result ->

                    when (result) {
                        is Result.Error -> {
                            send(
                                Result.Error(
                                    message = result.message!!,
                                )
                            )
                            println("REQUEST ${result.message}")

                        }


                        is Result.Success -> {
                            val specifications = result.data!!
                            println("REQUEST ${specifications}")
                            send(Result.Success(specifications))

                        }
                    }
                }
        }


    }
}