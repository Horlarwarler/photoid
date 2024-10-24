package presentation.order

import CustomTextButton
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.OrderItemModel
import domain.model.OrderStatus
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.empty_order
import photoid.composeapp.generated.resources.make_id_photo
import photoid.composeapp.generated.resources.no
import photoid.composeapp.generated.resources.no_order
import photoid.composeapp.generated.resources.order_page
import presentation.components.TextAppBar
import presentation.order.component.OrderItem
import ui.background
import ui.largePadding
import ui.mediumPadding
import ui.purple
import ui.white

@Composable
fun OrderScreen(
    onBackPressed: () -> Unit,
    onOrderClick: (String) -> Unit,
    ordersScreenState: OrdersScreenState,
    onMakeOrderClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {

        Box(
            modifier = Modifier
                .weight(0.25f)
                .clip(
                    RoundedCornerShape(
                        bottomStart = mediumPadding,
                        bottomEnd = mediumPadding
                    )
                )
                .background(background)
                .fillMaxSize()

                .padding(
                    horizontal = mediumPadding
                )
        ) {
            TextAppBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = mediumPadding),
                text = stringResource(Res.string.order_page),
                onBackClick = onBackPressed
            )
            CustomTextButton(
                modifier = Modifier.align(
                    Alignment.BottomCenter
                ).padding(bottom = mediumPadding),
                buttonText = stringResource(Res.string.make_id_photo),
                backgroundColor = purple,
                buttonColor = white,
                onClick = onMakeOrderClick
            )
        }
        if (ordersScreenState.orders.isEmpty()) {
            EmptyOrder(
                modifier = Modifier.weight(0.75f)
            )
            return@Column
        }
        LazyColumn(
            modifier = Modifier
                .weight(0.75f)
                .padding(horizontal = mediumPadding, vertical = largePadding)
        ) {
            items(
                items = ordersScreenState.orders
            ) { item ->
                OrderItem(
                    orderItemModel = item,
                    modifier = Modifier.padding(bottom = mediumPadding)
                )
            }
        }


    }

}

@Composable
private fun EmptyOrder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(
                    width = 215.dp, height = 150.dp
                ),
                painter = painterResource(Res.drawable.empty_order),
                contentDescription = null,

                )
            Spacer(modifier = Modifier.height(mediumPadding))
            Text(
                modifier = Modifier.padding(horizontal = mediumPadding),
                text = stringResource(Res.string.no_order),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center

            )
        }


    }
}