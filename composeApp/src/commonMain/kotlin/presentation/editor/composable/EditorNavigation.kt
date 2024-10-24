package presentation.editor.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.background
import photoid.composeapp.generated.resources.beauty
import photoid.composeapp.generated.resources.beuty
import photoid.composeapp.generated.resources.print
import photoid.composeapp.generated.resources.uniform
import ui.background_navigation_color
import ui.mediumPadding
import ui.navigation_color
import ui.purple
import ui.smallPadding

@Composable
fun EditorNavigation(
    modifier: Modifier = Modifier,
    currentIndex: Int = 0,
    onClick: (Int) -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        EditorNavigationItem(
            isSelected = currentIndex == 0,
            icon = Res.drawable.background,
            text = Res.string.background,
            onClick = {
                onClick(0)
            }
        )
        EditorNavigationItem(
            isSelected = currentIndex == 1,
            icon = Res.drawable.uniform,
            text = Res.string.uniform,
            onClick = {
                onClick(1)
            }
        )
        EditorNavigationItem(
            isSelected = currentIndex == 2,
            icon = Res.drawable.beuty,
            text = Res.string.beauty,
            onClick = {
                onClick(2)
            }
        )
        EditorNavigationItem(
            isSelected = currentIndex == 3,
            icon = Res.drawable.print,
            text = Res.string.print,
            onClick = {
                onClick(3)
            }
        )

    }
}

@Composable
private fun EditorNavigationItem(
    isSelected: Boolean,
    icon: DrawableResource,
    text: StringResource,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .clip(CircleShape)
                .background(background_navigation_color)
                .padding(mediumPadding),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(icon),
                tint = if (isSelected) purple else navigation_color,
                contentDescription = "Icon"
            )
        }
        Text(
            text = stringResource(text),
            color = if (isSelected) purple else navigation_color,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal

        )

    }

}