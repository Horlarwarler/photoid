package presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.arrow_left
import photoid.composeapp.generated.resources.camera_guide
import photoid.composeapp.generated.resources.cancel
import photoid.composeapp.generated.resources.guide_five
import photoid.composeapp.generated.resources.guide_four
import photoid.composeapp.generated.resources.guide_one
import photoid.composeapp.generated.resources.guide_six
import photoid.composeapp.generated.resources.guide_three
import photoid.composeapp.generated.resources.guide_two
import photoid.composeapp.generated.resources.photo_guide
import photoid.composeapp.generated.resources.question
import ui.black
import ui.light_black
import ui.mediumPadding
import ui.white

@Composable
fun Preview(
    modifier: Modifier = Modifier,
    showPlatform: Boolean = true,
    platformView: @Composable () -> Unit,
    onBackClick: () -> Unit,
    captureClick: () -> Unit,
    onOrientationClick: () -> Unit,
    onGalleryClick: () -> Unit
) {

    var showGuide by remember {
        mutableStateOf(false)
    }
    if (showGuide) {
        ShowGuide(
            onDismiss = {
                showGuide = false
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                showGuide
            ) {
                showGuide = false
            }
            .alpha(if (showGuide) 0.5f else 1f),
    ) {
        if (showPlatform) {
            platformView()
        }

        Icon(
            modifier = Modifier
                .padding(mediumPadding)
                .clickable {
                    onBackClick()
                }
                .align(Alignment.TopStart),
            painter = painterResource(resource = Res.drawable.arrow_left),
            contentDescription = null,
            tint = white
        )

        Icon(
            modifier = Modifier
                .padding(mediumPadding)
                .clickable {
                    showGuide = true
                }
                .align(Alignment.TopEnd),
            painter = painterResource(resource = Res.drawable.question),
            contentDescription = null,
            tint = white
        )

        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(resource = Res.drawable.camera_guide),
            contentDescription = null,
        )
        CameraAction(
            modifier = Modifier.align(Alignment.BottomCenter),
            onShutterClick = captureClick,
            changeOrientation = onOrientationClick,
            onGalleryClick = onGalleryClick
        )

    }
}

@Composable
private fun ShowGuide(
    onDismiss: () -> Unit,

    ) {
    val scrollState = rememberScrollState()


    Dialog(
        onDismissRequest = onDismiss,

        ) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(mediumPadding),
            elevation = mediumPadding,
            backgroundColor = white,

            ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(mediumPadding),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(mediumPadding)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.align(
                            Alignment.Center
                        ),
                        text = stringResource(Res.string.photo_guide),
                        fontSize = 14.sp,
                        color = black,
                    )
                    Icon(
                        modifier = Modifier
                            .clickable {
                                onDismiss()
                            }
                            .align(Alignment.CenterEnd),
                        painter = painterResource(Res.drawable.cancel),
                        contentDescription = null,
                        tint = black
                    )

                }

                Guide(
                    firstImage = Res.drawable.guide_one,
                    secondImage = Res.drawable.guide_two,
                    res = Res.string.guide_one
                )

                Guide(
                    firstImage = Res.drawable.guide_three,
                    secondImage = Res.drawable.guide_four,
                    res = Res.string.guide_two
                )

                Guide(
                    firstImage = Res.drawable.guide_five,
                    secondImage = Res.drawable.guide_six,
                    res = Res.string.guide_three
                )


            }


        }
    }
}

@Composable
private fun Guide(
    modifier: Modifier = Modifier,
    firstImage: DrawableResource,
    secondImage: DrawableResource,
    res: StringResource
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier.height(
                    height = 100.dp,

                    ),
                painter = painterResource(resource = firstImage),
                contentDescription = null,
            )
            Image(
                modifier = Modifier.height(height = 100.dp),
                painter = painterResource(resource = secondImage),
                contentDescription = null,
            )
        }
        Text(
            text = stringResource(res),
            color = light_black,
            fontSize = 12.sp,

            )

    }
}
