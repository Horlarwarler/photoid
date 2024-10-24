package presentation.order.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.OrderItemModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.man
import photoid.composeapp.generated.resources.production_time
import ui.mediumPadding
import ui.purple
import ui.smallPadding
import ui.white

@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    orderItemModel: OrderItemModel
) {

    Card(
        modifier = modifier,
        backgroundColor = white,
        elevation = 4.dp,
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 15.dp, vertical = 15.dp
            ).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(
                    width = 65.dp,
                    height = 80.dp
                )
                    .fillMaxSize(),
                painter = painterResource(Res.drawable.man),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(smallPadding))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier,
                        text = orderItemModel.orderName,
                        color = purple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    OrderStatus(
                        orderStatus = orderItemModel.status,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    OrderTime(
                        time = orderItemModel.productionTime,
                        timeDescription = stringResource(Res.string.production_time)
                    )
                    Spacer(modifier = Modifier.width(mediumPadding))

                    OrderTime(
                        time = orderItemModel.productionTime,
                        timeDescription = stringResource(Res.string.production_time)
                    )
                }

            }

        }

    }
}