package br.com.alexander.awesomemovieapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.alexander.awesomemovieapp.data.DataState
import br.com.alexander.awesomemovieapp.data.Movie
import br.com.alexander.awesomemovieapp.data.MovieDetails
import br.com.alexander.awesomemovieapp.data.Poster
import br.com.alexander.awesomemovieapp.repository.MovieRepository
import kotlinx.coroutines.launch

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

    private val movieRepository = MovieRepository(application)

    init {
        _dataStateLiveData.postValue(DataState.LOADING)
        getMoviesData()
    }

    fun onMovieSelected(movieId: Int) {
        getMovieDetailsData(movieId)
    }

    private fun getMoviesData() {
        viewModelScope.launch {
            val movieListResult = movieRepository.getMovieData()

            movieListResult.fold(
                onSuccess = {
                    _movieListLiveData.postValue(it)
                    _dataStateLiveData.postValue(DataState.SUCCESS)
                },
                onFailure = {
                    _dataStateLiveData.postValue(DataState.ERROR)
                }
            )
        }
    }

    private fun getMovieDetailsData(movieId: Int) {
        viewModelScope.launch {
            val movieDetailsResult = movieRepository.getMovieDetailsData(movieId)
            movieDetailsResult.fold(
                onSuccess = {
                    _movieDetailsLiveData.postValue(it)
                    getMoviePostersData(movieId)
                },
                onFailure = {
                    _dataStateLiveData.postValue(DataState.ERROR)
                }
            )
        }
    }

    private fun getMoviePostersData(movieId: Int) {
        viewModelScope.launch {
            val moviePostersResult = movieRepository.getMoviePostersData(movieId)
            moviePostersResult.fold(
                onSuccess = {
                    _moviePostersLiveData.postValue(it)
                    _navigationToMovieDetails.postValue(MovieEvent(Unit))
                },
                onFailure = {
                    _dataStateLiveData.postValue(DataState.ERROR)
                }
            )
        }
    }

}
