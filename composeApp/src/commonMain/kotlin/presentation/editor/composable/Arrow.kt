package presentation.editor.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.man


@Composable
fun ImageBoxWithArrows(
    imageUrl: String,
    widthPx: Int,
    heightPx: Int,
    widthMm: Int,
    heightMm: Int,
    modifier: Modifier = Modifier
) {
    // State to track if the image has been loaded
    //var isImageLoaded by remember { mutableStateOf(false) }
    val scaleFactorPx = 0.4f
    val scaleFactorMm = 2f


    // Calculate arrow lengths based on specifications
    val arrowLengthPx = (100).dp
    val arrowLengthMm = (widthMm * scaleFactorMm).dp


    ConstraintLayout(
        modifier = modifier
        // .aspectRatio(widthPx.toFloat() / heightPx)
    ) {
        // Create references for the elements
        val (image, topArrow, bottomArrow, startArrow, endArrow) = createRefs()

        // Image placeholder or actual image

        AsyncImage(
            modifier = Modifier
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .background(Color.Gray)
                .fillMaxHeight(),
            model = imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = "",
            placeholder = painterResource(Res.drawable.man)

        )

        // Top arrow and text
        ArrowWithText(
            text = "$widthPx px",
            orientation = ArrowOrientation.Horizontal,
            arrowLength = arrowLengthPx,
            modifier = Modifier
                .constrainAs(topArrow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )

        // Bottom arrow and text
        ArrowWithText(
            text = "$widthMm mm",
            orientation = ArrowOrientation.Horizontal,
            arrowLength = arrowLengthPx,
            modifier = Modifier
                .constrainAs(bottomArrow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )

        // Start (left) arrow and text
        ArrowWithText(
            arrowLength = arrowLengthPx,
            text = "$heightMm mm",
            orientation = ArrowOrientation.Vertical,
            modifier = Modifier
                .constrainAs(startArrow) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        // End (right) arrow and text
        ArrowWithText(
            arrowLength = arrowLengthPx,
            text = "$heightPx px",
            orientation = ArrowOrientation.Vertical,
            modifier = Modifier
                .constrainAs(endArrow) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Composable
fun ArrowWithText(
    text: String, orientation: ArrowOrientation,
    arrowLength: Dp,

    modifier: Modifier = Modifier
) {
    // This composable can be a Row or Column depending on the orientation
    when (orientation) {
        ArrowOrientation.Horizontal -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Arrow(Modifier.width(arrowLength))
                Text(text = text, modifier = Modifier.padding(horizontal = 2.dp))
                Arrow(Modifier.width(arrowLength), isReversed = true)
            }
        }

        ArrowOrientation.Vertical -> {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Arrow(Modifier.height(arrowLength))
                Text(text = text, modifier = Modifier.padding(vertical = 8.dp))
                Arrow(Modifier.height(arrowLength), isReversed = true)
            }
        }
    }
}

@Composable
fun Arrow(modifier: Modifier = Modifier, isReversed: Boolean = false) {
    // Draw a simple arrow using Canvas or use an ImageVector
    Canvas(modifier = modifier) {
        val startOffset = if (isReversed) {
            Offset(size.width, 0f)

        } else {
            Offset(0f, 0f)

        }

        val upArrowEndOffset =
            if (isReversed) Offset(size.width - 10f, -10f) else Offset(10f, -10f)


        val downArrowEndOffset =
            if (isReversed) Offset(size.width - 10f, 10f) else Offset(10f, 10f)


        // Draw arrow line
        drawLine(
            color = Color.Black,
            start = Offset.Zero,
            end = if (isReversed) Offset(size.width, size.height) else Offset(size.width, 0f),
            strokeWidth = 4f
        )
        // Draw arrowhead
        drawLine(
            color = Color.Black,
            start = startOffset,
            end = upArrowEndOffset,
            strokeWidth = 4f
        )
        drawLine(
            color = Color.Black,
            start = startOffset,
            end = downArrowEndOffset,
            strokeWidth = 4f
        )
    }
}

enum class ArrowOrientation {
    Horizontal,
    Vertical
}