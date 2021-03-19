package com.example.movieinfotest.presentation.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentLoginBinding
import com.example.movieinfotest.utils.isCorrectUserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        init()
        setupUI()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun init(){
        auth = Firebase.auth
        if (auth.currentUser != null){
            (activity as MainActivity).onBackPressed()
        }


        (activity as MainActivity).supportActionBar?.title =
            (activity as MainActivity).resources.getString(
                R.string.login
            )
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setupUI() {
        binding.logTextLoginHelp.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.logBtnLogin.setOnClickListener {
            val email = binding.logEmail.text.toString()
            val password = binding.logPassword.text.toString()
            if (isCorrectUserData(email, password))
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        (activity as MainActivity).onBackPressed()
                        activity?.recreate()
                    } else {
                        Toast.makeText(
                            context,
                            resources.getText(R.string.autentification_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            else
                Toast.makeText(
                    context,
                    resources.getText(R.string.incorrect_user_data),
                    Toast.LENGTH_SHORT
                ).show()

        }
    }


}