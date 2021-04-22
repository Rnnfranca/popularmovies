package com.example.filmespopulares.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmespopulares.adapter.MoviesListAdapter
import com.example.filmespopulares.databinding.FragmentMovieListBinding
import kotlinx.coroutines.launch


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
    private lateinit var mMovieListViewModel: MovieListViewModel


    // Fragmento criado, mas a view ainda não foi criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // habilita a navegação através das opções do menu
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
        mMovieListViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

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

        getMovieApi()

        mMovieListViewModel.moviesApi.observe(viewLifecycleOwner, Observer { listMovies ->
            popularMoviesListAdapter.updateMovies(listMovies)
        })
    }

    private fun getMovieApi() {
        lifecycleScope.launch {
            val response = mMovieListViewModel.getMovieApi()
            mMovieListViewModel._moviesApi.postValue(response.movies)
        }
    }

}