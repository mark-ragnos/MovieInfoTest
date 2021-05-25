package com.example.movieinfotest.presentation.ui.details.actors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.databinding.ItemActorListBinding
import com.example.movieinfotest.domain.entities.actor.CastDomain
import com.example.movieinfotest.utils.displayActorPicture

class ActorAdapter(private val list: List<CastDomain>) :
    RecyclerView.Adapter<ActorAdapter.ActorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorAdapter.ActorHolder {
        val binding =
            ItemActorListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ActorAdapter.ActorHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ActorHolder(private val binding: ItemActorListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: CastDomain) {
            binding.apply {
                actorName.text = cast.name
                actorRole.text = cast.character
                actorPoster.displayActorPicture(cast.profilePath, cast.gender, x = 100, y = 125)
            }
        }
    }
}
