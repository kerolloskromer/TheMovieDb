package com.kromer.themoviedb.presentation.ui.main.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.kromer.themoviedb.BuildConfig
import com.kromer.themoviedb.databinding.BottomSheetMovieDetailsBinding
import com.kromer.themoviedb.extensions.hide
import com.kromer.themoviedb.extensions.show
import com.kromer.themoviedb.presentation.base.BaseBottomSheet
import com.kromer.themoviedb.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsBottomSheet : BaseBottomSheet<BottomSheetMovieDetailsBinding>() {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var movieId: String

    override fun getVBClass(): Class<BottomSheetMovieDetailsBinding> =
        BottomSheetMovieDetailsBinding::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getExtras()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.getMovie(movieId)
    }

    private fun getExtras() {
        val args = MovieDetailsBottomSheetArgs.fromBundle(requireArguments())
        movieId = args.movieId
    }

    private fun setupObservers() {
        viewModel.movie.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressBar.show()
                    }

                    Status.ERROR -> {
                        binding.progressBar.hide()

                    }

                    Status.SUCCESS -> {
                        binding.progressBar.hide()

                        binding.ivPhoto.load(BuildConfig.IMAGE_BASE_URL + it.data?.posterPath)
                        binding.tvTitle.text = it.data?.title
                        binding.tvOverview.text = it.data?.overview.toString()
                        binding.tvRating.text = it.data?.voteAverage.toString()
                    }
                }
            }
        )
    }
}
