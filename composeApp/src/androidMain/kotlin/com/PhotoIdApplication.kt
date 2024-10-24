package com

import android.app.Application
import android.app.LocaleManager
import android.content.Context
import util.AppContext

class PhotoIdApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContext.setUpContext(applicationContext)
    }


}