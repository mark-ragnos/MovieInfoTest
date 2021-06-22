package com.example.movieinfotest.presentation.ui.details.actors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movieinfotest.databinding.FragmentActorBinding
import com.example.movieinfotest.domain.entities.actor.ActorInfoDomain
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.moviedbSpecificUtils.getGenderText
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ActorFragment : BaseFragment() {
    private lateinit var binding: FragmentActorBinding
    private val viewModel: ActorViewModel by viewModels { AppViewModelFactory.makeFactory() }
    private val parentViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActorBinding.inflate(inflater, container, false)

        initToolbar()
        observeData()
        getActorId()

        return binding.root
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        ToolbarMaker.makeDefaultToolbar(binding.toolbar, parentViewModel, this)
    }

    private fun getActorId() {
        viewModel.sendActorId(ActorFragmentArgs.fromBundle(requireArguments()).actorId)
    }

    private fun observeData() {
        viewModel.actorInfo.onEach {
            it?.let { setActor(it) }
        }.launchIn(lifecycleScope)
    }

    private fun setActor(actor: ActorInfoDomain) {
        binding.apply {
            actor.knownForDepartment?.let { famousFor.setValue(it) }
            biography.setValue(actor.biography)
            actor.birthday?.let { birthday.setValue(it) }
            actor.deathDay?.let { deathday.setValue(it) }
            actor.placeOfBirth?.let { birthPlace.setValue(it) }
            context?.let { getGenderText(actor.gender, it) }?.let { gender.setValue(it) }
        }
    }
}
