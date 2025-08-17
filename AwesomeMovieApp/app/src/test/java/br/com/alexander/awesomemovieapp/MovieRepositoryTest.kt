package br.com.alexander.awesomemovieapp

import br.com.alexander.awesomemovieapp.data.Movie
import br.com.alexander.awesomemovieapp.data.MovieDetails
import br.com.alexander.awesomemovieapp.data.Poster
import br.com.alexander.awesomemovieapp.datasource.MovieApiClientDataSource
import br.com.alexander.awesomemovieapp.datasource.MovieDatabaseDataSource
import br.com.alexander.awesomemovieapp.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieRepositoryTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    val movieApiClientDataSource: MovieApiClientDataSource = mockk()
    val movieDatabaseDataSource: MovieDatabaseDataSource = mockk()

    val movieRepository = MovieRepository(movieApiClientDataSource, movieDatabaseDataSource)
    val movieList = listOf(Movie(1, "Movie 1", "Description 1", "imagePath1"))
    private val movieId = 123
    private val movieDetails = MovieDetails(
        id = movieId,
        genres = emptyList(),
        title = "Movie Title",
        description = "An overview of the movie.",
        backdropPath = "/path.jpg",
        rating = 8.5
    )
    private val postersList = listOf(Poster("/path.jpg"))

    @Before
    fun setup() {
        coEvery { movieDatabaseDataSource.clearMovieData() } returns Unit
        coEvery { movieDatabaseDataSource.saveMovieData(any()) } returns Unit
    }

    @Test
    fun getMovieData_whenApiSourceHadSuccess_shouldPersistDataAndReturnList() = runTest {

        val apiResponse = Result.success(movieList)
        coEvery { movieApiClientDataSource.getMovieData() } returns apiResponse

        val result = movieRepository.getMovieData()

        assertThat(result).isEqualTo(apiResponse)
        coVerifySequence {
            movieApiClientDataSource.getMovieData()
            movieDatabaseDataSource.clearMovieData()
            movieDatabaseDataSource.saveMovieData(movieList)
        }

    }

    @Test
    fun getMovieData_whenApiSourceHadFailure_shouldLoadLocalData() = runTest {

        val apiResponse = Result.failure<List<Movie>>(Throwable("Error"))
        val dbResponse = Result.success(movieList)

        coEvery { movieApiClientDataSource.getMovieData() } returns apiResponse
        coEvery { movieDatabaseDataSource.getMovieData() } returns dbResponse

        val result = movieRepository.getMovieData()

        coVerify(exactly = 0) {
            movieDatabaseDataSource.clearMovieData()
            movieDatabaseDataSource.saveMovieData(any())
        }
        coVerify(exactly = 1) {
            movieDatabaseDataSource.getMovieData()
        }

        assertThat(result).isEqualTo(dbResponse)
    }

    @Test
    fun getMovieData_whenApiSourceThrowsException_shouldReturnLocalData() = runTest {

        val dbResponse = Result.success(movieList)

        coEvery { movieApiClientDataSource.getMovieData() } throws Exception("Error")
        coEvery { movieDatabaseDataSource.getMovieData() } returns dbResponse

        val result = movieRepository.getMovieData()

        coVerify(exactly = 0) {
            movieDatabaseDataSource.clearMovieData()
            movieDatabaseDataSource.saveMovieData(any())
        }
        coVerify(exactly = 1) {
            movieDatabaseDataSource.getMovieData()
        }

        assertThat(result).isEqualTo(dbResponse)
    }

    @Test
    fun getMovieDetailsData_whenApiSourceHadSuccess_shouldReturnList() = runTest {

        val apiResponse = Result.success(movieDetails)
        coEvery { movieApiClientDataSource.getMovieDetailsData(movieId) } returns apiResponse

        val result = movieRepository.getMovieDetailsData(movieId)

        coVerify(exactly = 1) {
            movieApiClientDataSource.getMovieDetailsData(movieId)
        }

        assertThat(result).isEqualTo(apiResponse)
    }

    @Test
    fun getMoviePostersData_whenApiSourceHadSuccess_shouldReturnList() = runTest {

        val apiResponse = Result.success(postersList)
        coEvery { movieApiClientDataSource.getMoviePostersData(movieId) } returns apiResponse

        val result = movieRepository.getMoviePostersData(movieId)

        coVerify(exactly = 1) {
            movieApiClientDataSource.getMoviePostersData(movieId)
        }

        assertThat(result).isEqualTo(apiResponse)
    }


}