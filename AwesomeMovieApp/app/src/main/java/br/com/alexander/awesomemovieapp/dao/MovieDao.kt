package br.com.alexander.awesomemovieapp.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.alexander.awesomemovieapp.data.Movie

@Dao
interface MovieDao : BaseDao<Movie> {

    @Query("SELECT * FROM Movie")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): Movie?

}