package com.julianvelandia.data.local

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import androidx.room.TypeConverter
import com.julianvelandia.data.local.detail.AbilityEntity
import com.julianvelandia.data.local.detail.FormEntity
import com.julianvelandia.data.local.detail.TypeEntity
import com.squareup.moshi.Types

class Converters {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun fromAbilityList(value: List<AbilityEntity>?): String? {
        val adapter = moshi.adapter<List<AbilityEntity>>(Types.newParameterizedType(List::class.java, AbilityEntity::class.java))
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toAbilityList(value: String?): List<AbilityEntity> {
        val adapter = moshi.adapter<List<AbilityEntity>>(Types.newParameterizedType(List::class.java, AbilityEntity::class.java))
        return value?.let { adapter.fromJson(it) } ?: emptyList()
    }

    @TypeConverter
    fun fromFormList(value: List<FormEntity>?): String? {
        val adapter = moshi.adapter<List<FormEntity>>(Types.newParameterizedType(List::class.java, FormEntity::class.java))
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toFormList(value: String?): List<FormEntity> {
        val adapter = moshi.adapter<List<FormEntity>>(Types.newParameterizedType(List::class.java, FormEntity::class.java))
        return value?.let { adapter.fromJson(it) } ?: emptyList()
    }

    @TypeConverter
    fun fromTypeList(value: List<TypeEntity>?): String? {
        val adapter = moshi.adapter<List<TypeEntity>>(Types.newParameterizedType(List::class.java, TypeEntity::class.java))
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toTypeList(value: String?): List<TypeEntity> {
        val adapter = moshi.adapter<List<TypeEntity>>(Types.newParameterizedType(List::class.java, TypeEntity::class.java))
        return value?.let { adapter.fromJson(it) } ?: emptyList()
    }
}
