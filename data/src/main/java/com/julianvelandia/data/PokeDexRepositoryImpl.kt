package com.julianvelandia.data

import com.julianvelandia.data.local.LocalDataStorage
import com.julianvelandia.data.local.detail.DetailPokemonEntity
import com.julianvelandia.data.local.detail.toDomain
import com.julianvelandia.data.local.detail.toEntity
import com.julianvelandia.data.local.home.toDomain
import com.julianvelandia.data.local.home.toEntity
import com.julianvelandia.data.remote.RemoteDataStorage
import com.julianvelandia.data.remote.toDomain
import com.julianvelandia.domain.PokeDexRepository
import com.julianvelandia.domain.Pokemon
import com.julianvelandia.domain.PokemonDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

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
                    localDataStorage.upsertHome(it.toEntity())
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

    override fun getPokemonDetail(name: String): Flow<Result<PokemonDetail?>> {
        return localDataStorage.getPokemonDetail(name)
            .map { localData ->
               Result.success(localData?.toDomain())
            }
            .catch { e ->
                emit(Result.failure(e))
            }
            .onStart {
                runCatching {
                    val response = remoteDataStorage.getPokemonDetail(name)
                    if (response.isSuccessful) {
                        response.body()?.let { dto ->
                            localDataStorage.upsertDetail(dto.toEntity())
                        }
                    }
                }.onFailure { e ->
                    emit(Result.failure(e))
                }
            }
    }

}