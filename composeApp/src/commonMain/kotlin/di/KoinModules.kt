package di

import SharedViewModel
import data.PhotoIdPrefImpl
import data.remote.PhotoRepositoryImpl
import data.remote.api.KtorPhotoApi
import domain.PhotoApiService
import domain.PhotoApiService.EndPoint.BASE_URL
import domain.PhotoIdPreference
import domain.PhotoRepository
import domain.usecase.SpecificationUseCase
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import presentation.editor.EditorViewModel
import presentation.specification.SpecificationViewModel
import presentation.specification_details.SpecificationDetailsViewModel
import presentation.splash.SplashViewModel
import util.initLogger


val provideHttpClient = module {
    single {

        HttpClient {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v("HTTP Client", null, message)
                    }
                }
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                url(BASE_URL)
            }

            install(ContentNegotiation) {

                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        coerceInputValues = true
                    },
                    contentType = ContentType.Application.Json
                )
            }
        }

    }
}

val provideKtorApi = module {
    single<PhotoApiService> {
        KtorPhotoApi(get())
    }
}

val providePhotoRepository = module {
    single<PhotoRepository> {
        PhotoRepositoryImpl(get())
    }
}

val splashViewModel = module {
    viewModelOf(::SplashViewModel)
}

val specificationViewModel = module {

    viewModelOf(::SpecificationViewModel)
}

val provideSpecificationUseCase = module {
    single {
        SpecificationUseCase(get())
    }
}

val providesSpecificationDetailsViewModel = module {
    viewModelOf(::SpecificationDetailsViewModel)
}

val providePref = module {
    single<PhotoIdPreference> {
        PhotoIdPrefImpl()
    }
}

val provideSharedViewModel = module {

    viewModelOf(::SharedViewModel)
}

val provideEditorViewModel = module {
    viewModelOf(::EditorViewModel)
}

fun appModules() =
    listOf(
        provideHttpClient,
        providePhotoRepository,
        provideKtorApi,
        splashViewModel,
        provideSpecificationUseCase,
        specificationViewModel,
        providesSpecificationDetailsViewModel,
        providePref,
        provideSharedViewModel,
        provideEditorViewModel
    )