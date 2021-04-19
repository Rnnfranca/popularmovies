package com.example.filmespopulares.model

import com.google.gson.annotations.SerializedName

/**
 * data class que indica qual será a estrutura dos dados guardados
 */
data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("overview") val overview: String
)

/**
 * Resposta esperada da requisição [GET]
 */
data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>
)