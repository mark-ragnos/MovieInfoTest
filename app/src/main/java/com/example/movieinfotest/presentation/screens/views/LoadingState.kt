package com.example.movieinfotest.presentation.screens.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.movieinfotest.R

fun <T : Any> LazyListScope.observePagingState(
    items: LazyPagingItems<T>
) {
    items.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item { LoadingStatus(modifier = Modifier.fillParentMaxSize()) }
            }
            loadState.append is LoadState.Loading -> {
                item { LoadingStatus() }
            }

            loadState.refresh is LoadState.Error -> {
                val e = items.loadState.refresh as LoadState.Error
                item {
                    ErrorMessage(
                        message = e.error.localizedMessage!!,
                        modifier = Modifier.fillParentMaxSize(),
                        onClickRetry = { retry() }
                    )
                }
            }
            loadState.append is LoadState.Error -> {
                val e = items.loadState.append as LoadState.Error
                item {
                    ErrorMessage(
                        message = e.error.localizedMessage!!,
                        onClickRetry = { retry() }
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String = "",
    onClickRetry: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = message, modifier = Modifier.align(Alignment.CenterHorizontally))
        Button(onClick = onClickRetry, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun LoadingStatus(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}
