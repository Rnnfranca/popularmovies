package com.example.filmespopulares.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.filmespopulares.databinding.FragmentMovieDetailBinding
import com.example.filmespopulares.model.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieDetailAdapter (
    val binding: FragmentMovieDetailBinding
) {

    // função que recebe a informação e faz o bind
    fun bind(movie: Movie) {
        Glide.with(binding.itemImage)
            .load("https://image.tmdb.org/t/p/original${movie.posterPath}")
            .transform(CenterCrop())
            .into(binding.itemImage)
        binding.movieTitle.text = movie.title
        binding.voteAverage.text = movie.voteAverage.toString()
        binding.movieOverview.text = movie.overview

        var date = movie.releaseDate
        val dateParsed = LocalDate.parse(date)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
        date = dateParsed.format(formatter)

        binding.releaseDate.text = date
    }
}