package br.com.alexander.awesomemovieapp.repository

import android.content.Context
import br.com.alexander.awesomemovieapp.data.Movie
import br.com.alexander.awesomemovieapp.data.MovieDetails
import br.com.alexander.awesomemovieapp.data.Poster
import br.com.alexander.awesomemovieapp.datasource.MovieApiClientDataSource
import br.com.alexander.awesomemovieapp.datasource.MovieDatabaseDataSource

class MovieRepository(context: Context) {

    val movieApiClientDataSource = MovieApiClientDataSource()
    val movieDatabaseDataSource = MovieDatabaseDataSource(context)

    suspend fun getMovieData(): Result<List<Movie>?> {
        return try {
            val result = movieApiClientDataSource.getMovieData()
            if (result.isSuccess) {
                persistMovieData(result.getOrNull())
                result
            } else {
                getLocalData()
            }
        } catch (e: Exception) {
            getLocalData()
        }
    }

    suspend fun getMovieDetailsData(movieId: Int): Result<MovieDetails?> {
        return try {
            movieApiClientDataSource.getMovieDetailsData(movieId)
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }

    suspend fun getMoviePostersData(movieId: Int): Result<List<Poster>?> {
        return try {
            movieApiClientDataSource.getMoviePostersData(movieId)
        } catch (e: Exception) {
           Result.failure(Throwable(e.message))
        }
    }

    private suspend fun getLocalData() = movieDatabaseDataSource.getMovieData()

    private suspend fun persistMovieData(movieList: List<Movie>?) {
        movieDatabaseDataSource.clearMovieData()
        movieList?.let {
            movieDatabaseDataSource.saveMovieData(it)
        }
    }



}