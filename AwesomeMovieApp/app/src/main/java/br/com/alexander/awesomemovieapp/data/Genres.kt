package br.com.alexander.awesomemovieapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genres(
    val id: Int = 0,
    val name: String
)
