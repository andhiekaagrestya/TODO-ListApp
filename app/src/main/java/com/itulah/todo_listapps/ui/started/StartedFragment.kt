package com.itulah.todo_listapps.ui.started

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.itulah.todo_listapps.R
import com.itulah.todo_listapps.databinding.FragmentStartedBinding

class StartedFragment : Fragment() {

    private var _binding: FragmentStartedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignIn.setOnClickListener {
            it.findNavController().navigate(R.id.action_startedFragment_to_loginFragment)
        }
        binding.btnSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_startedFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}