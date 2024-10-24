package presentation.editor.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.mark
import ui.eighth_background
import ui.fifth_background
import ui.first_background
import ui.fourth_background
import ui.ninth_background
import ui.second_background
import ui.seventh_background
import ui.sixth_background
import ui.smallPadding
import ui.third_background
import ui.white

@Composable
fun ColorSelector(
    modifier: Modifier,
    currentIndex: Int,
    colors: List<Color>,
    onColorSelected: (Int) -> Unit
) {


    LazyRow(
        modifier = modifier
    ) {
        items(colors.size) { index ->
            ColorItem(
                color = colors[index],
                isSelected = index == currentIndex,
                onClick = {
                    onColorSelected(index)
                }
            )
        }
    }


}

@Composable
private fun ColorItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .padding(end = smallPadding)
            .clickable {
                onClick()
            }
            .clip(CircleShape)
            .background(color)
            .size(25.dp)
    ) {
        if (isSelected) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(Res.drawable.mark),
                contentDescription = "check",
                tint = white
            )
        }
    }
}