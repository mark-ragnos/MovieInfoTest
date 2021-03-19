package com.example.movieinfotest.presentation.ui.details.actors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.actor.Actor
import com.example.movieinfotest.utils.registerImage

class ActorAdapter(private val list: List<Actor> ):RecyclerView.Adapter<ActorAdapter.ActorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorAdapter.ActorHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_actor_list, parent, false)
        return ActorHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ActorAdapter.ActorHolder, position: Int) {
        val movie = list.get(position)
        holder.poster.registerImage(movie.profile_path)
        holder.name.text = movie.name
        holder.role.text = movie.character
    }

    inner class ActorHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        lateinit var poster:ImageView
        lateinit var name:TextView
        lateinit var role:TextView

        init {
            poster = itemView.findViewById(R.id.actor_poster)
            name = itemView.findViewById(R.id.actor_name)
            role = itemView.findViewById(R.id.actor_role)
        }
    }
}