package presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.Specification
import domain.model.SpecificationType
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.cv_photo
import photoid.composeapp.generated.resources.id_card
import photoid.composeapp.generated.resources.next
import photoid.composeapp.generated.resources.passport
import photoid.composeapp.generated.resources.review
import ui.mediumPadding
import ui.size_background_four
import ui.size_background_one
import ui.size_background_three
import ui.size_background_two
import ui.smallPadding
import ui.white
import util.getDpi

@Composable
fun PhotoSpecificationItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    specification: Specification
) {

    val specificationIcon = when (specification.specificationType) {
        SpecificationType.VISA -> Res.drawable.id_card
        SpecificationType.PASSPORT -> Res.drawable.passport
        SpecificationType.CV_PHOTO -> Res.drawable.cv_photo
        SpecificationType.CUSTOM -> Res.drawable.passport
    }

    val specificationColor = when (specification.specificationType) {
        SpecificationType.PASSPORT -> size_background_one

        SpecificationType.VISA -> size_background_two
        SpecificationType.CV_PHOTO -> size_background_three
        SpecificationType.CUSTOM -> size_background_four
    }
    val dp = getDpi().toInt()

    val specificationDetails =
        "${specification.width} x ${specification.height}mm | ${specification.toPx(dp).first} x ${
            specification.toPx(dp).second
        }px"

    Card(
        modifier = modifier
            .padding(
                bottom = smallPadding
            )
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),
        elevation = 4.dp,
        shape = RoundedCornerShape(smallPadding),
        backgroundColor = white
    ) {
        Row(
            modifier = Modifier.padding(smallPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(end = smallPadding)
                    .clip(RoundedCornerShape(smallPadding))
                    .background(specificationColor)
                    .size(45.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(painter = painterResource(specificationIcon), contentDescription = null)
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = specification.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold


                )
                Text(
                    text = specificationDetails,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(Res.drawable.next),
                contentDescription = null
            )
        }
    }
}