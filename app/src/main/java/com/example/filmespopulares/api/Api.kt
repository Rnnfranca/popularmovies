package com.example.filmespopulares.api

import com.example.filmespopulares.model.GetMoviesResponse
import com.example.filmespopulares.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Endpoint para onde será feita a requisição [GET]
 */
interface Api {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = "86dbbfc5fedf87d00a3d8d0b8372cfcb",
        @Query("page") page: Int
    ): GetMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "86dbbfc5fedf87d00a3d8d0b8372cfcb"
    ) : Movie
}