package com.example.filmespopulares.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.filmespopulares.data.MovieDatabase
import com.example.filmespopulares.data.MovieEntity
import com.example.filmespopulares.data.MoviesRepositoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// extends AndroidView because it contains application reference
class FavoriteMoviesViewModel(application: Application) : AndroidViewModel(application) {

    val readlAllData: LiveData<List<MovieEntity>>
    private val repository: MoviesRepositoryDatabase

    // first block that is executed when user model user view model is called
    init {
        val movieDao = MovieDatabase.getDatabase(application).movieDao()
        repository = MoviesRepositoryDatabase(movieDao)
        readlAllData = repository.readAllData
    }

    // viewModelScope is part of kotlin co-routines. Using this with Dispatchers.IO, means that
    // I want to run this code in a background thread
    // Is a bad practice to launch database jobs from the main thread
    fun addMovie(movie: MovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovie(movie)
        }
    }
}