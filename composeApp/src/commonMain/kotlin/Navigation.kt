import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import domain.model.OrderItemModel
import domain.model.OrderStatus
import domain.model.Specification
import domain.model.SpecificationType
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.editor.EditorEvent
import presentation.editor.EditorScreen
import presentation.editor.EditorViewModel
import presentation.home.HomeScreen
import presentation.home.HomeScreenState
import presentation.invitation.InvitationScreen
import presentation.invitation.InvitationScreenState
import presentation.order.OrderScreen
import presentation.order.OrdersScreenState
import specification.presentation.specification_list.SpecificationScreen
import specification.presentation.specification_list.SpecificationViewModel
import presentation.specification_details.SpecificationDetailsEvent
import presentation.specification_details.SpecificationDetailsScreen
import presentation.specification_details.SpecificationDetailsViewModel
import presentation.splash.SplashScreen
import presentation.splash.SplashViewModel
import util.Constant.EDITOR_SCREEN
import util.Constant.HOME_SCREEN
import util.Constant.INVITATION_SCREEN
import util.Constant.ORDER_SCREEN
import util.Constant.SPECIFICATION_DETAIL_SCREEN
import util.Constant.SPECIFICATION_SCREEN
import util.Constant.SPLASH_SCREEN
import util.UploadResult

@OptIn(KoinExperimentalAPI::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val screen by remember(Unit) {
        mutableStateOf(Screen(navController))
    }
    val sharedViewModel: SharedViewModel = koinViewModel()
    val internetState = sharedViewModel.isConnected.collectAsState().value



    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {

        val listOfSpecification: List<Specification> = List(15) {
            Specification(
                id = "hello",
                width = it + 40,
                height = it + 30,
                description = "Description $it",
                specificationType = SpecificationType.VISA,
                name = "Name"
            )
        }

        composable(SPLASH_SCREEN) {
            val splashViewModel: SplashViewModel = koinViewModel()
            println("INTERNET, INTERNET STATE CHANGE $internetState")

            SplashScreen(
                isConnected = internetState
            ) {
                screen.navigateToHomeScreen()

            }
        }
        composable(HOME_SCREEN) {
            val homeScreenState = HomeScreenState(
                popularSpecification = listOfSpecification
            )
            HomeScreen(
                onOrderClick = screen.navigateToOrder,
                onInviteClick = screen.navigateToInvitation,
                makeIdClick = screen.navigateToSpecification,
                homeScreenState = homeScreenState,
                onItemSelected = {
                    sharedViewModel.setSpecification(it)
                    screen.navigateToSpecificationDetail()
                }
            )
        }

        composable(SPECIFICATION_SCREEN) {
            val specificationViewModel: SpecificationViewModel = koinViewModel()

            val specificationState =
                specificationViewModel.specificationScreenState.collectAsState().value
            SpecificationScreen(
                onSpecificationClick = { specification ->
                    sharedViewModel.setSpecification(specification)
                    screen.navigateToSpecificationDetail()
                },
                onScreenEvent = specificationViewModel::handleSpecificationEvent,
                onBackClick = {
                    screen.navigateBack()
                },
                specificationScreenState = specificationState
            )
        }
        composable(
            SPECIFICATION_DETAIL_SCREEN,
//            arguments = listOf(
//                navArgument(
//                    name = "id",
//                    builder = {
//                        type = NavType.StringType
//                    }
//                )
//            )
        ) {

            val specificationViewModel: SpecificationDetailsViewModel = koinViewModel()

            val specificationDetailsState =
                specificationViewModel.specificationDetailsState.collectAsState().value

            SpecificationDetailsScreen(
                specification = sharedViewModel.specificationSelected,
                specificationDetailsState = specificationDetailsState,
                event = specificationViewModel::event,
                navigateToEditor = { photo, specification ->
                    sharedViewModel.setUserPhoto(photo)
                    sharedViewModel.setSpecification(specification)
                    screen.navigateToEditor()
                    specificationViewModel.event(
                        SpecificationDetailsEvent.SetImageState(
                            UploadResult.Initial
                        )
                    )
                },
                onBackClick = {
                    screen.navigateBack()
                    // sharedViewModel.setSpecification()
                }

            )
        }
        composable(EDITOR_SCREEN) {
            val editorViewModel: EditorViewModel = koinViewModel()
            val editorScreenState = editorViewModel.editorScreenState.collectAsState().value

            LaunchedEffect(key1 = true) {
                editorViewModel.event(EditorEvent.SetUserPhoto(sharedViewModel.currentUploadedPhoto))
                editorViewModel.event(EditorEvent.SetSpecification(sharedViewModel.specificationSelected))
            }
            EditorScreen(
                event = editorViewModel::event,
                editorScreenState = editorScreenState,
                navigateBack = screen.navigateBack
            )
        }
        composable(ORDER_SCREEN) {
            val orders = (1..8).map { it ->
                OrderItemModel(
                    orderId = "snsksks",
                    orderName = "India Visa",
                    productionTime = "SEP 1$it 2024",
                    expirationDate = "SEP 2$it 2024",
                    status = if (it % 2 == 0) OrderStatus.PAID else OrderStatus.PENDING
                )
            }

            OrderScreen(
                onOrderClick = {

                },
                onBackPressed = screen.navigateBack,
                onMakeOrderClick = screen.navigateToSpecification,
                ordersScreenState = OrdersScreenState(
                    orders = orders
                )
            )
        }

        composable(INVITATION_SCREEN) {
            InvitationScreen(
                onBackClick = screen.navigateBack,
                invitationScreenState = InvitationScreenState(
                    myInvitationCode = "123456",
                    invitationCode = "123456"
                )
            )
        }

    }

}
