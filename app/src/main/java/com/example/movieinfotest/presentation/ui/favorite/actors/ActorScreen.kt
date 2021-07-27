package com.example.movieinfotest.presentation.ui.favorite.actors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.domain.entities.actor.ActorInfoDomain
import com.example.movieinfotest.presentation.ui.composite.layouts.RoundedCenterScreenPlaceholder
import com.example.movieinfotest.presentation.ui.composite.widgets.ImageLoader
import com.example.movieinfotest.presentation.ui.details.actors.ActorViewModel
import com.example.movieinfotest.presentation.ui.utils.POSTER_IMAGE_SIZE_BIG
import com.example.movieinfotest.utils.FLAG_PROFILE_W185
import com.example.movieinfotest.utils.moviedbSpecificUtils.MALE
import com.example.movieinfotest.utils.moviedbSpecificUtils.getGenderText

@Composable
fun ActorScreen(
    actorViewModel: ActorViewModel = viewModel(),
    actorId: Int
) {
    actorViewModel.sendActorId(actorId)

    val actor by actorViewModel.actorInfo.collectAsState()

    if (actor == null) {
        RoundedCenterScreenPlaceholder()
    } else {
        ActorContent(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            actor = actor!!
        )
    }
}

@Composable
private fun ActorContent(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    actor: ActorInfoDomain
) = Column(
    modifier = modifier,
    horizontalAlignment = horizontalAlignment
) {
    ImageLoader(
        path = actor.profilePath,
        imageResolution = FLAG_PROFILE_W185,
        modifier = Modifier
            .padding(top = 8.dp)
            .size(POSTER_IMAGE_SIZE_BIG.width, POSTER_IMAGE_SIZE_BIG.height)
            .clip(RoundedCornerShape(8.dp)),
        placeholder = if (actor.gender == MALE) R.drawable.ic_placeholder_male else R.drawable.ic_placeholder_female
    )

    Text(
        text = actor.name,
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(top = 8.dp)
    )

    PersonalInfo(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        knownFor = actor.knownForDepartment,
        birthPlace = actor.placeOfBirth,
        birthDay = actor.birthday,
        deathDay = actor.deathDay,
        gender = actor.gender,
        biography = actor.biography
    )
}

@Composable
fun PersonalInfo(
    modifier: Modifier = Modifier,
    knownFor: String?,
    birthPlace: String?,
    birthDay: String?,
    deathDay: String?,
    gender: Int,
    biography: String
) = Column(
    modifier = modifier
) {
    Text(
        text = stringResource(id = R.string.actorInfo_personal_info),
        style = MaterialTheme.typography.h5
    )

    if (!knownFor.isNullOrEmpty()) {
        TitledText(
            title = stringResource(id = R.string.actorInfo_notable_for),
            modifier = Modifier.padding(top = 8.dp),
            text = knownFor
        )
    }

    TitledText(
        title = stringResource(id = R.string.actorInfo_gender),
        modifier = Modifier.padding(top = 8.dp),
        text = getGenderText(gender)
    )

    if (!birthDay.isNullOrEmpty()) {
        TitledText(
            title = stringResource(id = R.string.actorInfo_birthday),
            modifier = Modifier.padding(top = 8.dp),
            text = birthDay
        )
    }

    if (!birthPlace.isNullOrEmpty()) {
        TitledText(
            title = stringResource(id = R.string.actorInfo_birthplace),
            modifier = Modifier.padding(top = 8.dp),
            text = birthPlace
        )
    }

    if (!deathDay.isNullOrEmpty()) {
        TitledText(
            title = stringResource(id = R.string.actorInfo_deathday),
            modifier = Modifier.padding(top = 8.dp),
            text = deathDay
        )
    }

    TitledText(
        title = stringResource(id = R.string.actorInfo_biography),
        modifier = Modifier.padding(top = 8.dp),
        text = biography
    )
}

@Composable
fun TitledText(
    modifier: Modifier = Modifier,
    title: String,
    text: String
) = Column(
    modifier = modifier
) {
    Text(text = title, style = MaterialTheme.typography.h6)
    Text(text = text, style = MaterialTheme.typography.body1)
}
