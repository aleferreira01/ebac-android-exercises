package br.com.alexander.awesomemovieapp.data

import android.content.Context
import br.com.alexander.awesomemovieapp.R
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int = 0,
    val title: String?,
    @Json(name = "overview")
    val description: String?,
    @Json(name = "poster_path")
    val imagePath: String?
) {

    companion object {
        const val IMAGEBASEURL = "https://image.tmdb.org/t/p/w500"
    }

    fun getTitleString(context: Context): String {
        if (title.isNullOrEmpty()) {
            return context.getString(R.string.title_not_available)
        }
        return title
    }

    fun getImageUrl(): String {
        return "$IMAGEBASEURL$imagePath"
    }

    fun getDescription(context: Context): String {
        if (description.isNullOrEmpty()) {
            return context.getString(R.string.desc_not_available)
        }
        return description
    }
}
