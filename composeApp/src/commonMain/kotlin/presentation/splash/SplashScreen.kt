package presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import photoid.composeapp.generated.resources.Res
import photoid.composeapp.generated.resources.app_description
import photoid.composeapp.generated.resources.bottom_splash
import photoid.composeapp.generated.resources.top_splash
import ui.background
import ui.black
import ui.largePadding
import ui.mediumPadding
import ui.white
import util.GoogleMlkit


@Composable
fun SplashScreen(
    isConnected: Boolean,
    navigateFromSplash: () -> Unit = {},

    ) {
    var delayCompleted by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {

        GoogleMlkit.initMlKit()
        delay(3000)
        navigateFromSplash()
        return@LaunchedEffect
        TODO()
        if (isConnected) {
            println("INTERNET AVAILABLE AFTER DELALY")
            navigateFromSplash()
            return@LaunchedEffect
        }
        println("INTERNET NOT INTERNET AVAILABLE AFTER DELALY")

        delayCompleted = true
    }
    LaunchedEffect(key1 = isConnected) {

        if (delayCompleted && isConnected) {
            println("INTERNET AVAILABLE AFTER LATER")

            navigateFromSplash()

        }
    }
    Box(
        modifier = Modifier
            .background(color = background)
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 20.dp),
            painter = painterResource(Res.drawable.top_splash),
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(
                    RoundedCornerShape(
                        topStart = mediumPadding,
                        topEnd = mediumPadding
                    )
                )
                .background(color = white)

                .padding(top = largePadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 55.dp, end = 55.dp),
                text = stringResource(Res.string.app_description),
                fontSize = 20.sp,
                color = black,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
//            Text(
//                modifier = Modifier
//                    .padding(start = 55.dp, end = 55.dp)
//                ,
//                text = "UUID $UUID",
//                fontSize = 40.sp,
//                color = black,
//                fontWeight = FontWeight.SemiBold,
//                textAlign = TextAlign.Center
//            )
            Spacer(modifier = Modifier.height(mediumPadding))
            Image(
                painter = painterResource(Res.drawable.bottom_splash),
                contentDescription = null,

                )
        }

    }
}

