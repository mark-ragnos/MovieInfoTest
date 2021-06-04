package com.example.movieinfotest.presentation.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.presentation.ui.main.MainActivityViewModel
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentLoginBinding
import com.example.movieinfotest.utils.FirebaseLogin
import com.example.movieinfotest.utils.ToastUtils
import com.example.movieinfotest.utils.isCorrectUserData

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val parentViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        init()
        setupUI()
        inputTextWatcher()
        preValidateButton()

        return binding.root
    }

    private fun init() {
        if (FirebaseLogin.isLogin()) {
            requireActivity().onBackPressed()
        }

        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupUI() {
        binding.logTextLoginHelp.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        firebase()
    }

    private fun firebase() {
        binding.logBtnLogin.setOnClickListener {
            val email = binding.logEmail.text.toString()
            val password = binding.logPassword.text.toString()
            showProgressBar(true)
            parentViewModel.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        requireActivity().onBackPressed()
                    } else {
                        makeToast(resources.getText(R.string.autentification_failed))
                    }
                    showProgressBar(false)
                }
        }
    }

    private fun makeToast(message: CharSequence) {
        context?.let { ToastUtils.makeShortMessage(it, message.toString()) }
    }

    private fun showProgressBar(isProgress: Boolean) {
        binding.progressBar.visibility = if (isProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun inputTextWatcher() {
        binding.logEmail.addTextChangedListener {
            binding.logBtnLogin.isEnabled =
                isCorrectUserData(it.toString(), binding.logPassword.text.toString())
        }

        binding.logPassword.addTextChangedListener {
            binding.logBtnLogin.isEnabled =
                isCorrectUserData(binding.logEmail.text.toString(), it.toString())
        }
    }

    private fun preValidateButton() {
        binding.logBtnLogin.isEnabled =
            isCorrectUserData(binding.logEmail.text.toString(), binding.logPassword.text.toString())
    }
}
