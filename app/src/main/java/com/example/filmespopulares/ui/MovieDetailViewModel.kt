package com.example.filmespopulares.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.filmespopulares.Repository.MoviesRepository
import com.example.filmespopulares.data.MovieDatabase
import com.example.filmespopulares.data.MovieEntity
import com.example.filmespopulares.data.MoviesRepositoryDatabase
import com.example.filmespopulares.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MoviesRepositoryDatabase

    private val _existFavorite = MutableLiveData<Boolean>()
    val existFavorite: LiveData<Boolean> get() = _existFavorite

    var _movieDetail = MutableLiveData<Movie>()
    val movieDetail: LiveData<Movie> get() = _movieDetail


    init {
        val movieDao = MovieDatabase.getDatabase(application).movieDao()
        repository = MoviesRepositoryDatabase(movieDao)
    }

    suspend fun getMovieDetailApi(movieId: Int): Movie {
        return MoviesRepository.getMovieDetail(movieId)
    }

    // viewModelScope is part of kotlin co-routines. Using this with Dispatchers.IO, means that
    // I want to run this code in a background thread
    // Is a bad practice to launch database jobs from the main thread
    fun addMovie(movie: MovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovie(movie)
        }
    }

    fun isFavorite(movieId: Int) {
        viewModelScope.launch {
            val isFavorite = repository.isFavorite(movieId)
            _existFavorite.value = isFavorite
        }
    }

    fun deleteMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(movieId)
        }
    }

}