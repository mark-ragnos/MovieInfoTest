package com.example.movieinfotest.utils

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfotest.R

fun getDivider(context: Context, orientation: Int, drawableRes: Int): DividerItemDecoration {
    val divider = DividerItemDecoration(context, orientation)
    ContextCompat.getDrawable(context, drawableRes)?.let {
        divider.setDrawable(it)
    }
    return divider
}

fun RecyclerView.addDefaultDivider(context: Context?, orientation: Int) {
    context?.let {
        addItemDecoration(
            getDivider(
                it,
                orientation,
                R.drawable.divider
            )
        )
    }
}
