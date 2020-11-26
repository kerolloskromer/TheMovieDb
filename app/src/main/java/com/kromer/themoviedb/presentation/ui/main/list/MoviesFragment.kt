package com.kromer.themoviedb.presentation.ui.main.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kromer.themoviedb.R
import com.kromer.themoviedb.databinding.FragmentMoviesBinding
import com.kromer.themoviedb.domain.model.Movie
import com.kromer.themoviedb.extensions.hide
import com.kromer.themoviedb.extensions.show
import com.kromer.themoviedb.presentation.base.BaseFragment
import com.kromer.themoviedb.utils.Status
import com.kromer.themoviedb.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {

    private val viewModel: MoviesViewModel by viewModels()
    private val items: ArrayList<Movie> = ArrayList()
    private lateinit var adapter: MoviesAdapter
    private var currentPage = 1

    override fun getVBInflater(): (LayoutInflater) -> FragmentMoviesBinding =
        FragmentMoviesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        reset()
    }

    private fun setupObservers() {
        viewModel.popularMovies.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.ERROR -> {
                    showError(it.message!!)
                }

                Status.SUCCESS -> {
                    notifyAdapter(it.data!!)
                }
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = MoviesAdapter(
            items = items,
            itemClick = { onItemClick(it) }
        )
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.findLastCompletelyVisibleItemPosition() + 1
                    if (visibleItemCount == layoutManager.itemCount) {
                        getData()
                    }
                }
            }
        })
    }

    private fun onItemClick(item: Movie) {
        val action = MoviesFragmentDirections.actionNavigationMoviesToDetails(item.id)
        findNavController().navigate(action)
    }

    private fun notifyAdapter(newItems: List<Movie>) {
        if (newItems.isNotEmpty()) {
            currentPage++
            items.addAll(newItems)
            adapter.notifyDataSetChanged()
        }

        if (items.isNotEmpty()) {
            showData()
        } else {
            showError(getString(R.string.no_data))
        }
    }

    private fun showLoading() {
        binding.progressBar.show()
    }

    private fun showError(error: String) {
        binding.recyclerView.hide()
        binding.progressBar.hide()
        binding.textView.show()

        binding.textView.text = error
    }

    private fun showData() {
        binding.recyclerView.show()
        binding.progressBar.hide()
        binding.textView.hide()
    }

    private fun reset() {
        binding.recyclerView.hide()
        binding.progressBar.show()
        binding.textView.hide()
        currentPage = 1
        getData()
    }

    private fun getData() {
        viewModel.getPopularMovies(currentPage, Utils.isNetworkAvailable(requireContext()))
    }
}