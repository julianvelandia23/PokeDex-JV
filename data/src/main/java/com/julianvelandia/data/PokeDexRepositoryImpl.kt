package com.julianvelandia.data

import com.julianvelandia.data.remote.RemoteDataStorage
import com.julianvelandia.data.remote.toDomain
import com.julianvelandia.domain.PokeDexRepository
import com.julianvelandia.domain.Pokemon

class PokeDexRepositoryImpl(
    private val remoteDataStorage: RemoteDataStorage
) : PokeDexRepository {
    override suspend fun getListPokemon(): Result<List<Pokemon>> {
        return try {
            val response = remoteDataStorage.getListPokemon()
            if (response.isSuccessful) {
                val result = response.body()?.results?.map { it.toDomain() } ?: emptyList()
                Result.success(result)
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}