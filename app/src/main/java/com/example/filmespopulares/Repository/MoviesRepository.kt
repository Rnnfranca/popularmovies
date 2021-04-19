package com.example.filmespopulares.Repository

import android.util.Log
import com.example.filmespopulares.api.Api
import com.example.filmespopulares.model.GetMoviesResponse
import com.example.filmespopulares.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularMovies(page = page)
            // enqueue é usado para fazer uma requisição assincrona
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            // se tiver resposta da api, retornar apenas a o valores contidos dentro de movies
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure getPopularMovies()", t)
                }

            })
    }

    /**
     * função para fazer o GET no detalhe do filme
     */
    fun getMovieDetail(
        movieId: Int,
        // onSuccess é um parâmetro que é uma função que não retorna nada, mas aceita um Movie
        onSuccess: (movies: Movie) -> Unit, // Higher-Order function para fazer um callback em caso de resposta de sucesso
        // onError é o mesmo que onSucess, mas não aceita nada
        onError: () -> Unit // Higher-Order function para fazer um callback em caso de resposta de erro
    ){
        val requestMovie: Call<Movie> = api.getMovieDetail(movieId)

        requestMovie.enqueue(object : Callback<Movie>{

            // quando não é possível fazer a requisição
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e("MovieRepository", "getMovieDetail onFailure")
            }

            //caso obtenha uma resposta, não quer dizer que seja sucesso, pode ser uma resposta
            // com erro
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    // requisição retornou com sucesso
                    if (responseBody != null) {
                        // invoke() é como executar uma higher-order function
                        onSuccess.invoke(responseBody)
                    } else {
                        onError.invoke()
                    }

                } else {
                    // não retornou com sucesso
                    onError.invoke()
                }
            }

        })
    }
}