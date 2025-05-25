package br.com.alexander.awesomemovieapp.api

import br.com.alexander.awesomemovieapp.data.MovieDetails
import br.com.alexander.awesomemovieapp.data.MoviePosters
import br.com.alexander.awesomemovieapp.data.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Response<MovieDetails>

    @GET("movie/{movie_id}/images")
    suspend fun getMoviePosters(
        @Path("movie_id") movieId: Int,
        @Query("include_image_language") language: String = "en"
    ): Response<MoviePosters>

}