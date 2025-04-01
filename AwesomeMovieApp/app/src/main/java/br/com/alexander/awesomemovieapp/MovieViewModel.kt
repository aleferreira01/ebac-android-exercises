package br.com.alexander.awesomemovieapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alexander.awesomemovieapp.placeholder.PlaceholderContent

class MovieViewModel : ViewModel() {

    // live data para MovieDetailsFragment
    val movieDetailsLiveData: LiveData<MovieDetails>
        get() = _movieDetailsLiveData

    private val _movieDetailsLiveData = MutableLiveData<MovieDetails>()

    // live data para MovieFragment
    val movieListLiveData: LiveData<MutableList<PlaceholderContent.PlaceholderItem>>
        get() = _movieListLiveData

    private val _movieListLiveData = MutableLiveData<MutableList<PlaceholderContent.PlaceholderItem>>()

    // live data para navegar para o MovieDetailsFragment
    val navigationToMovieDetails: LiveData<MovieEvent<Unit>>
        get() = _navigationToMovieDetails

    private val _navigationToMovieDetails = MutableLiveData<MovieEvent<Unit>>()

    // live data para o DataState
    val dataStateLiveData: LiveData<DataState>
        get() = _dataStateLiveData

    private val _dataStateLiveData = MutableLiveData<DataState>()


    init {
        _movieListLiveData.postValue(PlaceholderContent.ITEMS)
        _dataStateLiveData.postValue(DataState.SUCCESS)
    }

    fun onMovieSelected(position: Int, context: Context) {
        val movieDetails = MovieDetails(
            context.getString(R.string.movie_title_example),
            context.getString(R.string.movie_desc_example),
            R.drawable.movie
        )
        _movieDetailsLiveData.postValue(movieDetails)
        _navigationToMovieDetails.postValue(MovieEvent(Unit))
    }

}
