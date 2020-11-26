package com.kromer.themoviedb.presentation.ui.main.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kromer.themoviedb.domain.interactor.GetMoviesByDateInteractor
import com.kromer.themoviedb.domain.interactor.GetPopularMoviesInteractor
import com.kromer.themoviedb.domain.model.Movie
import com.kromer.themoviedb.presentation.base.BaseViewModel
import com.kromer.themoviedb.utils.Resource
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(
    private val getPopularMoviesInteractor: GetPopularMoviesInteractor,
    private val getMoviesByDateInteractor: GetMoviesByDateInteractor
) : BaseViewModel() {

    private val _popularMovies = MutableLiveData<Resource<List<Movie>>>()
    val popularMovies: LiveData<Resource<List<Movie>>> = _popularMovies

    fun getPopularMovies(page: Int, forceUpdate: Boolean, includeAdult: Boolean) {
        viewModelScope.launch {
            _popularMovies.value = Resource.loading(data = null)
            try {
                _popularMovies.value =
                    Resource.success(
                        data = getPopularMoviesInteractor.getPopularMovies(
                            page,
                            forceUpdate,
                            includeAdult
                        )
                    )
            } catch (exception: Exception) {
                _popularMovies.value =
                    Resource.error(data = null, message = exception.message ?: "Error Occurred!")
            }
        }
    }

    fun getByDate(date: String) {
        viewModelScope.launch {
            _popularMovies.value = Resource.loading(data = null)
            try {
                _popularMovies.value =
                    Resource.success(
                        data = getMoviesByDateInteractor.getByDate(date)
                    )
            } catch (exception: Exception) {
                _popularMovies.value =
                    Resource.error(data = null, message = exception.message ?: "Error Occurred!")
            }
        }
    }
}