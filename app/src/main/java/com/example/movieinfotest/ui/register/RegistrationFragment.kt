package com.example.movieinfotest.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.movieinfotest.MainActivity
import com.example.movieinfotest.R
import com.example.movieinfotest.databinding.FragmentRegistrationBinding
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
        auth = Firebase.auth

        setupUI()
        (activity as MainActivity).supportActionBar?.title =
            (activity as MainActivity).resources.getString(
                R.string.register
            )
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    private fun setupUI() {
        if(auth.currentUser != null)
            (activity as MainActivity).onBackPressed()

        binding.regTextLoginHelp.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.regBtnRegistration.setOnClickListener {
            val email = "example@example.com"
            val password = "ASInidso6dsa"
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    (activity as MainActivity).onBackPressed()
                }else{
                    Toast.makeText(context, resources.getText(R.string.autentification_failed), Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}