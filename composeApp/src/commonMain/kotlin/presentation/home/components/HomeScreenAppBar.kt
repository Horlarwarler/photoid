package presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.home
import photoid.composeapp.generated.resources.menu
import photoid.composeapp.generated.resources.order
import ui.black
import ui.purple
import ui.red
import ui.smallPadding
import ui.white

@Composable
fun HomeScreenAppBar(
    modifier: Modifier = Modifier,
    onBuggerClick: () -> Unit,
    onInviteClick: () -> Unit,
    onOrderClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clickable {
                    onBuggerClick()
                },
            painter = painterResource(Res.drawable.menu),
            contentDescription = null,
            tint = black
        )

        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier

                .clickable {
                    onInviteClick()
                }
                .clip(CircleShape)
                .background(white)
                .padding(
                    smallPadding
                ),
            painter = painterResource(Res.drawable.home),
            tint = red,
            contentDescription = null,
        )
        Spacer(
            modifier = Modifier.width(smallPadding)
        )
        Icon(
            modifier = Modifier
                .clickable {
                    onOrderClick()
                }
                .clip(CircleShape)
                .background(white)
                .padding(
                    smallPadding
                ),
            painter = painterResource(Res.drawable.order),
            tint = purple,

            contentDescription = null,
        )
    }

}