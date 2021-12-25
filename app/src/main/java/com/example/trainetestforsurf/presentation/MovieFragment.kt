package com.example.trainetestforsurf.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainetestforsurf.R
import com.example.trainetestforsurf.databinding.FragmentMovieBinding
import com.example.trainetestforsurf.presentation.adapter.MoviesAdapter
import com.example.trainetestforsurf.presentation.viewmodel.MoviesResult
import com.example.trainetestforsurf.presentation.viewmodel.MoviesViewModel
import com.example.trainetestforsurf.util.NetworkResult
import com.example.trainetestforsurf.util.afterTextChanged
import com.example.trainetestforsurf.util.toInvisible
import com.example.trainetestforsurf.util.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel : MoviesViewModel by viewModels()
    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val adapter : MoviesAdapter by lazy {
        MoviesAdapter(requireActivity(),
        {movie -> viewModel.saveMovie(movie)},
        viewModel)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieBinding.inflate(inflater,container,false)
        binding.editText.afterTextChanged(viewModel::onNewQuery)

        viewModel.listMovie.observe(viewLifecycleOwner,::handleMoviesList)
        viewModel.searchMovie.observe(viewLifecycleOwner,::handleSearchMovie)


        binding.refreshImg.setOnClickListener {
            refreshData()
        }
        val span = when(resources.configuration.orientation){
            Configuration.ORIENTATION_LANDSCAPE -> 2
            else -> 1
        }
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(),span)
        binding.recyclerview.adapter = adapter
        return binding.root
    }

    private fun handleMoviesList(state : MoviesResult){
        when(state){
            is MoviesResult.SuccessResult ->{
                adapter.submitList(state.result)
                hideProgressBar()
                hideErrorResponse()

            }
            is MoviesResult.ErrorResult ->{
                hideProgressBar()
                showErrorResponse()
            }
        }
    }
    private fun handleSearchMovie(state : MoviesResult){
        when(state){
            is MoviesResult.SuccessSearchResult ->{
                adapter.submitList(state.result)
                hideProgressBar()
                hideEmptyResponse()
                hideErrorResponse()
                hideHorizontalProgressBar()
            }
            is MoviesResult.ErrorSearchResult ->{

                showErrorResponse()
                hideEmptyResponse()
                hideHorizontalProgressBar()
            }
            is MoviesResult.Loading ->{
                binding.progressBarHorizontal.toVisible()
                showHorizontalProgressBar()
            }
            is MoviesResult.EmptyResult ->{
                binding.emptyResponseTxt.setText(getString(R.string.emptyResult,state.request))
                showEmptyResponse()
                hideErrorResponse()
                hideHorizontalProgressBar()
            }
            is MoviesResult.EmptyQuery ->{
                hideHorizontalProgressBar()
            }
        }
    }

    private fun hideProgressBar(){
        binding.progressBar.toInvisible()
        binding.progressBarHorizontal.toInvisible()
    }

    private fun showHorizontalProgressBar(){
        binding.progressBarHorizontal.toVisible()
    }
    private fun hideHorizontalProgressBar(){
        binding.progressBarHorizontal.toInvisible()
    }
    private fun showEmptyResponse(){
        adapter.submitList(emptyList())
        binding.recyclerview.toVisible()
        binding.emptyResponseImg.toVisible()
        binding.emptyResponseTxt.toVisible()
    }
    private fun hideEmptyResponse(){
        binding.recyclerview.toVisible()
        binding.emptyResponseImg.toInvisible()
        binding.emptyResponseTxt.toInvisible()

    }
    private fun showErrorResponse(){
        adapter.submitList(emptyList())
        binding.recyclerview.toInvisible()
        binding.errorImg.toVisible()
        binding.errorTxt.toVisible()
        binding.refreshImg.toVisible()
    }
    private fun hideErrorResponse(){
        binding.recyclerview.toVisible()
        binding.errorImg.toInvisible()
        binding.errorTxt.toInvisible()
        binding.refreshImg.toInvisible()

    }
    private fun refreshData() {
        viewModel.loadListMovie()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}