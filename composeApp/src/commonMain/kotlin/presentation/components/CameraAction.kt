package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.baseline_image_24
import photoid.composeapp.generated.resources.reload
import photoid.composeapp.generated.resources.shutter
import ui.arch
import ui.largePadding
import ui.mediumPadding
import ui.white

@Composable
fun CameraAction(
    modifier: Modifier,
    onShutterClick: () -> Unit,
    changeOrientation: () -> Unit,
    onGalleryClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = mediumPadding,
                    topEnd = mediumPadding
                )
            )
            .background(arch)
    ) {

        Row(
            modifier = Modifier
                .padding(
                    horizontal = mediumPadding,
                    vertical = largePadding
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .clickable {
                        onGalleryClick()
                    },
                painter = painterResource(resource = Res.drawable.baseline_image_24),
                contentDescription = null,
                tint = white
            )

            Icon(
                modifier = Modifier
                    .clickable {
                        onShutterClick()
                    },
                painter = painterResource(resource = Res.drawable.shutter),
                contentDescription = null,
                tint = white
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        changeOrientation()
                    },
                painter = painterResource(resource = Res.drawable.reload),
                contentDescription = null,
                tint = white
            )
        }
    }
}
