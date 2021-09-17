package com.example.movieinfotest.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

/**
 * Simple wrapper ower [repeatOnLifecycle]
 * @param state state of [repeatOnLifecycle] state param
 * @param body our suspend function
 */
fun LifecycleOwner.launchAndRepeatOnLifecycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: suspend () -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            body()
        }
    }
}
