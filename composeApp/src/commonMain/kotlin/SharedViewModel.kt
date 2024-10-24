import androidx.lifecycle.ViewModel
import com.plusmobileapps.konnectivity.Konnectivity
import domain.model.Specification
import domain.model.UserPhoto
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

open class SharedViewModel : ViewModel(), KoinComponent {

    private val kConnectivity by lazy {
        Konnectivity()
    }

    lateinit var specificationSelected: Specification
        private set

    lateinit var currentUploadedPhoto: UserPhoto
        private set
    val isConnected: StateFlow<Boolean> = kConnectivity.isConnectedState

    fun setSpecification(specification: Specification) {
        specificationSelected = specification
    }

    fun setUserPhoto(
        userPhoto: UserPhoto
    ) {
        currentUploadedPhoto = userPhoto
    }


}