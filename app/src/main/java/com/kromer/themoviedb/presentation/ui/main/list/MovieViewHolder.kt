package com.kromer.themoviedb.presentation.ui.main.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kromer.themoviedb.BuildConfig
import com.kromer.themoviedb.databinding.ItemMovieBinding
import com.kromer.themoviedb.domain.model.Movie

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemMovieBinding.bind(itemView)

    fun bind(item: Movie, itemClick: (Movie) -> Unit) {
        binding.ivPhoto.load(BuildConfig.IMAGE_BASE_URL + item.posterPath)
        binding.tvTitle.text = item.title
        binding.tvRating.text = item.voteAverage.toString()
        itemView.setOnClickListener {
            itemClick.invoke(item)
        }
    }
}