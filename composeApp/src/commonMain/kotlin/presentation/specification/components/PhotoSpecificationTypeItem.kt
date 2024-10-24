package presentation.specification.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.SpecificationType
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.cv_photo
import photoid.composeapp.generated.resources.id_card
import photoid.composeapp.generated.resources.passport
import photoid.composeapp.generated.resources.review
import ui.black
import ui.largePadding
import ui.size_background_four
import ui.size_background_one
import ui.size_background_three
import ui.size_background_two
import ui.smallPadding

@Composable
fun PhotoSpecificationTypeItem(
    modifier: Modifier = Modifier,
    specificationType: SpecificationType,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val specificationIcon = when (specificationType) {
        SpecificationType.VISA -> Res.drawable.id_card
        SpecificationType.PASSPORT -> Res.drawable.passport
        SpecificationType.CV_PHOTO -> Res.drawable.cv_photo
        SpecificationType.CUSTOM -> Res.drawable.passport
    }

    val specificationColor = when (specificationType) {
        SpecificationType.PASSPORT -> size_background_one

        SpecificationType.VISA -> size_background_two
        SpecificationType.CV_PHOTO -> size_background_three
        SpecificationType.CUSTOM -> size_background_four
    }

    Box(
        modifier = Modifier
            .padding(end = smallPadding)


            .defaultMinSize(110.dp)
            .clickable {
                onClick()
            }
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Black else Color.Transparent,
                shape = RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp))
            .background(specificationColor),
    ) {

        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = smallPadding, top = smallPadding),
            text = specificationType.name,
            color = black,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold

        )
        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(top = largePadding + smallPadding)
                .size(80.dp),
            painter = painterResource(specificationIcon),
            contentDescription = null,
        )
    }

}