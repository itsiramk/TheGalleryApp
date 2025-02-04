package com.iram.thegalleryapp.di

import com.iram.thegalleryapp.data.MediaRepository
import com.iram.thegalleryapp.data.MediaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * AppModule for providing dependencies
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 3rd Feb 2025
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMediaRepository(): MediaRepository = MediaRepositoryImpl()
}