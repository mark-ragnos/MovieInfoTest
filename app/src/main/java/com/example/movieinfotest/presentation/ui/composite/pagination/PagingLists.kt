package com.example.movieinfotest.presentation.ui.composite.pagination

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items

@Composable
fun <T : Any> PagingLazyColumn(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
    beforeItemDecorator: @Composable (T) -> Unit = { Spacer(modifier = Modifier.padding(top = 2.dp)) },
    afterItemDecorator: @Composable (T) -> Unit = { Spacer(modifier = Modifier.padding(bottom = 2.dp)) },
    pagingStateObserver: LazyListScope.(LazyPagingItems<T>) -> Unit = { observePagingState(it) },
    content: @Composable (T) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = items) { item ->
            item?.let {
                beforeItemDecorator(item)
                content(item)
                afterItemDecorator(item)
            }
        }
        pagingStateObserver(items)
    }
}
