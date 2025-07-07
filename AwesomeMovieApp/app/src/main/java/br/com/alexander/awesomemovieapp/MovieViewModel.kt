package br.com.alexander.awesomemovieapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.alexander.awesomemovieapp.api.MovieService
import br.com.alexander.awesomemovieapp.data.ApiCredentials
import br.com.alexander.awesomemovieapp.data.DataState
import br.com.alexander.awesomemovieapp.data.Movie
import br.com.alexander.awesomemovieapp.data.MovieDetails
import br.com.alexander.awesomemovieapp.data.Poster
import br.com.alexander.awesomemovieapp.database.MovieDatabase
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    // live data para MovieDetailsFragment
    val movieDetailsLiveData: LiveData<MovieDetails>
        get() = _movieDetailsLiveData

    private val _movieDetailsLiveData = MutableLiveData<MovieDetails>()

    // live data para MovieFragment
    val movieListLiveData: LiveData<List<Movie>?>
        get() = _movieListLiveData

    private val _movieListLiveData = MutableLiveData<List<Movie>?>()

    // live data para navegar para o MovieDetailsFragment
    val navigationToMovieDetails: LiveData<MovieEvent<Unit>>
        get() = _navigationToMovieDetails

    private val _navigationToMovieDetails = MutableLiveData<MovieEvent<Unit>>()

    // live data para o DataState
    val dataStateLiveData: LiveData<DataState>
        get() = _dataStateLiveData

    private val _dataStateLiveData = MutableLiveData<DataState>()

    // live data para MoviePosters
    val moviePostersLiveData: LiveData<List<Poster>>
        get() = _moviePostersLiveData

    private val _moviePostersLiveData = MutableLiveData<List<Poster>>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials().baseUrl)
        .client(ApiCredentials().okHttpClient().build())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val movieService = retrofit.create(MovieService::class.java)

    private val movieDatabase = MovieDatabase.getDatabase(application)
    private val movieDao = movieDatabase.movieDao()

    init {
        _dataStateLiveData.postValue(DataState.LOADING)
        getMoviesData()
    }

    fun onMovieSelected(movieId: Int) {
        getMovieDetailsData(movieId)
    }

    private fun getMoviesData() {
        viewModelScope.launch {
            try {
                val response = movieService.getPopularMovies()

                if (response.isSuccessful) {

                    val movieList = response.body()?.results

                    movieList?.let {
                        persistMovieData(it)
                    }

                    _movieListLiveData.postValue(movieList)
                    _dataStateLiveData.postValue(DataState.SUCCESS)
                } else {
                    errorHandling()
                }
            } catch (e: Exception) {
                errorHandling()
            }

        }
    }

    private fun getMovieDetailsData(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = movieService.getMovieDetails(movieId)

                if (response.isSuccessful) {
                    _movieDetailsLiveData.postValue(response.body())
                    getMoviePostersData(movieId)
                } else {
                    _dataStateLiveData.postValue(DataState.ERROR)
                }
            } catch (e: Exception) {
                _dataStateLiveData.postValue(DataState.ERROR)
            }
        }
    }

    private fun getMoviePostersData(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = movieService.getMoviePosters(movieId)

                if (response.isSuccessful) {
                    _moviePostersLiveData.postValue(response.body()?.posters)
                    _navigationToMovieDetails.postValue(MovieEvent(Unit))
                } else {
                    _dataStateLiveData.postValue(DataState.ERROR)
                }
            } catch (e: Exception) {
                _dataStateLiveData.postValue(DataState.ERROR)
            }
        }
    }

    private suspend fun persistMovieData(movieList: List<Movie>) {
        movieDao.insertAll(movieList)
    }

    private suspend fun loadPersistedMovieData() = movieDao.getAllMovies()

    private suspend fun errorHandling() {
        val movieList = loadPersistedMovieData()

        if (movieList.isNullOrEmpty()) {
            _dataStateLiveData.postValue(DataState.ERROR)
        } else {
            _movieListLiveData.postValue(movieList)
            _dataStateLiveData.postValue(DataState.SUCCESS)
        }
    }

}
