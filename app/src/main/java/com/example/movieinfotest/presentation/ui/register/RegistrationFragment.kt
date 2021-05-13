package com.example.movieinfotest.presentation.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.MainActivityViewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentRegistrationBinding
import com.example.movieinfotest.utils.ToastUtils
import com.example.movieinfotest.utils.isCorrectUserData


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val parentViewModel: MainActivityViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
                        (activity as MainActivity).onBackPressed()
                        activity?.recreate()
                    } else {
                        makeToast(resources.getText(R.string.autentification_failed))
                    }
                    showProgressBar(false)
                }
        }
    }

    private fun makeToast(message: CharSequence) {
        ToastUtils.makeShortMessage(requireContext(), message.toString())
    }

    private fun showProgressBar(isProgress: Boolean) {
        binding.progressBar.visibility = if (isProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun inputTextWatcher() {
        binding.regEmail.addTextChangedListener {
            binding.regBtnRegistration.isEnabled = isCorrectUserData(it.toString(), binding.regPassword.text.toString())
        }

        binding.regPassword.addTextChangedListener {
            binding.regBtnRegistration.isEnabled = isCorrectUserData(binding.regEmail.text.toString(), it.toString())
        }
    }

    private fun preValidateButton(){
        binding.regBtnRegistration.isEnabled = isCorrectUserData(binding.regEmail.text.toString(), binding.regPassword.text.toString())
    }
}