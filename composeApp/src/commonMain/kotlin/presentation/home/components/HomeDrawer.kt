package presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.feedback
import photoid.composeapp.generated.resources.good_review
import photoid.composeapp.generated.resources.review
import photoid.composeapp.generated.resources.shooting_tip
import photoid.composeapp.generated.resources.support
import ui.background
import ui.black
import ui.largePadding
import ui.mediumPadding
import ui.purple
import ui.white

@Composable
fun HomeDrawer(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background)
            .padding(vertical = largePadding)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = largePadding),
            color = black,
            text = "ID PHOT0",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding)
        ) {
            HomeDrawerItem(
                icon = Res.drawable.shooting_tip,
                text = Res.string.shooting_tip
            )
            HomeDrawerItem(
                icon = Res.drawable.feedback,
                text = Res.string.feedback
            )
            HomeDrawerItem(
                icon = Res.drawable.review,
                text = Res.string.good_review
            )
            HomeDrawerItem(
                icon = Res.drawable.support,
                text = Res.string.support
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = largePadding),
            color = black,
            text = "Version 12",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun HomeDrawerItem(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    text: StringResource

) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            elevation = 10.dp,
            backgroundColor = purple,
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(
                modifier = Modifier.padding(10.dp),
                painter = painterResource(resource = icon),
                contentDescription = null,
                tint = white
            )
        }
        Spacer(modifier = Modifier.width(15.dp))

        Text(
            modifier = Modifier,
            color = black,
            text = stringResource(text),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

    }

}