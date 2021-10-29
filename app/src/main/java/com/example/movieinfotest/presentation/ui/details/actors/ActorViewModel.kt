package com.example.movieinfotest.presentation.ui.details.actors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieinfotest.domain.entities.actor.ActorInfoDomain
import com.example.movieinfotest.domain.usecases.ActorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val actorUseCase: ActorUseCase
) : ViewModel() {
    private val _actorInfo = MutableStateFlow<ActorInfoDomain?>(null)
    val actorInfo = _actorInfo.asStateFlow()

    private val _actorId = MutableStateFlow(0)
    val actorId = _actorId.asStateFlow()

    fun sendActorId(actorId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _actorId.emit(actorId)
    }

    init {
        actorId.onEach {
            if (it != 0) {
                _actorInfo.emit(actorUseCase.getActorInfo(it))
            }
        }.launchIn(viewModelScope)
    }
}
