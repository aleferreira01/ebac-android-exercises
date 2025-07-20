package br.com.alexander.awesomemovieapp.datasource

import android.content.Context
import br.com.alexander.awesomemovieapp.data.Movie
import br.com.alexander.awesomemovieapp.database.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDatabaseDataSource(context: Context) : MovieDataSource {

    private val movieDatabase = MovieDatabase.getDatabase(context)
    private val movieDao = movieDatabase.movieDao()

    override suspend fun getMovieData(): Result<List<Movie>?> =
        withContext(Dispatchers.IO) {
            Result.success(loadPersistedMovieData())
        }

    override suspend fun saveMovieData(list: List<Movie>) {
        movieDao.insertAll(list)
    }

    override suspend fun clearMovieData() {
        movieDao.deleteAll()
    }

    private suspend fun loadPersistedMovieData() = movieDao.getAllMovies()

}