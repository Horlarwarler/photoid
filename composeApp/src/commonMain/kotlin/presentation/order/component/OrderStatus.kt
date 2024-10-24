package presentation.order.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.OrderStatus
import ui.largePadding
import ui.light_red
import ui.mediumPadding
import ui.purple
import ui.red
import ui.smallPadding
import ui.white

@Composable
fun OrderStatus(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus
) {
    val background = if (orderStatus == OrderStatus.PAID) purple else light_red
    val textColor = if (orderStatus == OrderStatus.PAID) white else red
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(largePadding))
            .background(background)
            .padding(
                vertical = 5.dp,
                horizontal = 15.dp
            ),
        text = orderStatus.name,
        color = textColor,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold

    )
}