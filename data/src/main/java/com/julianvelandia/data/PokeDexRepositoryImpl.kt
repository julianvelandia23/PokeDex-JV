package com.julianvelandia.data

import com.julianvelandia.data.local.LocalDataStorage
import com.julianvelandia.data.local.toDomain
import com.julianvelandia.data.local.toEntity
import com.julianvelandia.data.remote.RemoteDataStorage
import com.julianvelandia.data.remote.toDomain
import com.julianvelandia.domain.PokeDexRepository
import com.julianvelandia.domain.Pokemon
import com.julianvelandia.domain.PokemonDetail
import kotlinx.coroutines.flow.Flow

class PokeDexRepositoryImpl(
    private val remoteDataStorage: RemoteDataStorage,
    private val localDataStorage: LocalDataStorage
) : PokeDexRepository {

    override fun getListPokemon(): Flow<Result<List<Pokemon>>> =
        safeCallData(
            localCall = {
                localDataStorage.getAll().map { it.toDomain() }
            },
            remoteCall = {
                val response = remoteDataStorage.getListPokemon()
                val mappedList = response.body()?.results?.map { it.toDomain() }.orEmpty()
                Result.success(mappedList)
            },
            updateLocalData = { data ->
                data.forEach {
                    localDataStorage.upsert(it.toEntity())
                }
            }
        )


    override suspend fun searchPokemon(query: String): Result<List<Pokemon>> {
        return runCatching {
            localDataStorage.searchPokemon(query)
                .map { it.toDomain() }
        }.recoverCatching { _ ->
            emptyList()
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