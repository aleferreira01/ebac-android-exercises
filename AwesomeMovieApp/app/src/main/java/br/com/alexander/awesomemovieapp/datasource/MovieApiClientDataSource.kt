package br.com.alexander.awesomemovieapp.datasource

import br.com.alexander.awesomemovieapp.api.MovieService
import br.com.alexander.awesomemovieapp.data.Movie
import br.com.alexander.awesomemovieapp.data.MovieDetails
import br.com.alexander.awesomemovieapp.data.Poster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieApiClientDataSource @Inject constructor() : MovieDataSource {

    @Inject lateinit var movieService: MovieService

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