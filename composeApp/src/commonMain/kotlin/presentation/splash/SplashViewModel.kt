package presentation.splash

import SharedViewModel
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import domain.PhotoIdPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import util.generateUUID


class SplashViewModel() : SharedViewModel() {

    private val photoIdPref: PhotoIdPreference by inject()

    private val _splashScreenState: MutableStateFlow<SplashScreenState> = MutableStateFlow(
        SplashScreenState()
    )
    val splashScreenState = _splashScreenState.asStateFlow()


    init {
        saveUUID()
        checkInternetConnection()

    }

    private fun checkInternetConnection() {
        viewModelScope.launch {
            isConnected.collectLatest { internetConnected ->
                println("INTERNET IS CONNECTED COLLECT CHANGES $internetConnected")

                _splashScreenState.update {
                    splashScreenState.value.copy(
                        navigateToHomeScreen = internetConnected
                    )
                }

            }
        }

    }


    private fun saveUUID() {
        photoIdPref.getUUID() ?: run {
            val uuid = generateUUID()
            photoIdPref.setUUID(uuid)
            return
        }


    }

    override fun onCleared() {
        super.onCleared()
    }

}
