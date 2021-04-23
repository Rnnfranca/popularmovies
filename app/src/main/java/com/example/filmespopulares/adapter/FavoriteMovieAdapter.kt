package com.example.filmespopulares.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.filmespopulares.R
import com.example.filmespopulares.data.MovieEntity
import com.example.filmespopulares.ui.FavoritesFragmentDirections
import kotlinx.android.synthetic.main.list_item.view.*

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteViewHolder>() {

    private var movieList = emptyList<MovieEntity>()

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var movieId: Int = 0

        fun bind(movie: MovieEntity, holder: FavoriteViewHolder) {

            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/original${movie.posterPath}")
                .transform(CenterCrop())
                .into(itemView.item_image)

            movieId = movie.movieId
            itemView.item_title.text = movie.title
            itemView.vote_average.text = movie.voteAverage.toString()
            itemView.release_date.text = movie.releaseDate

            holder.itemView.item_image.setOnClickListener {
                val action =
                    FavoritesFragmentDirections.actionFavoritesFragmentToMovieDetailFragment(movieId = movieId)
                holder.itemView.findNavController().navigate(action)
            }

            holder.itemView.more_details.setOnClickListener {
                val action =
                    FavoritesFragmentDirections.actionFavoritesFragmentToMovieDetailFragment(movieId = movieId)
                holder.itemView.findNavController().navigate(action)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(movieList[position], holder)
    }

    fun setData(movie: List<MovieEntity>) {
        this.movieList = movie
        notifyDataSetChanged()

    }

}