package br.com.alexander.awesomemovieapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Poster(
    @Json(name = "file_path")
    val imagePath: String
) {
    companion object {
        const val IMAGEBASEURL = "https://image.tmdb.org/t/p/w200"
    }

    fun getImageUrl(): String {
        return "${IMAGEBASEURL}$imagePath"
    }

}

