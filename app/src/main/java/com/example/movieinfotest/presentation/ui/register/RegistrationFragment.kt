package com.example.movieinfotest.presentation.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentRegistrationBinding
import com.example.movieinfotest.presentation.ui.base.BaseFragment
import com.example.movieinfotest.utils.isCorrectUserData

class RegistrationFragment : BaseFragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val parentViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        init()
        setupUI()
        inputTextWatcher()
        preValidateButton()

        return binding.root
    }

    private fun init() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupUI() {
        binding.regTextLoginHelp.setOnClickListener {
            requireActivity().onBackPressed()
        }

        firebase()
    }

    private fun firebase() {
        binding.regBtnRegistration.setOnClickListener {
            val email = binding.regEmail.text.toString()
            val password = binding.regPassword.text.toString()
            showProgressBar(true)
            parentViewModel.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        requireActivity().onBackPressed()
                    } else {
                        makeShortMessage(view, resources.getText(R.string.autentification_failed))
                    }
                    showProgressBar(false)
                }
        }
    }

    private fun showProgressBar(isProgress: Boolean) {
        binding.progressBar.visibility = if (isProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun inputTextWatcher() {
        binding.regEmail.addTextChangedListener {
            binding.regBtnRegistration.isEnabled =
                isCorrectUserData(it.toString(), binding.regPassword.text.toString())
        }

        binding.regPassword.addTextChangedListener {
            binding.regBtnRegistration.isEnabled =
                isCorrectUserData(binding.regEmail.text.toString(), it.toString())
        }
    }

    private fun preValidateButton() {
        binding.regBtnRegistration.isEnabled =
            isCorrectUserData(binding.regEmail.text.toString(), binding.regPassword.text.toString())
    }

    companion object {
        fun navigate(navController: NavController) {
            navController.navigate(R.id.action_global_registrationGraph)
        }
    }
}
