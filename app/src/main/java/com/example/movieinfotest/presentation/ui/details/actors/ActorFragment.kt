package com.example.movieinfotest.presentation.ui.details.actors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movieinfotest.databinding.FragmentActorBinding
import com.example.movieinfotest.domain.entities.actor.ActorInfoDomain
import com.example.movieinfotest.presentation.di.base.AppViewModelFactory
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.utils.ToolbarMaker
import com.example.movieinfotest.utils.displayActorPicture
import com.example.movieinfotest.utils.moviedbSpecificUtils.getGenderText
import com.example.movieinfotest.utils.network.NetworkConnection
import com.example.movieinfotest.utils.setInvisible
import com.example.movieinfotest.utils.setVisible
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ActorFragment : BaseFragment() {
    private var _binding: FragmentActorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ActorViewModel by viewModels { AppViewModelFactory.makeFactory() }
    private val parentViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActorBinding.inflate(inflater, container, false)

        getActorId()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress(true)
        preload()
        initToolbar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actorInfo.collectLatest { actor ->
                    actor?.let {
                        setActor(it)
                        progress(false)
                    }
                }
            }
        }
    }

    private fun setActor(actor: ActorInfoDomain) {
        binding.apply {
            name.text = actor.name
            actor.knownForDepartment?.let { famousFor.setValue(it) }
            biography.setValue(actor.biography)
            actor.birthday?.let { birthday.setValue(it) }
            actor.deathDay?.let { deathday.setValue(it) }
            actor.placeOfBirth?.let { birthPlace.setValue(it) }
            context?.let { getGenderText(actor.gender, it) }?.let { gender.setValue(it) }
            poster.displayActorPicture(actor.profilePath, actor.gender, IMAGE_SIZE, IMAGE_SIZE)
        }
    }

    private fun progress(isInProgress: Boolean) {
        if (isInProgress) {
            binding.container.setInvisible()
            binding.progressBar.setVisible()
        } else {
            binding.container.setVisible()
            binding.progressBar.setInvisible()
        }
    }

    private fun preload() {
        if (!NetworkConnection.isOnline()) {
            requireActivity().onBackPressed()
        }
    }

    companion object {
        const val IMAGE_SIZE = 150
    }
}
