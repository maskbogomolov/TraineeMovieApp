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
        {movie -> viewModel.save(movie)},
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
                binding.progressBarHorizontal.visibility = View.VISIBLE
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
        binding.progressBar.visibility = View.INVISIBLE
        binding.progressBarHorizontal.visibility = View.GONE
    }

    private fun showHorizontalProgressBar(){
        binding.progressBarHorizontal.visibility = View.VISIBLE
    }
    private fun hideHorizontalProgressBar(){
        binding.progressBarHorizontal.visibility = View.INVISIBLE
    }
    private fun showEmptyResponse(){
        adapter.submitList(emptyList())
        binding.recyclerview.visibility = View.GONE
        binding.emptyResponseImg.visibility = View.VISIBLE
        binding.emptyResponseTxt.visibility = View.VISIBLE
    }
    private fun hideEmptyResponse(){
        binding.recyclerview.visibility = View.VISIBLE
        binding.emptyResponseImg.visibility = View.INVISIBLE
        binding.emptyResponseTxt.visibility = View.INVISIBLE

    }
    private fun showErrorResponse(){
        adapter.submitList(emptyList())
        binding.recyclerview.visibility = View.GONE
        binding.errorImg.visibility = View.VISIBLE
        binding.errorTxt.visibility = View.VISIBLE
        binding.refreshImg.visibility = View.VISIBLE
    }
    private fun hideErrorResponse(){
        binding.recyclerview.visibility = View.VISIBLE
        binding.errorImg.visibility = View.INVISIBLE
        binding.errorTxt.visibility = View.INVISIBLE
        binding.refreshImg.visibility = View.INVISIBLE

    }
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun refreshData() {
        viewModel.loadListMovie()
    }

}