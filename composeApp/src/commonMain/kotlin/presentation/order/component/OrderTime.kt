package presentation.order.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ui.light_black
import ui.light_grey

@Composable
fun OrderTime(
    modifier: Modifier = Modifier,
    time: String,
    timeDescription: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = time,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            color = light_black

        )
        Text(
            text = timeDescription,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            textAlign = TextAlign.Start,
            color = light_grey

        )
    }
}