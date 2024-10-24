package data

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import util.AppContext


actual fun createSettings(): Settings {
    val context = AppContext.getContext()
    val preferencesManager = context.getSharedPreferences("PhotoIdSettings", Context.MODE_PRIVATE)
    //val preferencesManager = context.getAppContext().getSharedPreferences(context.)
    return SharedPreferencesSettings(preferencesManager)
}