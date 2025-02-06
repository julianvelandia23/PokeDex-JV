package com.julianvelandia.data.di

import com.julianvelandia.data.PokeDexRepositoryImpl
import com.julianvelandia.data.remote.RemoteDataStorage
import com.julianvelandia.domain.PokeDexRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDogsRepository(
        remoteDataStorage: RemoteDataStorage,
    ): PokeDexRepository = PokeDexRepositoryImpl(remoteDataStorage)
}