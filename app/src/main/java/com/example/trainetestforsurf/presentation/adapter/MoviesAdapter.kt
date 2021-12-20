package com.example.trainetestforsurf.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.trainetestforsurf.databinding.MovieRowBinding
import com.example.trainetestforsurf.domain.Movies
import com.example.trainetestforsurf.presentation.viewmodel.MoviesViewModel

class MoviesAdapter(
    private val requireActivity : FragmentActivity,
    private val saveMove : (Movies) -> Unit,
    private val viewModel: MoviesViewModel
) : ListAdapter<Movies, MovieViewHolder>(MoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieRowBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(requireActivity,binding,viewModel)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position),saveMove)
    }
}

private class MoviesDiffCallback : DiffUtil.ItemCallback<Movies>() {

    override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean =
        oldItem == newItem
}