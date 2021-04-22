package com.example.filmespopulares.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.filmespopulares.R
import com.example.filmespopulares.adapter.MovieDetailAdapter
import com.example.filmespopulares.data.MovieEntity
import com.example.filmespopulares.databinding.FragmentMovieDetailBinding
import com.example.filmespopulares.model.Movie
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailFragment : Fragment() {

    val args: MovieDetailFragmentArgs by navArgs()

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private var movieId: Int = 0
    private lateinit var movie: Movie

    private lateinit var movieDetailAdapter: MovieDetailAdapter

    // view model inicialization
    private lateinit var mMovieDetailViewModel: MovieDetailViewModel

    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // recebendo o id fo filme clicado no MovieListFragment
        movieId = args.movieId

        setHasOptionsMenu(true)
    }


    // inflando o layout e passando a referencia do fragmentMovieDetail para o binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater)

        // as View will be enable only after onCreateView, mUserViewModel need to be assign here
        mMovieDetailViewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)

        return binding.root
    }

    // realizando o binding na view
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        movieDetailAdapter = MovieDetailAdapter(binding)

        checkIsFavorite()

        // click listener to insert data on database
        binding.favoriteButton.setOnClickListener {

            if (isFavorite) {
                deleteMovie()
                changeButtonContent(!isFavorite)
                isFavorite = !isFavorite
            } else {
                insertDataToDatabase()
                changeButtonContent(!isFavorite)
                isFavorite = !isFavorite
            }

        }

        getMovieDetail(movieId)

        mMovieDetailViewModel.movieDetail.observe(viewLifecycleOwner, Observer { movie ->
            movieDetailAdapter.bind(movie)
            this.movie = movie
        })

    }

    private fun checkIsFavorite() {
        // send movieId for view model verify if the movie is saved as favorite
        mMovieDetailViewModel.isFavorite(movieId)

        // observe movie status (if it is favorite or not)
        mMovieDetailViewModel.existFavorite.observe(viewLifecycleOwner, Observer { existFavorite ->
            isFavorite = existFavorite
            changeButtonContent(existFavorite)
        })
    }

    private fun changeButtonContent(existFavorite: Boolean) {
        if (existFavorite) {
            binding.favoriteButton.text = "Remover dos favoritos"
            binding.favoriteButton.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_button_favorite_filled,
                0,
                0,
                0
            )


        } else {
            binding.favoriteButton.text = "Adicionar aos favoritos"
            binding.favoriteButton.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_button_favorite_outline,
                0,
                0,
                0
            )
        }
    }


    private fun getMovieDetail(movieId: Int) {
        lifecycleScope.launch {
            val response = mMovieDetailViewModel.getMovieDetailApi(movieId)
            mMovieDetailViewModel._movieDetail.postValue(response)
        }
    }

    // function to get data movie's field and insert into database
    private fun insertDataToDatabase() {
        val movieTitle = binding.movieTitle.text.toString()
        val posterPath = this.movie.posterPath
        val releaseDate = binding.releaseDate.text.toString()
        val voteAverage = binding.voteAverage.text.toString().toFloat()

        try {
            // create movie object
            val movie = MovieEntity(0, movieId, movieTitle, posterPath, releaseDate, voteAverage)
            // add data to database
            mMovieDetailViewModel.addMovie(movie)
//            Toast.makeText(requireContext(), "Filme Adicionado aos favoritos", Toast.LENGTH_LONG)
//                .show()
        } catch (e: Exception) {
//            Toast.makeText(requireContext(), "Filme não foi salvo nos favoritos", Toast.LENGTH_LONG)
//                .show()

            Log.e("MovieDetailFragment", "Falha ao salvar filme como favorito")
        }
    }

    private fun deleteMovie() {
        mMovieDetailViewModel.deleteMovie(movieId)
//        Toast.makeText(requireContext(), "Filme removido dos favoritos", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}


