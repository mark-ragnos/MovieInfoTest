package com.example.movieinfotest.presentation.ui.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.LoadingStateBinding

class MovieLoadingStateAdapter(private val adapter: MovieAdapter) :

    LoadStateAdapter<MovieLoadingStateAdapter.LoadingStateHolder>() {
    class LoadingStateHolder(
        private val binding: LoadingStateBinding,
        private val retryCallback: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener {
                retryCallback.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                if (loadState is LoadState.Error) {
                    errorMsg.text = loadState.error.localizedMessage
                }
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState !is LoadState.Loading
                errorMsg.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: LoadingStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingStateHolder =
        LoadingStateHolder(
            LoadingStateBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.loading_state, parent, false)
            )
        ) { adapter.retry() }
}
