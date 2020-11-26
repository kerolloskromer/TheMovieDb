package com.kromer.themoviedb.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.kromer.themoviedb.databinding.ActivityMainBinding
import com.kromer.themoviedb.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun getVBInflater(): (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.popularMovies.observe(this, {

        })
    }
}