package br.com.alexander.awesomemovieapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviePosters(
    val posters: List<Poster>
)
