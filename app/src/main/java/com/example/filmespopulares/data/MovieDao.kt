package com.example.filmespopulares.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // if there is a new exactly the same user, apply a strategy or ignore
    suspend fun addMovie(movie: MovieEntity) // suspend is necessary because it will be used with coroutine

    @Query("SELECT * FROM movie_table ORDER BY voteAverage ASC") // Function to return all data, sorted by id in ascending order
    fun readAllData(): LiveData<List<MovieEntity>>

    @Query("SELECT EXISTS(SELECT * FROM movie_table WHERE movieId = :movieId)")
    suspend fun isFavorite(movieId: Int): Boolean

    @Query("DELETE FROM movie_table WHERE movieId = :movieId")
    suspend fun deleteMovie(movieId: Int)
}