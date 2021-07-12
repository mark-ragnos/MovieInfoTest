package com.example.movieinfotest.presentation.ui.base

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

open class BaseFragment : Fragment() {
    protected val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        doWhenErrorHanding(coroutineContext, throwable)
    }

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

    /**
     * If need custom action when happened exception override this
     * By default make message when error handle
     */
    open fun doWhenErrorHanding(coroutineContext: CoroutineContext, throwable: Throwable) {
        makeShortMessage(view, "Exception handle message: $throwable")
    }
}
