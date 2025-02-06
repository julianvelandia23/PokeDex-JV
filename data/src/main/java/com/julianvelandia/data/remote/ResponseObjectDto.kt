package com.julianvelandia.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseListDto<T>(
    val results: List<T>? = emptyList(),
)