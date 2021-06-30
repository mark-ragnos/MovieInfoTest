package com.example.movieinfotest.presentation.ui.random.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.databinding.ItemRandomMovieBinding
import com.example.movieinfotest.domain.entities.movie.MovieDomain
import com.example.movieinfotest.utils.RANDOM_LIST_SIZE
import com.example.movieinfotest.utils.displayMoviePoster
import com.example.movieinfotest.utils.listeners.NavigationListener
import java.util.LinkedList
import kotlin.properties.Delegates

class RandomMoviesAdapter(
    private val navigationListener: NavigationListener<Int>
) : RecyclerView.Adapter<RandomMoviesAdapter.ViewHolder>() {
    private val randomMovies = LinkedList<MovieDomain>()

    fun addMovie(movies: MovieDomain) {
        val oldList = LinkedList(randomMovies)
        randomMovies.addFirst(movies)
        if (itemCount > RANDOM_LIST_SIZE) {
            randomMovies.removeLast()
        }

        val diffCallback = RandomMovieDiffCallback(oldList, randomMovies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRandomMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(randomMovies[position], navigationListener)
    }

    override fun getItemCount(): Int = randomMovies.size

    class ViewHolder(private val binding: ItemRandomMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var id by Delegates.notNull<Int>()

        fun bind(movie: MovieDomain, navigationListener: NavigationListener<Int>) {
            id = movie.id

            binding.apply {
                name.text = movie.title
                description.text = movie.overview
                moviePoster.displayMoviePoster(movie.posterPath)
            }

            binding.root.setOnClickListener {
                navigationListener.navigate(id)
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
}
