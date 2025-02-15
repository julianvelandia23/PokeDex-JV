package com.julianvelandia.data.di

import com.julianvelandia.data.remote.PokeDexApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApisModule {

    @Provides
    @Singleton
    fun providePokeDexApi(
        retrofit: Retrofit
    ): PokeDexApi = retrofit.create(PokeDexApi::class.java)
}