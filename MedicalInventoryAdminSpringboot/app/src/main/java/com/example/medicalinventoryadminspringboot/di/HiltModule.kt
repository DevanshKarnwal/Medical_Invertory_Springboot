package com.example.medicalinventoryadminspringboot.di

import com.example.medicalinventoryadminspringboot.network.ApiProvider
import com.example.medicalinventoryadminspringboot.network.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun provideApiServices(): ApiServices {
        return ApiProvider.provideApi;
    }
}