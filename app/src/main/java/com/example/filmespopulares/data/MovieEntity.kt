package com.example.filmespopulares.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The table to storage users
 */
@Entity(tableName = "movie_table") // @entity is used to specify table's name
data class MovieEntity(
    // these properties are the columns of the table
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var movieId: Int,
    var title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Float
)