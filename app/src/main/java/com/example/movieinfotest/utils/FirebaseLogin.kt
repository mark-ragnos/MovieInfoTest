package com.example.movieinfotest.utils

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseLogin {

    companion object{
        fun isLogin():Boolean{
            return Firebase.auth.currentUser != null
        }
    }
}