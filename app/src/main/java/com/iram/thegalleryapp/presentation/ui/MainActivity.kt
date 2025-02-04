package com.iram.thegalleryapp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iram.thegalleryapp.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity for the app
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 3rd Feb 2025
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
