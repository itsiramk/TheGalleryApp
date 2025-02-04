package com.iram.thegalleryapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * App entry point
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 3rd Feb 2025
 */

@HiltAndroidApp
class CustomGallery : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}