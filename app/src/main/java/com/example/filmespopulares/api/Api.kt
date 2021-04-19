package com.example.filmespopulares.api

import com.example.filmespopulares.model.GetMoviesResponse
import com.example.filmespopulares.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Endpoint para onde será feita a requisição [GET]
 */
interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "86dbbfc5fedf87d00a3d8d0b8372cfcb",
        @Query("page") page: Int
    // o retorno da função do tipo Call indica que será enviado uma requisição e será retornado /
    // uma resposta. o <parâmetro> indica qual será o corpo da resposta esperado (em caso de sucesso)
    ): Call<GetMoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "86dbbfc5fedf87d00a3d8d0b8372cfcb"
    ) : Call<Movie>
}