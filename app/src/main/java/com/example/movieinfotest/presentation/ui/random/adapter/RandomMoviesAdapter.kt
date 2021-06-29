package com.example.movieinfotest.presentation.ui.random.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.databinding.ItemRandomMovieBinding
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.utils.displayMoviePoster
import java.util.LinkedList

class RandomMoviesAdapter : RecyclerView.Adapter<RandomMoviesAdapter.ViewHolder>() {
    private val randomMovies = LinkedList<MovieDomain>()

    fun addMovie(movies: MovieDomain) {
        randomMovies.addFirst(movies)
        if (itemCount > RANDOM_LIST_SIZE){
            randomMovies.removeLast()
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRandomMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(randomMovies[position])
    }

    override fun getItemCount(): Int = randomMovies.size

    class ViewHolder(private val binding: ItemRandomMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDomain) {
            binding.apply {
                name.text = movie.title
                description.text = movie.overview
                moviePoster.displayMoviePoster(movie.posterPath)
            }
        }
    }

    class RandomMovieDiffCallback(
        private val oldList: List<MovieDomain>,
        private val newList: List<MovieDomain>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    companion object{
        const val RANDOM_LIST_SIZE = 20
    }
}
