package com.example.filmespopulares.Repository

import com.example.filmespopulares.api.Api
import com.example.filmespopulares.model.GetMoviesResponse
import com.example.filmespopulares.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * estrutura para comunicação com a webservice API
 * É declarado um singleton, pois é necessário apenas uma instância deste objeto durante
 * o programa e ele e seus mebros precisam ser visíveis para o programa inteiro
 */
object MoviesRepository {

    private val api: Api

    init {
        // instanciando o retrofit dentro do init para que ele seja instanciando quando
        // o singleton for inicializado
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // retornando uma classe que implementa a interface api
        api = retrofit.create(Api::class.java)
    }

    // função para de fato executar o GET na API, através do retrofit e usando a o endpoint da api
    suspend fun getPopularMovies(page: Int = 1): GetMoviesResponse {
        return api.getPopularMovies(page = page)
    }

    /**
     * Função para retornar os detalhes do filme com base no id
     */
    suspend fun getMovieDetail(movieId: Int): Movie {
        return api.getMovieDetail(movieId)
    }


}