package com.example.filmespopulares.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.filmespopulares.R
import com.example.filmespopulares.model.Movie
import com.example.filmespopulares.ui.MovieListFragmentDirections
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MoviesListAdapter(
    private var movies: List<Movie>
) : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_image)
        private var title: TextView = itemView.findViewById(R.id.item_title)
        private var popularity: TextView = itemView.findViewById(R.id.vote_average)
        private var relaseDate: TextView = itemView.findViewById(R.id.release_date)
        private var moreDetails: TextView = itemView.findViewById(R.id.more_details)


        fun bind(movie: Movie, holder: MovieViewHolder) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/original${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)

            title.text = movie.title
            popularity.text = movie.voteAverage.toString()
            var date = movie.releaseDate
            val dateParsed = LocalDate.parse(date)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            date = dateParsed.format(formatter)

            relaseDate.text = date

            // adiciona o evento de click no view holder
            holder.poster.setOnClickListener {
                val action =
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieId = movie.id)
                holder.itemView.findNavController().navigate(action)
            }

            holder.moreDetails.setOnClickListener {
                val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieId = movie.id)
                holder.itemView.findNavController().navigate(action)
            }

        }

    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], holder)
    }

}