package com.example.movieinfotest.presentation.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieinfotest.R
import com.example.movieinfotest.presentation.screens.utils.POSTER_IMAGE_SIZE
import com.example.movieinfotest.presentation.screens.views.ImageLoader
import com.example.movieinfotest.utils.moviedbSpecificUtils.MALE


@Composable
fun <T : Any> ActorList(
    actors: List<T>,
    displayItem: @Composable (T) -> Unit,
    decorator: @Composable () -> Unit = {
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
    }
) {
    LazyRow(
    ) {
        items(items = actors) {
            decorator()
            displayItem(it)
            decorator()
        }
    }
}

@Composable
fun ActorCard(
    id: Int,
    name: String,
    role: String,
    profilePath: String? = null,
    gender: Int? = null,
    onCardClick: (Int) -> Unit = {}
) {
    val placeholder =
        if (gender == MALE) R.drawable.ic_placeholder_male else R.drawable.ic_placeholder_female

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .clickable {
                onCardClick(id)
            }
    ) {
        Column {
            ImageLoader(
                path = profilePath,
                imageSize = POSTER_IMAGE_SIZE,
                placeholder = placeholder
            )

            Text(
                text = name,
                modifier = Modifier.width(POSTER_IMAGE_SIZE.width),
                maxLines = 2
            )
            Text(
                text = role,
                modifier = Modifier.width(POSTER_IMAGE_SIZE.width),
                maxLines = 2
            )
        }
    }
}
