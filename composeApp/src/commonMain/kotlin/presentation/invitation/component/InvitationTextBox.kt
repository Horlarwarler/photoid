package presentation.invitation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.arrow_left
import photoid.composeapp.generated.resources.enter_invitation_code
import photoid.composeapp.generated.resources.next
import photoid.composeapp.generated.resources.right_arrow
import photoid.composeapp.generated.resources.search
import photoid.composeapp.generated.resources.search_description
import ui.black
import ui.light_black
import ui.light_grey
import ui.purple
import ui.red
import ui.smallPadding
import ui.white

@Composable
fun InvitationTextBox(
    modifier: Modifier = Modifier,
    invitationCode: String,
    onInvitationTextChange: (String) -> Unit,
    onDoneClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier
            // .clip(RoundedCornerShape(5.dp))
            .border(1.dp, purple, RoundedCornerShape(5.dp))
            .background(white)
            .padding(start = 5.dp, top = 5.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(Res.string.enter_invitation_code),
                color = light_grey,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,

                )
            BasicTextField(
                value = invitationCode,
                onValueChange = onInvitationTextChange,
                onTextLayout = { it ->

                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = light_black,
                    fontWeight = FontWeight.SemiBold
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onDoneClick()
                    }
                )

            )

        }

        Icon(
            modifier = modifier
                .clickable {
                    onDoneClick()
                }
                .clip(CircleShape)
                .background(purple)
                .padding(horizontal = 15.dp, vertical = 10.dp),
            painter = painterResource(Res.drawable.next),
            tint = white,
            contentDescription = null,
        )
    }


}