package com.example.filmespopulares.data

import androidx.lifecycle.LiveData

class MoviesRepositoryDatabase(private val movieDao: MovieDao) {

    // getting all users using our class UserDao with the method readallData()
    val readAllData: LiveData<List<MovieEntity>> = movieDao.readAllData()


    // function to add user using the method addUser from UserDao class
    // suspend, because we are going to use in a view model
    suspend fun addMovie(movie: MovieEntity) {
        movieDao.addMovie(movie)
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        return movieDao.isFavorite(movieId)
    }

    suspend fun deleteMovie(movieId: Int) {
        movieDao.deleteMovie(movieId)
    }

}