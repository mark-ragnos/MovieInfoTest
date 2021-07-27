package com.example.movieinfotest.presentation.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.actor.CastDomain
import com.example.movieinfotest.domain.entities.actor.CrewDomain
import com.example.movieinfotest.presentation.ui.composite.layouts.LazyRowWithDecorators
import com.example.movieinfotest.presentation.ui.utils.PROFILE_FACE
import com.example.movieinfotest.presentation.ui.views.ImageLoader
import com.example.movieinfotest.utils.FLAG_PROFILE_FACE
import com.example.movieinfotest.utils.moviedbSpecificUtils.MALE

@Composable
fun ActorContent(
    modifier: Modifier = Modifier,
    cast: List<CastDomain>?,
    crew: List<CrewDomain>?,
    onItemClick: (Int) -> Unit = {}
) = Column(modifier = modifier) {
    val (checked, setChecked) = remember {
        mutableStateOf(false)
    }

    if (!cast.isNullOrEmpty() && !crew.isNullOrEmpty()) {
        TextedSwitch(
            modifier = Modifier.padding(horizontal = 8.dp),
            leftItem = stringResource(id = R.string.cast),
            rightItem = stringResource(id = R.string.crew),
            checked = checked,
            onCheckedChange = setChecked
        )
    }

    val visible = cast != null || crew != null

    if (visible) {
        if (!checked) {
            LazyRowWithDecorators(items = cast!!) {
                ActorCard(
                    id = it.id,
                    name = it.name,
                    role = it.character,
                    profilePath = it.profilePath,
                    gender = it.gender,
                    onCardClick = onItemClick
                )
            }

        } else {
            LazyRowWithDecorators(items = crew!!) {
                ActorCard(
                    id = it.id,
                    name = it.name,
                    role = it.job,
                    profilePath = it.profilePath,
                    gender = it.gender,
                    onCardClick = onItemClick
                )
            }
        }
    }
}

@Composable
fun TextedSwitch(
    modifier: Modifier = Modifier,
    leftItem: String,
    rightItem: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(modifier = modifier) {
        Text(text = if (!checked) leftItem else rightItem)
        Switch(checked = checked, onCheckedChange = onCheckedChange, modifier = Modifier.weight(1f))
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
