package br.com.alexander.awesomemovieapp.datasource

import br.com.alexander.awesomemovieapp.api.MovieService
import br.com.alexander.awesomemovieapp.data.ApiCredentials
import br.com.alexander.awesomemovieapp.data.Movie
import br.com.alexander.awesomemovieapp.data.MovieDetails
import br.com.alexander.awesomemovieapp.data.Poster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApiClientDataSource : MovieDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials().baseUrl)
        .client(ApiCredentials().okHttpClient().build())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val movieService = retrofit.create(MovieService::class.java)

    override suspend fun getMovieData(): Result<List<Movie>?> =
        withContext(Dispatchers.IO) {
            val response = movieService.getPopularMovies()

            when {
                response.isSuccessful -> Result.success(response.body()?.results)
                else -> Result.failure(Throwable(response.message()))
            }

        }

    suspend fun getMovieDetailsData(movieId: Int): Result<MovieDetails?> =
        withContext(Dispatchers.IO) {
            val response = movieService.getMovieDetails(movieId)

            when {
                response.isSuccessful -> Result.success(response.body())
                else -> Result.failure(Throwable(response.message()))
            }
        }


    suspend fun getMoviePostersData(movieId: Int): Result<List<Poster>?> =
        withContext(Dispatchers.IO) {
            val response = movieService.getMoviePosters(movieId)

            when {
                response.isSuccessful -> Result.success(response.body()?.posters)
                else -> Result.failure(Throwable(response.message()))
            }
        }


    override suspend fun saveMovieData(list: List<Movie>) {
        // Not needed for this data source
    }

    override suspend fun clearMovieData() {
        // Not needed for this data source
    }
}