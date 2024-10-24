package presentation.editor.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.ai
import photoid.composeapp.generated.resources.baseline_tag_faces_24
import ui.purple
import ui.smallPadding
import ui.white

@Composable
fun Beauty(
    modifier: Modifier,
    onClick: (Boolean) -> Unit,
    isSelected: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ApplyNone(
            modifier = Modifier.size(height = 100.dp, width = 100.dp),
            onClick = {
                onClick(false)
            },
            isSelected = !isSelected
        )
        ApplyAi(
            isSelected = isSelected,
            onClick = {
                onClick(true)
            }
        )


    }
}

@Composable
fun ApplyAi(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(end = smallPadding)
            .clickable {
                onClick()
            }
            .border(
                width = 1.dp,
                color = if (isSelected) white else Color.Transparent,
                shape = RoundedCornerShape(15.dp)
            )
            .size(height = 100.dp, width = 100.dp)
            .clip(RoundedCornerShape(15.dp))
            .alpha(if (isSelected) 1f else 0.8f)
            .background(purple),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(Res.drawable.baseline_tag_faces_24),
                tint = white,
                contentDescription = null
            )
            Text(
                text = stringResource(Res.string.ai),
                fontSize = 14.sp,
                color = white
            )

        }

    }
}