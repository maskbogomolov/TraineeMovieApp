package com.example.trainetestforsurf.presentation.adapter

import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.trainetestforsurf.R
import com.example.trainetestforsurf.databinding.MovieRowBinding
import com.example.trainetestforsurf.domain.Movies
import com.example.trainetestforsurf.presentation.viewmodel.MoviesViewModel

class MovieViewHolder(
    private val requireActivity: FragmentActivity,
    private val binding: MovieRowBinding,
    private val viewModel: MoviesViewModel
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(movies: Movies,saveMove : (Movies) -> Unit) {
        setTitle(movies)
        setImage(movies)
        setOverview(movies)
        setDate(movies)
        movieClick(movies)
        checkFavoriteMovie(movies)
        setClickListener(saveMove, movies)
    }

    private fun setClickListener(saveMove: (Movies) -> Unit, movies: Movies) {
        binding.heartImg.setOnClickListener {
            movies.isFavorite = !movies.isFavorite
            saveMove(movies)
            checkFavoriteMovie(movies)
        }
    }

    private fun checkFavoriteMovie(movies: Movies) {
        viewModel.isMovieFavourite(movies)
        if (movies.isFavorite) {
            binding.heartImg.setImageDrawable(ResourcesCompat.getDrawable(
                requireActivity.resources,
                R.drawable.full_heart,
                    null
                )
            )
        } else {
            binding.heartImg.setImageDrawable(ResourcesCompat.getDrawable(
                    requireActivity.resources,
                    R.drawable.empty_heart,
                    null
                )
            )
        }

    }

    private fun movieClick(movies: Movies) {
        itemView.setOnClickListener {
            Toast.makeText(requireActivity, "${movies.title}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDate(movies: Movies) {
        binding.releaseDateTxt.text = movies.releaseDate
    }

    private fun setOverview(movies: Movies) {
        binding.overviewTxt.text = movies.overview
    }

    private fun setTitle(movies: Movies) {
        binding.titleTxt.text = movies.title
    }

    private fun setImage(movies: Movies) {
        binding.movieImg.load(movies.image)
    }
}