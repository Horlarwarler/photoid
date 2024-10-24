import androidx.navigation.NavController
import util.Constant.EDITOR_SCREEN
import util.Constant.HOME_SCREEN
import util.Constant.INVITATION_SCREEN
import util.Constant.ORDER_SCREEN
import util.Constant.SPECIFICATION_DETAIL_SCREEN
import util.Constant.SPECIFICATION_SCREEN
import util.Constant.SPLASH_SCREEN

class Screen(
    private val navController: NavController
) {

    val navigateToInvitation: () -> Unit = {
        navController.navigate(INVITATION_SCREEN)
    }
    val navigateToOrder: () -> Unit = {
        navController.navigate(ORDER_SCREEN)

    }
    val navigateToHomeScreen: () -> Unit = {
        navController.navigate(HOME_SCREEN) {
            popUpTo(SPLASH_SCREEN) {
                inclusive = true
            }
        }
    }
    val navigateToSpecificationDetail: () -> Unit = {
        navController.navigate(SPECIFICATION_DETAIL_SCREEN)
    }
    val navigateToSpecification: () -> Unit = {
        navController.navigate(SPECIFICATION_SCREEN)
    }
    val navigateBack: () -> Unit = {
        navController.navigateUp()
    }
    val navigateToEditor: () -> Unit = {
        navController.navigate(EDITOR_SCREEN) {
            popUpTo(SPECIFICATION_DETAIL_SCREEN)
        }
    }

}