package br.com.alexander.awesomemovieapp.data

import android.content.Context
import br.com.alexander.awesomemovieapp.R
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.DecimalFormat

@JsonClass(generateAdapter = true)
data class MovieDetails(
    val id: Int = 0,
    val genres: List<Genres>,
    val title: String?,
    @Json(name = "overview")
    val description: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "vote_average")
    val rating: Double = 0.0
) {
    companion object {
        const val IMAGEBASEURL = "https://image.tmdb.org/t/p/w500"
    }

    fun getTitleString(context: Context): String {
        return title ?: context.getString(R.string.title_not_available)
    }

    fun getImageUrl(): String {
        return "$IMAGEBASEURL$backdropPath"
    }

    fun getDescription(context: Context): String {
        return description ?: context.getString(R.string.desc_not_available)
    }

    fun getGenresString(): String {

        val limitedGenres = genres.take(5)

        return if (limitedGenres.isEmpty()) {
            ""
        } else {
            limitedGenres.joinToString(separator = " • ") {
                it.name
            }
        }
    }

    fun getRatingString(): String {
        val decimalFormat = DecimalFormat("#.#")
        return "★ ${decimalFormat.format(rating)}"
    }
}
