package com.example.movieinfotest.presentation.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movieinfotest.R
import com.example.movieinfotest.presentation.screens.utils.PROFILE_FACE
import com.example.movieinfotest.presentation.screens.views.ImageLoader
import com.example.movieinfotest.utils.FLAG_PROFILE_FACE
import com.example.movieinfotest.utils.moviedbSpecificUtils.MALE

@Composable
fun <T : Any> ActorList(
    modifier: Modifier = Modifier,
    actors: List<T>,
    displayItem: @Composable (T) -> Unit,
    decorator: @Composable () -> Unit = {
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
    }
) {
    LazyRow(
        modifier = modifier
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
        modifier = Modifier
            .height(200.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp), true)
            .clickable {
                onCardClick(id)
            }
    ) {
        Column {
            ImageLoader(
                path = profilePath,
                placeholder = placeholder,
                imageResolution = FLAG_PROFILE_FACE,
                modifier = Modifier.size(PROFILE_FACE.width, PROFILE_FACE.height)
            )

            Text(
                text = name,
                modifier = Modifier.width(PROFILE_FACE.width),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = role,
                modifier = Modifier.width(PROFILE_FACE.width),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = MaterialTheme.typography.subtitle2.fontSize
            )
        }
    }
}
