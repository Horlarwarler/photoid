package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.arrow_left
import ui.black
import ui.mediumPadding
import ui.smallPadding
import ui.white

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    backButtonClick: () -> Unit
) {
    Icon(
        modifier = modifier
            .clickable {
                backButtonClick()
            }
            .clip(CircleShape)
            .background(white)
            .padding(smallPadding),
        painter = painterResource(Res.drawable.arrow_left),
        tint = black,
        contentDescription = null,
    )
}