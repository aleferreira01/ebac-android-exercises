package br.com.alexander.awesomemovieapp.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.com.alexander.awesomemovieapp.data.Movie

@Dao
interface MovieDao : BaseDao<Movie> {

    @Transaction
    @Query("SELECT * FROM Movie")
    suspend fun getAllMovies(): List<Movie>

    @Transaction
    @Query("SELECT * FROM Movie WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): Movie?

    @Transaction
    @Query("DELETE FROM Movie")
    suspend fun deleteAll()

}