package br.com.alexander.awesomemovieapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alexander.awesomemovieapp.data.DataState
import br.com.alexander.awesomemovieapp.data.Movie
import br.com.alexander.awesomemovieapp.data.MovieResponse
import br.com.alexander.awesomemovieapp.data.MovieDetails
import br.com.alexander.awesomemovieapp.data.MoviePosters
import br.com.alexander.awesomemovieapp.data.Poster
import br.com.alexander.awesomemovieapp.movieHome.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieViewModel : ViewModel() {

    // live data para MovieDetailsFragment
    val movieDetailsLiveData: LiveData<MovieDetails>
        get() = _movieDetailsLiveData

    private val _movieDetailsLiveData = MutableLiveData<MovieDetails>()

    // live data para MovieFragment
    val movieListLiveData: LiveData<List<Movie>>
        get() = _movieListLiveData

    private val _movieListLiveData = MutableLiveData<List<Movie>>()

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

    init {
        _dataStateLiveData.postValue(DataState.LOADING)
        getMoviesData()
    }

    fun onMovieSelected(movieId: Int) {
        getMovieDetailsData(movieId)
    }

    private fun getMoviesData() {
        movieService.getPopularMovies().enqueue(object: Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    _movieListLiveData.postValue(response.body()?.results)
                    _dataStateLiveData.postValue(DataState.SUCCESS)
                } else {
                    _dataStateLiveData.postValue(DataState.ERROR)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("MovieViewModel - getPopularMovies", "Error: ${t.message}")
                _dataStateLiveData.postValue(DataState.ERROR)
            }
        })
    }

    private fun getMovieDetailsData(movieId: Int) {
        movieService.getMovieDetails(movieId).enqueue(object: Callback<MovieDetails>{
            override fun onResponse(
                call: Call<MovieDetails>,
                response: Response<MovieDetails>
            ) {
                if (response.isSuccessful) {
                    _movieDetailsLiveData.postValue(response.body())
                    getMoviePostersData(movieId)
                } else {
                    _dataStateLiveData.postValue(DataState.ERROR)
                }
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Log.e("MovieViewModel - getMovieDetails", "Error: ${t.message}")
                _dataStateLiveData.postValue(DataState.ERROR)
            }
        })
    }

    private fun getMoviePostersData(movieId: Int) {
        movieService.getMoviePosters(movieId).enqueue(object: Callback<MoviePosters> {
            override fun onResponse(
                call: Call<MoviePosters>,
                response: Response<MoviePosters>
            ) {
                if (response.isSuccessful) {
                    _moviePostersLiveData.postValue(response.body()?.posters)
                    _navigationToMovieDetails.postValue(MovieEvent(Unit))
                } else {
                    _dataStateLiveData.postValue(DataState.ERROR)
                }
            }

            override fun onFailure(call: Call<MoviePosters>, t: Throwable) {
                Log.e("MovieViewModel - getMoviePosters", "Error: ${t.message}")
                _dataStateLiveData.postValue(DataState.ERROR)
            }
        })
    }

}
