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
import com.example.filmespopulares.data.MovieEntity
import com.example.filmespopulares.ui.FavoritesFragmentDirections

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteViewHolder>() {

    private var movieList = emptyList<MovieEntity>()

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var movieId: Int = 0
        private val poster: ImageView = itemView.findViewById(R.id.item_image)
        private var title: TextView = itemView.findViewById(R.id.item_title)
        private var popularity: TextView = itemView.findViewById(R.id.vote_average)
        private var relaseDate: TextView = itemView.findViewById(R.id.release_date)
        private var moreDetails: TextView = itemView.findViewById(R.id.more_details)


        fun bind(movie: MovieEntity, holder: FavoriteViewHolder) {

            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/original${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)

            movieId = movie.movieId
            title.text = movie.title
            popularity.text = movie.voteAverage.toString()
            relaseDate.text = movie.releaseDate

            holder.poster.setOnClickListener {
                val action =
                    FavoritesFragmentDirections.actionFavoritesFragmentToMovieDetailFragment(movieId = movieId)
                holder.itemView.findNavController().navigate(action)
            }

            holder.moreDetails.setOnClickListener {
                val action = FavoritesFragmentDirections.actionFavoritesFragmentToMovieDetailFragment(movieId = movieId)
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