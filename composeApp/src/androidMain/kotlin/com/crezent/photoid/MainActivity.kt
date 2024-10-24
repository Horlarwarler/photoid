package com.crezent.photoid

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ui.purple
import ui.smallPadding


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }


    override fun onDestroy() {


        super.onDestroy()
    }
}

@Composable
fun ImageWithBox() {
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
            isAnimating = !isAnimating
        }

    )


    val drawableResource = R.drawable.baseline_keyboard_arrow_right_24



    Box(
        modifier = Modifier.size(90.dp)
    ) {


        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .rotate(225f),
            painter = painterResource(id = drawableResource),
            contentDescription = "Decoration",
            tint = purple
        )
        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .rotate(-45f),
            painter = painterResource(id = drawableResource),
            contentDescription = "Decoration",
            tint = purple
        )
        Icon(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .rotate(135f),
            painter = painterResource(id = drawableResource),
            contentDescription = "Decoration",
            tint = purple
        )
        Icon(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .rotate(45f),
            painter = painterResource(id = drawableResource),
            contentDescription = "Decoration",
            tint = purple
        )
        Image(
            modifier = Modifier
                .size(45.dp)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.baseline_person_3_24),
            contentDescription = null,

            )
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(start = 15.dp, end = smallPadding)
                .height(1.dp)
                .offset(y = animateDp),
            painter = painterResource(id = R.drawable.line),
            contentScale = ContentScale.FillWidth,
            // tint = purple,
            contentDescription = "Divider"
        )
//        Image(
//            modifier = Modifier
//                .size(35.dp)
//                .align(Alignment.Center),
//            painter = painterResource(Res.drawable.man),
//            contentDescription = "Man Picture",
//        )


    }
}

@Preview
@Composable
private fun PreviewX() {
    ImageWithBox()
}

