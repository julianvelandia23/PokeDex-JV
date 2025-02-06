package com.julianvelandia.data.di

import com.julianvelandia.data.remote.PokeDexApi
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

class ApisModule {

    @Provides
    @Singleton
    fun providePokeDexApi(
        retrofit: Retrofit
    ): PokeDexApi = retrofit.create(PokeDexApi::class.java)
}