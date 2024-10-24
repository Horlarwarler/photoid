import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import di.appModules
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import util.initLogger

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {

    KoinApplication(

        application = {
            modules(appModules())
        }
    ) {

        initLogger()

        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }
        MaterialTheme {
            Navigation()

        }
    }

}


fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()