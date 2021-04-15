package com.example.movieinfotest.presentation.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentRegistrationBinding
import com.example.movieinfotest.utils.isCorrectUserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        init()
        setupUI()

        return binding.root
    }

    private fun init() {
        auth = Firebase.auth

        (activity as MainActivity).supportActionBar?.title =
            resources.getString(R.string.register)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setupUI() {
        binding.regTextLoginHelp.setOnClickListener {
            activity?.onBackPressed()
        }

        firebase()
    }

    private fun firebase(){
        binding.regBtnRegistration.setOnClickListener {
            val email = binding.regEmail.text.toString()
            val password = binding.regPassword.text.toString()
            if (isCorrectUserData(email, password)) {
                showProgressBar(true)
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        (activity as MainActivity).onBackPressed()
                        activity?.recreate()
                    } else {
                        makeToast(resources.getText(R.string.autentification_failed))
                    }
                    showProgressBar(false)
                }
            }
            else
                makeToast(resources.getText(R.string.incorrect_user_data))
        }
    }

    private fun makeToast(message: CharSequence){
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showProgressBar(isProgress: Boolean) {
        binding.progressBar.visibility = if (isProgress) View.VISIBLE else View.INVISIBLE
    }
}