package presentation.specification_details.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import domain.model.ImageProcessState
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.baseline_keyboard_arrow_right_24
import photoid.composeapp.generated.resources.line
import photoid.composeapp.generated.resources.man
import ui.largePadding
import ui.light_black
import ui.light_grey
import ui.purple
import ui.smallPadding
import ui.white

@Composable
fun ImageProcessingBox(
    imageProcessState: ImageProcessState,
    reUpload: () -> Unit,
    dismissDialog: () -> Unit
) {
    if (imageProcessState == ImageProcessState.PROCESSED) {
        dismissDialog()
        return
    }

    val isLoading =
        imageProcessState == ImageProcessState.PROCESSING || imageProcessState == ImageProcessState.SELECTING_IMAGE
    val dialogDescription =
        if (isLoading) "Please wait while photos is processing" else "Operation failed, no face detected"
    Dialog(
        onDismissRequest = dismissDialog
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(smallPadding))
                .background(white)
                .padding(vertical = smallPadding, horizontal = smallPadding)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center

        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = largePadding, horizontal = smallPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,


                ) {
                //Processing Image
                ImageWithBox(
                    showDivider = isLoading
                )

                //Text Description
                Spacer(modifier = Modifier.height(smallPadding))
                Text(
                    text = dialogDescription,
                    fontSize = 14.sp,
                    color = light_black,
                    textAlign = TextAlign.Center
                )
                if (imageProcessState != ImageProcessState.ERROR) {
                    return@Column
                }
                Spacer(modifier = Modifier.height(smallPadding))

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = light_grey
                )
                Spacer(modifier = Modifier.height(smallPadding))
                Text(
                    modifier = Modifier.clickable {
                        reUpload()
                    },
                    text = "Re-upload",
                    fontSize = 14.sp,
                    color = light_black,
                    textAlign = TextAlign.Center
                )


                //Divider if face not found
                //Re Upload button
            }

        }
    }

}

@Composable
fun ImageWithBox(
    showDivider: Boolean
) {
    var isAnimating by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        isAnimating = true
    }
    val animateDp by animateDpAsState(
        targetValue = if (isAnimating) 33.dp else (-33).dp,
        animationSpec = tween(
            durationMillis = 1000,
        ),
        finishedListener = {
            if (showDivider) {
                isAnimating = !isAnimating
            }
        }

    )


    val drawableResource = Res.drawable.baseline_keyboard_arrow_right_24



    Box(
        modifier = Modifier.size(90.dp)
    ) {


        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .rotate(225f),
            painter = painterResource(drawableResource),
            contentDescription = "Decoration",
            tint = purple
        )
        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .rotate(-45f),
            painter = painterResource(drawableResource),
            contentDescription = "Decoration",
            tint = purple
        )
        Icon(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .rotate(135f),
            painter = painterResource(drawableResource),
            contentDescription = "Decoration",
            tint = purple
        )
        Icon(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .rotate(45f),
            painter = painterResource(drawableResource),
            contentDescription = "Decoration",
            tint = purple
        )
        Image(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.Center),
            painter = painterResource(Res.drawable.man),
            contentDescription = null,

            )
        if (showDivider) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = smallPadding)
                    .height(1.dp)
                    .offset(y = animateDp),
                painter = painterResource(Res.drawable.line),
                contentScale = ContentScale.FillWidth,
                contentDescription = "Divider"
            )
        }


    }
}