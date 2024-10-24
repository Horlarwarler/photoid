package presentation.home

import CustomTextButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.Specification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.make_id_photo
import photoid.composeapp.generated.resources.popular_size
import presentation.components.PhotoSpecificationItem
import presentation.home.components.HomeDrawer
import presentation.home.components.HomeScreenAppBar
import ui.background
import ui.black
import ui.largePadding
import ui.mediumPadding
import ui.purple
import ui.white

@Composable
fun HomeScreen(
    onOrderClick: () -> Unit,
    onInviteClick: () -> Unit,
    makeIdClick: () -> Unit,
    homeScreenState: HomeScreenState,
    onItemSelected: (Specification) -> Unit
) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val drawerClick: () -> Unit = {
        coroutineScope.launch(Dispatchers.Main) {
            if (scaffoldState.drawerState.isOpen) {
                scaffoldState.drawerState.close()
            } else {
                scaffoldState.drawerState.open()
            }
        }

    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = background,

        topBar = {
            Column(
                modifier = Modifier
                    .background(white)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = mediumPadding,
                            bottomEnd = mediumPadding
                        )
                    )
                    .background(color = background)
                    .padding(
                        horizontal = mediumPadding,
                        vertical = largePadding
                    )

            ) {
                HomeScreenAppBar(
                    onOrderClick = onOrderClick,
                    onBuggerClick = drawerClick,
                    onInviteClick = onInviteClick
                )

                Spacer(modifier = Modifier.height(mediumPadding))
                CustomTextButton(
                    buttonText = stringResource(Res.string.make_id_photo),
                    onClick = makeIdClick,
                    buttonColor = Color.White,
                    backgroundColor = purple
                )
                Spacer(modifier = Modifier.height(mediumPadding))


            }

        },
        drawerContent = {
            HomeDrawer()
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(white)
                .padding(vertical = mediumPadding, horizontal = mediumPadding)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(bottom = 15.dp),
                    text = stringResource(Res.string.popular_size),
                    fontSize = 18.sp,
                    color = black,
                    fontWeight = FontWeight.SemiBold
                )
            }
            items(homeScreenState.popularSpecification) { item ->
                PhotoSpecificationItem(
                    onClick = { onItemSelected(item) },
                    specification = item
                )
            }

        }
    }
}