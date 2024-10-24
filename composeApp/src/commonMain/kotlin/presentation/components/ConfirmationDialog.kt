package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ui.largePadding
import ui.light_black
import ui.purple
import ui.white

@Composable
fun ConfirmationDialog(
    modifier: Modifier,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
    positiveButtonText: StringResource,
    negativeButtonText: StringResource,
    dialogTitle: StringResource,
    dialogDescription: StringResource,
    onDismissDialog: () -> Unit
) {

    AlertDialog(
        modifier = modifier,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(dialogTitle),
                fontSize = 16.sp,
                color = light_black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = largePadding,
                        end = largePadding,
                        bottom = largePadding
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.clickable { onNegativeButtonClick() },
                    text = stringResource(negativeButtonText),
                    color = light_black,

                    )
                Text(
                    modifier = Modifier
                        .clickable { onPositiveButtonClick() }
                        .clip(RoundedCornerShape(10.dp))
                        .background(purple)
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    text = stringResource(positiveButtonText),
                    color = white
                )

            }
        },
        text = {
            Text(
                text = stringResource(dialogDescription),
                fontSize = 14.sp,
                color = light_black,
                textAlign = TextAlign.Start
            )
        },
        onDismissRequest = onDismissDialog,
        backgroundColor = white
    )
}