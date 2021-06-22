package com.example.movieinfotest.presentation.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.databinding.ItemActorListBinding
import com.example.movieinfotest.domain.entities.actor.CastDomain
import com.example.movieinfotest.utils.displayActorPicture
import com.example.movieinfotest.utils.listeners.NavigationListener

class CastAdapter(
    private val list: List<CastDomain>,
    private val navigationListener: NavigationListener<Int>
) :
    RecyclerView.Adapter<CastAdapter.ActorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        val binding =
            ItemActorListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.bind(list[position], navigationListener)
    }

    inner class ActorHolder(private val binding: ItemActorListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: CastDomain, navigationListener: NavigationListener<Int>) {
            binding.apply {
                actorName.text = cast.name
                actorRole.text = cast.character
                actorPoster.displayActorPicture(cast.profilePath, cast.gender, x = 100, y = 125)
            }
            binding.root.setOnClickListener {
                navigationListener.navigate(cast.id)
            }
        }
    }
}
