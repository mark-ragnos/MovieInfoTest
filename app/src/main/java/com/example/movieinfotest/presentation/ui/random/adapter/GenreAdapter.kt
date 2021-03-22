package com.example.movieinfotest.presentation.ui.random.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.genre.Genre

class GenreAdapter(val context: Context, var genres: List<Genre>) : BaseAdapter() {
    override fun getCount(): Int {
        return genres.size
    }

    init {
        val res = ArrayList<Genre>()
        res.add(Genre(0, ""))
        res.addAll(genres)
        genres = res
    }

    override fun getItem(position: Int): Any {
        return genres[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    //почта? пароль

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.spinner_row, parent, false)
            vh = ItemHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.id?.text = genres[position].id.toString()
        vh.genre?.text = genres[position].name

        if(position == 0){
            vh.genre?.text = context.resources.getText(R.string.select_genre)
            vh.genre?.setTextColor(Color.GRAY)
        }
        return view
    }

    private class ItemHolder(row: View?) {
        val id = row?.findViewById<TextView>(R.id.sp_id)
        val genre = row?.findViewById<TextView>(R.id.sp_genre)
    }

}