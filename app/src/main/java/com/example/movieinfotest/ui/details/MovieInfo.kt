package com.example.movieinfotest.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.movieinfotest.R
import com.example.movieinfotest.network.MovieHelper
import com.example.movieinfotest.utils.getGenreList
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieInfo : Fragment() {

    lateinit var image:ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRes = inflater.inflate(R.layout.fragment_movie_info, container, false)
        image = viewRes.findViewById<ImageView>(R.id.info_poster)
        test(viewRes)
        return viewRes
    }

    fun test(view:View) {

        val helper = MovieHelper()

        CoroutineScope(Dispatchers.Main).launch {
            val info = helper.getDetailsInformation("111717")

            val name = view.findViewById<TextView>(R.id.info_name)
            val desc = view.findViewById<TextView>(R.id.info_description)
            val date = view.findViewById<TextView>(R.id.info_date)
            val rating = view.findViewById<TextView>(R.id.info_rating)
            val genres = view.findViewById<TextView>(R.id.info_genres)

            name.text = info?.title
            desc.text = info?.overview
            date.text = "Release: ${info?.release_date}"
            rating.text = "Rating: ${info?.vote_average.toString()}"
            genres.text = getGenreList(info!!.genres)

            Picasso.get()
                .load("https://www.themoviedb.org/t/p/w1280${info?.poster_path}")
                .resize(200, 300)
                .centerCrop()
                .into(image)
        }
    }

}