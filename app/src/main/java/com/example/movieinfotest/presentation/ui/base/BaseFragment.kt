package com.example.movieinfotest.presentation.ui.base

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {
    fun makeShortMessage(view: View?, text: CharSequence) {
        view?.let {
            Snackbar.make(it, text, Snackbar.LENGTH_SHORT).show()
        }
    }

    fun makeLongMessage(view: View?, text: CharSequence) {
        view?.let {
            Snackbar.make(it, text, Snackbar.LENGTH_LONG).show()
        }
    }
}
