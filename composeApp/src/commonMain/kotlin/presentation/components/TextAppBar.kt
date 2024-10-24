package presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ui.black

@Composable
fun TextAppBar(
    modifier: Modifier = Modifier,
    text: String,
    onBackClick: () -> Unit,

    ) {

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        BackButton(
            modifier = Modifier.align(
                Alignment.CenterStart
            ),
            backButtonClick = onBackClick
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            color = black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }
}