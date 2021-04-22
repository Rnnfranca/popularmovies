package com.example.filmespopulares.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmespopulares.Repository.MoviesRepository
import com.example.filmespopulares.model.GetMoviesResponse
import com.example.filmespopulares.model.Movie

class MovieListViewModel : ViewModel() {

    val _moviesApi = MutableLiveData<List<Movie>>()
    val moviesApi: LiveData<List<Movie>> get() = _moviesApi


    suspend fun getMovieApi(): GetMoviesResponse {
        return MoviesRepository.getPopularMovies()
    }

//    private fun getMoviesApi() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val movieResponse = MoviesRepository.getPopularMovies()
//            _moviesApi.value = movieResponse.movies
//        }
//    }

}