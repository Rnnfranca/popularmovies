package com.example.filmespopulares.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.filmespopulares.data.MovieDatabase
import com.example.filmespopulares.data.MovieEntity
import com.example.filmespopulares.data.MoviesRepositoryDatabase

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
}