package com.kromer.themoviedb.presentation.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import com.kromer.themoviedb.domain.interactor.GetPopularMoviesInteractor
import com.kromer.themoviedb.presentation.base.BaseViewModel
import com.kromer.themoviedb.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel @ViewModelInject constructor(
    private val getPopularMoviesInteractor: GetPopularMoviesInteractor
) : BaseViewModel() {

    val popularMovies = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getPopularMoviesInteractor.getPopularMovies(1, true)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}