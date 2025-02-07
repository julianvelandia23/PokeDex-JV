package com.julianvelandia.data

import android.util.Log
import com.julianvelandia.data.local.LocalDataStorage
import com.julianvelandia.data.local.toDomain
import com.julianvelandia.data.local.toEntity
import com.julianvelandia.data.remote.RemoteDataStorage
import com.julianvelandia.data.remote.toDomain
import com.julianvelandia.domain.PokeDexRepository
import com.julianvelandia.domain.Pokemon
import com.julianvelandia.domain.PokemonDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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





    /* override fun getListPokemon(): Flow<Result<List<Pokemon>>> {
         return try {
             val response = remoteDataStorage.getListPokemon()
             if (response.isSuccessful) {
                 val result = response.body()?.results?.map {
                     localDataStorage.upsert(it.toEntity())
                     it.toDomain()
                 } ?: emptyList()

                 localDataStorage.getAll().collect {
                     it.forEach { homePokemon ->
                         Log.d("PokeDexRepositoryImpl", homePokemon.name.orEmpty())
                     }
                 }

                 Result.success(result)
             } else {
                 Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
             }
         } catch (e: Exception) {
             Result.failure(e)
         }
     } */

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