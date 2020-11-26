package com.kromer.themoviedb.presentation.ui.main.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kromer.themoviedb.domain.interactor.GetMovieInteractor
import com.kromer.themoviedb.domain.model.Movie
import com.kromer.themoviedb.presentation.base.BaseViewModel
import com.kromer.themoviedb.utils.Resource
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val getMovieInteractor: GetMovieInteractor
) : BaseViewModel() {

    private val _movie = MutableLiveData<Resource<Movie>>()
    val movie: LiveData<Resource<Movie>> = _movie

    fun getMovie(id: String) {
        viewModelScope.launch {
            _movie.value = Resource.loading(data = null)
            try {
                _movie.value =
                    Resource.success(
                        data = getMovieInteractor.getMovie(id)!!
                    )
            } catch (exception: Exception) {
                _movie.value =
                    Resource.error(data = null, message = exception.message ?: "Error Occurred!")
            }
        }
    }
}