package com.iram.thegalleryapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CustomGallery : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}