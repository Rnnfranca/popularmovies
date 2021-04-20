package com.example.filmespopulares.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmespopulares.Repository.MoviesRepository
import com.example.filmespopulares.adapter.MoviesListAdapter
import com.example.filmespopulares.databinding.FragmentMovieListBinding
import com.example.filmespopulares.model.Movie


/**
 * A simple [Fragment] subclass.
 * Use the [MovieListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieListFragment : Fragment() {

    // pegando a referencia do layout e atribuindo o valor de null, pois devido ao lifecycle do
    // fragmento o valor dessa variável será definida somente no método: onCreateView()
    private var _binding: FragmentMovieListBinding? = null

    // Aqui o get() significa que essa property é "get-only", ou seja, o valor dela será definido
    // apenas nessa parte do código, em outro momento não será possível redefinir o valor dela
    private val binding get() = _binding!!

    //property para pegar a referencia da recycler view
    private lateinit var recyclerView: RecyclerView

    private lateinit var popularMoviesListAdapter: MoviesListAdapter



    // Fragmento criado, mas a view ainda não foi criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        recyclerView = binding.recyclerView


        // get movies from api
        getPopularMoviesList()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




    fun getPopularMoviesList() {
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )


        popularMoviesListAdapter = MoviesListAdapter(listOf())
        recyclerView.adapter = popularMoviesListAdapter

        MoviesRepository.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesListAdapter.updateMovies(movies)
    }

    private fun onError() {
        Toast.makeText(context, "Check the internet", Toast.LENGTH_SHORT).show()
    }


}