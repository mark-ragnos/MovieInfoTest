package com.example.movieinfotest.utils

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.movieinfotest.MainActivityViewModel
import com.example.movieinfotest.MovieApp
import com.example.movieinfotest.R

class ToolbarMaker {
    companion object{
        fun makeToolbar(toolbar: Toolbar, parentViewModel: MainActivityViewModel){
            toolbar.inflateMenu(R.menu.tool_menu)
            toolbar.menu.apply {
                getItem(0).isVisible = !FirebaseLogin.isLogin()
                getItem(1).isVisible = FirebaseLogin.isLogin()
                if (parentViewModel.isDarkMode)
                    getItem(2).icon =
                        ContextCompat.getDrawable(MovieApp.getInstance(), R.drawable.ic_light_mode)
                else
                    getItem(2).icon =
                        ContextCompat.getDrawable(MovieApp.getInstance(), R.drawable.ic_dark_mode)

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    getItem(2)?.iconTintList = ColorStateList.valueOf(Color.WHITE)
                }
            }
        }
    }
}