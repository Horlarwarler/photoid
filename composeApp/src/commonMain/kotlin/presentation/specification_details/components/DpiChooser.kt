package presentation.specification_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.okay
import photoid.composeapp.generated.resources.picture_resolution
import ui.light_black
import ui.light_grey
import ui.mediumPadding
import ui.purple
import ui.smallPadding
import ui.white

@Composable
fun DpiChooser(
    onDpiChoose: (Int) -> Unit,
    cancelDialog: () -> Unit,
    selectedIndex: Int
) {

    var currentSelectedIndex by remember {
        mutableStateOf(selectedIndex)
    }

    Dialog(
        onDismissRequest = cancelDialog,

        ) {

        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(mediumPadding),
            elevation = mediumPadding,
            backgroundColor = white,

            ) {
            Column(
                modifier = Modifier.padding(mediumPadding),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(mediumPadding)
            ) {
                Text(
                    text = stringResource(Res.string.picture_resolution),
                    fontSize = 14.sp,
                    color = light_black,
                )
                Dpi(
                    value = "300",
                    isCurrent = currentSelectedIndex == 0,
                    onClick = {
                        currentSelectedIndex = 0
                    }
                )

                Dpi(
                    value = "350",
                    isCurrent = currentSelectedIndex == 1,
                    onClick = {
                        currentSelectedIndex = 1
                    }
                )

                Dpi(
                    value = "450",
                    isCurrent = currentSelectedIndex == 2,
                    onClick = {
                        currentSelectedIndex = 2
                    }
                )

                Dpi(
                    value = "600",
                    isCurrent = currentSelectedIndex == 3,
                    onClick = {
                        currentSelectedIndex = 3
                    }
                )

                Dpi(
                    value = "HD",
                    isCurrent = currentSelectedIndex == 4,
                    onClick = {
                        currentSelectedIndex = 4
                    }
                )
                Spacer(Modifier.height(mediumPadding))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(smallPadding))
                            .background(purple)
                            .padding(7.dp)
                            .clickable {
                                onDpiChoose(currentSelectedIndex)
                            },
                        text = stringResource(Res.string.okay),
                        color = white,
                        fontSize = 14.sp
                    )
                }
            }


        }
    }
}

@Composable
private fun Dpi(
    value: String,
    isCurrent: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.spacedBy(smallPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = if (isCurrent) purple else light_grey,
                    shape = CircleShape
                )
                .background(Color.Transparent)
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {

            if (isCurrent) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(purple)
                        .size(15.dp)
                )
            }
        }

        Text(
            text = value,
            fontSize = 14.sp,
            color = if (isCurrent) purple else light_grey
        )
    }
}