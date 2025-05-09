package br.com.alexander.awesomemovieapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    val code: Int?,
    val status: String?,
    val results: List<Movie>
)
