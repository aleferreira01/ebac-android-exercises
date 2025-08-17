package br.com.alexander.awesomemovieapp.datasource

import br.com.alexander.awesomemovieapp.dao.MovieDao
import br.com.alexander.awesomemovieapp.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDatabaseDataSource @Inject constructor() : MovieDataSource {

    @Inject
    lateinit var movieDao: MovieDao

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