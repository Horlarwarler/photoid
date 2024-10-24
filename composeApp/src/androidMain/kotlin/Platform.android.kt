import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

val name: String = "Android ${Build.VERSION.SDK_INT}"
actual fun getPlatform(): String = name