package com.example.filmespopulares.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.filmespopulares.data.MovieDatabase
import com.example.filmespopulares.data.MovieEntity
import com.example.filmespopulares.data.MoviesRepositoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MoviesRepositoryDatabase

    private val _existFavorite = MutableLiveData<Boolean>()
    val existFavorite: LiveData<Boolean> get() = _existFavorite


    init {
        val movieDao = MovieDatabase.getDatabase(application).movieDao()
        repository = MoviesRepositoryDatabase(movieDao)
    }

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