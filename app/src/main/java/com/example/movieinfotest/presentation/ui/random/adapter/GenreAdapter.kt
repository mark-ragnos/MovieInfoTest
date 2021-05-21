package com.example.movieinfotest.presentation.ui.random.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.databinding.SpinnerRowBinding
import com.example.movieinfotest.domain.entities.genre.GenreDomain
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.PowerSpinnerInterface
import com.skydoves.powerspinner.PowerSpinnerView

class GenreAdapter(
    override var index: Int,
    override var onSpinnerItemSelectedListener: OnSpinnerItemSelectedListener<GenreDomain>?,
    override val spinnerView: PowerSpinnerView
) : RecyclerView.Adapter<GenreAdapter.ViewHolder>(), PowerSpinnerInterface<GenreDomain> {
    private lateinit var genres: List<GenreDomain>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapter.ViewHolder {
        val binding = SpinnerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreAdapter.ViewHolder, position: Int) {
        holder.bind(genres[position])
        holder.binding.root.setOnClickListener {
            notifyItemSelected(position)
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    class ViewHolder(val binding: SpinnerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: GenreDomain) {
            binding.spId.text = genre.id.toString()
            binding.spGenre.text = genre.name
        }
    }

    override fun notifyItemSelected(index: Int) {
        val oldIndex = this.index
        this.index = index
        this.spinnerView.notifyItemSelected(index, this.genres[index].name)
        this.onSpinnerItemSelectedListener?.onItemSelected(
            oldIndex = oldIndex,
            oldItem = genres[oldIndex],
            newIndex = index,
            newItem = genres[index]
        )
    }

    override fun setItems(itemList: List<GenreDomain>) {
        genres = itemList
    }
}
