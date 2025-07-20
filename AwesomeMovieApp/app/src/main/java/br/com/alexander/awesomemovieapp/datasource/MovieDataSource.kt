package br.com.alexander.awesomemovieapp.datasource

import br.com.alexander.awesomemovieapp.data.Movie

interface MovieDataSource {
    suspend fun getMovieData(): Result<List<Movie>?>
    suspend fun saveMovieData(list: List<Movie>)
    suspend fun clearMovieData()
}