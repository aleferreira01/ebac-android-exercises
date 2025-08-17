package br.com.alexander.awesomemovieapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alexander.awesomemovieapp.dao.MovieDao
import br.com.alexander.awesomemovieapp.data.Movie

@Database(
    entities = [
        Movie::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}