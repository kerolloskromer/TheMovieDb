package com.kromer.themoviedb.presentation.ui.main.list

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kromer.themoviedb.R
import com.kromer.themoviedb.databinding.FragmentMoviesBinding
import com.kromer.themoviedb.domain.model.Movie
import com.kromer.themoviedb.extensions.hide
import com.kromer.themoviedb.extensions.show
import com.kromer.themoviedb.presentation.base.BaseFragment
import com.kromer.themoviedb.utils.EndlessRecyclerViewScrollListener
import com.kromer.themoviedb.utils.Status
import com.kromer.themoviedb.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {

    private val viewModel: MoviesViewModel by viewModels()
    private val items: ArrayList<Movie> = ArrayList()
    private lateinit var adapter: MoviesAdapter
    private var isDataLoading = false

    private var datePickerDialog: DatePickerDialog? = null
    private var adultMenuFilter: MenuItem? = null

    override fun getVBInflater(): (LayoutInflater) -> FragmentMoviesBinding =
        FragmentMoviesBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        reset()
        getData(1)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_filter, menu)
        adultMenuFilter = menu.findItem(R.id.action_filter_type)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_filter_type -> {
                reset()
                item.isChecked = !item.isChecked
                getData(1)
                true
            }
            R.id.action_filter_date -> {
                setupDatePickerDialog()
                true
            }
            R.id.action_filter_clear -> {
                reset()
                getData(1)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupDatePickerDialog() {
        val calendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            requireContext(),
            { view: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                calendar[year, month] = dayOfMonth
                val date = calendar.time
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val selected = format.format(date)
                getDataByDate(selected)
            },
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog?.datePicker?.maxDate = calendar.timeInMillis
        datePickerDialog?.show()
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

        binding.recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(
            binding.recyclerView.layoutManager as LinearLayoutManager,
            1
        ) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                getData(page)
            }

            override val isLoading: Boolean
                get() = isDataLoading
        })
    }

    private fun onItemClick(item: Movie) {
        val action = MoviesFragmentDirections.actionNavigationMoviesToDetails(item.id)
        findNavController().navigate(action)
    }

    private fun notifyAdapter(newItems: List<Movie>) {
        isDataLoading = false
        if (newItems.isNotEmpty()) {
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
        isDataLoading = false
        items.clear()
        adultMenuFilter?.isChecked = false
    }

    private fun getData(page: Int) {
        isDataLoading = true
        viewModel.getPopularMovies(
            page,
            Utils.isNetworkAvailable(requireContext()),
            adultMenuFilter?.isChecked ?: false
        )
    }

    private fun getDataByDate(date: String) {
        reset()
        viewModel.getByDate(date)
    }
}