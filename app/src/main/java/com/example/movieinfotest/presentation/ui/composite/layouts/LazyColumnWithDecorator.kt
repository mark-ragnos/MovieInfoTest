package com.example.movieinfotest.presentation.ui.composite.layouts

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> LazyColumnWithDecorators(
    modifier: Modifier = Modifier,
    items: List<T>,
    beforeItemDecorator: @Composable (T) -> Unit = { Spacer(modifier = Modifier.padding(top = 2.dp)) },
    afterItemDecorator: @Composable (T) -> Unit = { Spacer(modifier = Modifier.padding(bottom = 2.dp)) },
    content: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = items) { item ->
            beforeItemDecorator(item)
            content(item)
            afterItemDecorator(item)
        }
    }
}
