package com.example.filmespopulares.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.filmespopulares.R
import com.example.filmespopulares.model.Movie
import com.example.filmespopulares.ui.MovieListFragmentDirections
import kotlinx.android.synthetic.main.list_item.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MoviesListAdapter(
    private var movies: List<Movie>
) : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie, holder: MovieViewHolder) {
            Glide.with(itemView.item_image)
                .load("https://image.tmdb.org/t/p/original${movie.posterPath}")
                .transform(CenterCrop())
                .into(itemView.item_image)

            itemView.item_title.text = movie.title
            itemView.vote_average.text = movie.voteAverage.toString()
            var date = movie.releaseDate
            val dateParsed = LocalDate.parse(date)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            date = dateParsed.format(formatter)

            itemView.release_date.text = date

            // adiciona o evento de click no poster
            holder.itemView.item_image.setOnClickListener {
                val action =
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieId = movie.id)
                holder.itemView.findNavController().navigate(action)
            }

            holder.itemView.more_details.setOnClickListener {
                val action =
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieId = movie.id)
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