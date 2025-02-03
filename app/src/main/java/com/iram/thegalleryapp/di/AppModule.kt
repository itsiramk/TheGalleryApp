package com.iram.thegalleryapp.di

import com.iram.thegalleryapp.data.MediaRepository
import com.iram.thegalleryapp.data.MediaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMediaRepository(): MediaRepository = MediaRepositoryImpl()
}