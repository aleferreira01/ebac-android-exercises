package br.com.alexander.awesomemovieapp.movieHome

import br.com.alexander.awesomemovieapp.data.MovieDetails
import br.com.alexander.awesomemovieapp.data.MoviePosters
import br.com.alexander.awesomemovieapp.data.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    fun getPopularMovies(): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int): Call<MovieDetails>

    @GET("movie/{movie_id}/images")
    fun getMoviePosters(
        @Path("movie_id") movieId: Int,
        @Query("include_image_language") language: String = "en"
    ): Call<MoviePosters>

}