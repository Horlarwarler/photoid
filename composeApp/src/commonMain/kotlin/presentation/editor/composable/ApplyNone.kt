package presentation.editor.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.none
import ui.light_grey
import ui.purple
import ui.smallPadding
import ui.white

@Composable
fun ApplyNone(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Box(
        modifier = modifier
            .padding(end = smallPadding)
            .clickable {
                onClick()
            }
            .border(
                width = 1.dp,
                color = if (isSelected) purple else Color.Transparent,
                shape = RoundedCornerShape(15.dp)
            )
            .size(height = 70.dp, width = 50.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(white),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(Res.drawable.none),
            tint = light_grey,
            contentDescription = null
        )
    }
}