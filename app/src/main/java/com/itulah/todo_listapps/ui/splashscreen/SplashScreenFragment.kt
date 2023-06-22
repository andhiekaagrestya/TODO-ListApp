package com.itulah.todo_listapps.ui.splashscreen

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.itulah.todo_listapps.R
import com.itulah.todo_listapps.databinding.FragmentSplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        loadScreen()
        return binding.root
    }

    private fun loadScreen() {
        Handler().postDelayed({
            lifecycleScope.launchWhenCreated {
                checkUserSession()
            }
        }, 2000L)
    }

    private fun checkUserSession() {
        if (Firebase.auth.currentUser != null) {
            findNavController().navigate(R.id.action_splashScreenFragment_to_navigation_home)
        } else {
            findNavController().navigate(R.id.action_splashScreenFragment_to_startedFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}