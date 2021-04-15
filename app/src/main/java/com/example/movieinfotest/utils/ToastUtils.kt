package com.example.movieinfotest.utils

import android.content.Context
import android.widget.Toast

class ToastUtils {

    companion object{
        fun makeShortMessage(context: Context, message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun makeLongMessage(context: Context, message: String){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}