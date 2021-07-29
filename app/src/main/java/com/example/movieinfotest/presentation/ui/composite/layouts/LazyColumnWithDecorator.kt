package com.example.movieinfotest.presentation.ui.composite.layouts

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> LazyColumnWithDecorators(
    modifier: Modifier = Modifier,
    items: List<T>,
    addInProgress: Boolean = false,
    beforeItemDecorator: @Composable (T) -> Unit = { Spacer(modifier = Modifier.padding(top = 2.dp)) },
    afterItemDecorator: @Composable (T) -> Unit = { Spacer(modifier = Modifier.padding(bottom = 2.dp)) },
    content: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier.animateContentSize(),
        reverseLayout = true
    ) {
        items(items = items) { item ->
            beforeItemDecorator(item)
            content(item)
            afterItemDecorator(item)
        }

        if (addInProgress) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
