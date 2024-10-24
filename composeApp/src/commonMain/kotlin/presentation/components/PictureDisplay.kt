package presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import domain.model.Specification
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.down_arror
import photoid.composeapp.generated.resources.left_arrow
import photoid.composeapp.generated.resources.line
import photoid.composeapp.generated.resources.man
import photoid.composeapp.generated.resources.right_arrow
import photoid.composeapp.generated.resources.up_arrow
import ui.smallPadding


@Composable
fun PictureDisplay(
    modifier: Modifier = Modifier,
    specification: Specification,
    dpi: Int,
    imageUrl: Any? = null,
    imageBitmap: ImageBitmap? = null,
    color: Color = Color.Transparent
) {
    val heightInMM = "${specification.height}mm"
    val widthInMM = "${specification.width}mm"
    val heightInPx by remember(dpi) {
        mutableStateOf("${specification.toPx(dpi).second}px")
    }
    val widthInPx by remember(dpi) {
        mutableStateOf("${specification.toPx(dpi).first}px")
    }

    ConstraintLayout(
        modifier = modifier
    ) {
        val padding = (-10).dp

        val (image, topArrow, bottomArrow, leftArrow, rightArrow) = createRefs()

        Box(
            modifier = Modifier

                .constrainAs(image) {
                    start.linkTo(leftArrow.end)
                    end.linkTo(rightArrow.start)
                    top.linkTo(topArrow.bottom)
                    bottom.linkTo(bottomArrow.top)
                }
                .padding(
                    15.dp
                )
                .background(color)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl == null && imageBitmap == null) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Res.drawable.man),
                    contentDescription = null,
                    contentScale = ContentScale.Fit

                )

            } else if (imageBitmap != null) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    bitmap = imageBitmap,

                    contentScale = ContentScale.FillBounds,
                    contentDescription = "Image"
                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    placeholder = painterResource(Res.drawable.man),
                    model = imageUrl,
                    contentScale = ContentScale.Fit,
                    contentDescription = "Image"
                )
            }
        }



        ArrowHorizontal(
            modifier = Modifier
                .constrainAs(topArrow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = widthInPx
        )

        ArrowHorizontal(
            modifier = Modifier
                .constrainAs(bottomArrow) {
                    bottom.linkTo(parent.bottom, (-10).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = widthInMM
        )

        ArrowVertical(
            modifier = Modifier
                .constrainAs(leftArrow) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, (-10).dp)
                },
            text = heightInMM
        )
        ArrowVertical(
            modifier = Modifier.constrainAs(rightArrow) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end, (-10).dp)
            },
            text = heightInPx
        )

//        ArrowHorizontal(
//            modifier = Modifier.constrainAs(bottomArrow) {
//                bottom.linkTo(image.bottom, padding)
//                start.linkTo(image.start, padding)
//                end.linkTo(image.end, padding)
//            },
//            text = widthInMM
//        )
//
//        BoxWithConstraints(
//            modifier = modifier
//        ) {
//            val height = 200.dp
//            val width = 150.dp
//
//            if (imageUrl == null && imageBitmap == null) {
////                Image(
////                    modifier = Modifier
////                        .size(height = 210.dp, width = 260.dp)
////                        .align(Alignment.Center),
////                    painter = painterResource(Res.drawable.man),
////                    contentDescription = null,
////                    contentScale = ContentScale.Fit
////
////                )
//            } else if (imageBitmap != null) {
//                Image(
//                    modifier = Modifier
//                        .size(height = 210.dp, width = 260.dp)
//                        .align(Alignment.Center),
//                    bitmap = imageBitmap,
//
//                    contentScale = ContentScale.Fit,
//                    contentDescription = "Image"
//                )
//            } else {
//                AsyncImage(
//                    modifier = Modifier
//                        .size(
//                            width, height
//                        )
//                        .fillMaxSize()
//                        .align(Alignment.Center),
//                    placeholder = painterResource(Res.drawable.man),
//                    model = imageUrl,
//                    contentScale = ContentScale.Crop,
//                    contentDescription = "Image"
//                )
//            }
//

//


    }

}

@Composable
private fun ArrowVertical(
    modifier: Modifier = Modifier,
    text: String,
) {
    val smallOffset = 4.dp

    Column(
        modifier = modifier.padding(
            vertical = 10.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {

        Image(
            modifier = Modifier
                .size(10.dp)
                .offset(y = smallOffset),
            painter = painterResource(Res.drawable.line),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            //    .height((length / 2) - 16.dp),
            painter = painterResource(Res.drawable.up_arrow),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(
                    vertical = 10.dp
                )
                .rotate(270f),
            text = text,
            fontSize = 8.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 10.sp

        )
        Image(
            modifier = Modifier

                .weight(1f)
                .fillMaxHeight(),
            painter = painterResource(Res.drawable.down_arror),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .size(10.dp)
                .offset(y = -(smallOffset)),

            painter = painterResource(Res.drawable.line),
            contentDescription = null
        )


    }
}

@Composable
private fun ArrowHorizontal(
    modifier: Modifier = Modifier,
    length: Dp = 180.dp,
    text: String,
) {
    4.dp

    Row(
        modifier = modifier.padding(
            horizontal = smallPadding
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier
                .size(10.dp)
                .offset(x = 5.dp)
                .rotate(90f),
            painter = painterResource(Res.drawable.line),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            painter = painterResource(Res.drawable.left_arrow),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(
                    horizontal = 10.dp
                ),
            text = text,
            fontSize = 8.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 10.sp

        )
        Image(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            painter = painterResource(Res.drawable.right_arrow),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .size(10.dp)
                .offset(x = (-4).dp)

                .rotate(90f),

            painter = painterResource(Res.drawable.line),
            contentDescription = null,
        )


    }
}



