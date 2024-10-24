package presentation.invitation

import CustomTextButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.invite_freind_bg
import photoid.composeapp.generated.resources.invite_friend
import photoid.composeapp.generated.resources.invite_friends
import photoid.composeapp.generated.resources.my_invitation_code
import photoid.composeapp.generated.resources.no_order
import photoid.composeapp.generated.resources.save_for_free
import photoid.composeapp.generated.resources.top_splash
import presentation.components.TextAppBar
import presentation.invitation.component.InvitationTextBox
import ui.background
import ui.largePadding
import ui.light_black
import ui.mediumPadding
import ui.purple
import ui.smallPadding
import ui.white

@Composable
fun InvitationScreen(
    onBackClick: () -> Unit,
    invitationScreenState: InvitationScreenState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white),
        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.SpaceBetween


    ) {
        TextAppBar(
            modifier = Modifier
                .background(background)
                .padding(
                    top = smallPadding,
                    start = mediumPadding,
                    end = mediumPadding,
                    bottom = smallPadding
                )
                .fillMaxWidth(),
            text = stringResource(Res.string.invite_friend),
            onBackClick = onBackClick
        )
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        bottomStart = mediumPadding,
                        bottomEnd = mediumPadding
                    )
                )
                .background(background)
                .weight(0.85f)
                .padding(bottom = mediumPadding),
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Image(
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth(),
                    painter = painterResource(Res.drawable.invite_freind_bg),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = mediumPadding),
                    text = stringResource(Res.string.invite_friends),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = purple
                )
                Spacer(modifier = Modifier.height(mediumPadding))
                val invitation = buildAnnotatedString {

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = light_black,

                            )
                    ) {
                        append(stringResource(Res.string.my_invitation_code))
                    }
                    append(" - ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = purple
                        )
                    ) {
                        append(invitationScreenState.myInvitationCode)
                    }
                }

                Text(
                    text = invitation,
                    textAlign = TextAlign.Center

                )
                Spacer(modifier = Modifier.height(smallPadding))
                InvitationTextBox(
                    modifier = Modifier.padding(horizontal = mediumPadding),
                    invitationCode = invitationScreenState.invitationCode,
                    onDoneClick = {

                    },
                    onInvitationTextChange = {

                    }
                )


            }
        }



        Box(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxSize()
        ) {
            CustomTextButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = mediumPadding, start = mediumPadding, end = mediumPadding)
                    .fillMaxWidth(),
                backgroundColor = purple,
                buttonColor = white,
                buttonText = stringResource(Res.string.save_for_free),
                onClick = {}
            )
        }


    }
}