package presentation.specification_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.Specification
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.baseline_edit_square_24
import photoid.composeapp.generated.resources.next
import photoid.composeapp.generated.resources.none
import ui.light_black
import ui.light_grey
import ui.smallPadding
import ui.white

@Composable
fun SpecificationDetailsItem(
    value: String,
    bottomText: String,
    onEditClick: (() -> Unit)? = null
) {

    Card(
        modifier = Modifier
            .padding(
                bottom = smallPadding
            )
            .fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(smallPadding),
        backgroundColor = white
    ) {
        Row(
            modifier = Modifier.padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.Start
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(smallPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = value,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = light_black


                    )

                    if (onEditClick != null) {
                        Icon(
                            modifier = Modifier.clickable {
                                onEditClick()
                            },
                            painter = painterResource(Res.drawable.baseline_edit_square_24),
                            contentDescription = "EDit",
                            tint = light_grey,

                            )
                    }
                }
                Text(
                    text = bottomText,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = light_black
                )

            }

        }
    }
}