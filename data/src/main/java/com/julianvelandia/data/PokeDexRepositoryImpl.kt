package com.julianvelandia.data

import com.julianvelandia.data.remote.RemoteDataStorage
import com.julianvelandia.data.remote.toDomain
import com.julianvelandia.domain.PokeDexRepository
import com.julianvelandia.domain.Pokemon
import com.julianvelandia.domain.PokemonDetail

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

    override suspend fun getPokemonDetail(name: String): Result<PokemonDetail> {
        return try {
            val response = remoteDataStorage.getPokemonDetail(name)
            if (response.isSuccessful) {
                val result = response.body()?.toDomain()
                result?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Mapping error: Response body is null"))
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}