package br.com.alexander.awesomemovieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.alexander.awesomemovieapp.data.DataState
import br.com.alexander.awesomemovieapp.data.Movie
import br.com.alexander.awesomemovieapp.data.MovieDetails
import br.com.alexander.awesomemovieapp.data.Poster
import br.com.alexander.awesomemovieapp.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    val dispatcher = StandardTestDispatcher()

    val movieRepository: MovieRepository = mockk()
    lateinit var viewModel: MovieViewModel
    val dataStateObserver: Observer<DataState> = mockk(relaxed = true)
    val navigationObserver: Observer<MovieEvent<Unit>> = mockk(relaxed = true)
    val dataStateValues = mutableListOf<DataState>()
    val navigationValues = mutableListOf<MovieEvent<Unit>>()

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
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxed = true)

        justRun { dataStateObserver.onChanged(capture(dataStateValues)) }
        justRun { navigationObserver.onChanged(capture(navigationValues)) }

        coEvery { movieRepository.getMovieData() } returns Result.failure(Throwable("Test"))

        viewModel = MovieViewModel(movieRepository)
        viewModel.dataStateLiveData.observeForever(dataStateObserver)
        viewModel.navigationToMovieDetails.observeForever(navigationObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.dataStateLiveData.removeObserver(dataStateObserver)
        viewModel.navigationToMovieDetails.removeObserver(navigationObserver)
        dataStateValues.clear()
        navigationValues.clear()
    }

    // getMoviesData

    @Test
    fun getMoviesData_whenMovieRepository_hasData_shouldChangeStateToSuccess() = runTest {
        coEvery { movieRepository.getMovieData() } returns Result.success(movieList)
        dataStateValues.clear()

        viewModel.getMoviesData()
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(dataStateValues).isEqualTo(listOf(DataState.LOADING, DataState.SUCCESS))

    }

    @Test
    fun getMoviesData_whenMovieRepository_hasError_shouldChangeStateToError() = runTest {
        coEvery { movieRepository.getMovieData() } returns Result.failure(Throwable("Error"))
        dataStateValues.clear()

        viewModel.getMoviesData()
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(dataStateValues).isEqualTo(listOf(DataState.LOADING, DataState.ERROR))

    }

    @Test
    fun getMoviesData_whenMovieRepository_hasData_shouldEmitList() = runTest {
        val movieList = movieList
        coEvery { movieRepository.getMovieData() } returns Result.success(movieList)

        viewModel.getMoviesData()
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.movieListLiveData.value).isEqualTo(movieList)

    }

    @Test
    fun getMoviesData_whenMovieRepository_hasError_shouldNotEmitList() = runTest {
        coEvery { movieRepository.getMovieData() } returns Result.failure(Throwable("Error"))

        viewModel.getMoviesData()
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.movieListLiveData.value).isNull()

    }

    // getMovieDetailsData

    @Test
    fun getMovieDetailsData_whenMovieRepository_hasData_shouldEmitAMovieDetailsData() = runTest {
        val movieDetails = movieDetails
        coEvery { movieRepository.getMovieDetailsData(movieId) } returns Result.success(movieDetails)
        coEvery { movieRepository.getMoviePostersData(movieId) } returns Result.success(postersList)

        viewModel.getMovieDetailsData(movieId)
        dispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) {
            movieRepository.getMoviePostersData(movieId)
        }

        assertThat(viewModel.movieDetailsLiveData.value).isEqualTo(movieDetails)

    }

    @Test
    fun getMovieDetailsData_whenMovieRepository_hasError_shouldChangeStateToError() = runTest {
        coEvery { movieRepository.getMovieDetailsData(movieId) } returns Result.failure(Throwable("Error"))
        dataStateValues.clear()

        viewModel.getMovieDetailsData(movieId)
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(dataStateValues).isEqualTo(listOf(DataState.ERROR))

    }

    @Test
    fun getMovieDetailsData_whenMovieRepository_hasError_shouldNotEmitList() = runTest {
        coEvery { movieRepository.getMovieDetailsData(movieId) } returns Result.failure(Throwable("Error"))

        viewModel.getMovieDetailsData(movieId)
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.movieDetailsLiveData.value).isNull()

    }

    // getMoviePostersData

    @Test
    fun getMoviePostersData_whenMovieRepository_hasData_shouldEmitList() = runTest {
        val posters = postersList
        coEvery { movieRepository.getMoviePostersData(movieId) } returns Result.success(postersList)

        viewModel.getMoviePostersData(movieId)
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.moviePostersLiveData.value).isEqualTo(posters)

    }

    @Test
    fun getMoviePostersData_whenMovieRepository_hasError_shouldChangeStateToError() = runTest {
        coEvery { movieRepository.getMoviePostersData(movieId) } returns Result.failure(Throwable("Error"))
        dataStateValues.clear()

        viewModel.getMoviePostersData(movieId)
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(dataStateValues).isEqualTo(listOf(DataState.ERROR))

    }

    @Test
    fun getMoviePostersData_whenMovieRepository_hasError_shouldNotEmitList() = runTest {
        coEvery { movieRepository.getMoviePostersData(movieId) } returns Result.failure(Throwable("Error"))

        viewModel.getMoviePostersData(movieId)
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.moviePostersLiveData.value).isNull()

    }

    // Navigation (navigationToMovieDetails)

    @Test
    fun getMovieDetailsData_whenDetailsAndPostersSuccess_shouldEmitNavigationEvent() = runTest {
        coEvery { movieRepository.getMovieDetailsData(movieId) } returns Result.success(movieDetails)
        coEvery { movieRepository.getMoviePostersData(movieId) } returns Result.success(postersList)
        dataStateValues.clear()
        navigationValues.clear()

        viewModel.getMovieDetailsData(movieId)
        dispatcher.scheduler.advanceUntilIdle()

        coVerifyOrder {
            movieRepository.getMovieDetailsData(movieId)
            movieRepository.getMoviePostersData(movieId)
        }

        assertThat(navigationValues).isNotEmpty()
        assertThat(navigationValues.size).isEqualTo(1)

    }

    @Test
    fun getMovieDetailsData_whenDetailsSuccessButPostersFailure_shouldNotEmitNavigationEvent() = runTest {
        coEvery { movieRepository.getMovieDetailsData(movieId) } returns Result.success(movieDetails)
        coEvery { movieRepository.getMoviePostersData(movieId) } returns Result.failure(Throwable("Error"))
        dataStateValues.clear()
        navigationValues.clear()

        viewModel.getMovieDetailsData(movieId)
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(navigationValues).isEmpty()
    }

    @Test
    fun getMovieDetailsData_whenDetailsFailure_shouldNotEmitNavigationEvent() = runTest {
        coEvery { movieRepository.getMovieDetailsData(movieId) } returns Result.failure(Throwable("Error"))
        dataStateValues.clear()
        navigationValues.clear()

        viewModel.getMovieDetailsData(movieId)
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(navigationValues).isEmpty()
        coVerify(exactly = 0) { movieRepository.getMoviePostersData(any())}
    }

}