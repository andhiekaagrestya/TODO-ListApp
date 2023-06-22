package com.itulah.todo_listapps.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.itulah.todo_listapps.R
import com.itulah.todo_listapps.databinding.FragmentRegisterBinding


class RegisterFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pw = binding.etPw.text.toString().trim()
            val cpw = binding.etCpw.text.toString().trim()
            if (pw.isNotEmpty() && cpw.isNotEmpty() && email.isNotEmpty()){
                if (pw.equals(cpw, false)) {
                    signUp(email, pw)
                } else {
                    SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                        .setContentText("Password and Confirm Password must Match!")
                        .setConfirmText("Try Again")
                        .show()
                }
            }else{
                SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                    .setContentText("Email or Password must not empty")
                    .setConfirmText("Try Again")
                    .show()
            }
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setContentText("Your account has been created")
                        .setConfirmText("OK")
                        .show()
//                    this.dismiss()
                    this.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                } else {
                    SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                        .setContentText(task.exception?.message.toString())
                        .setConfirmText("Try Again")
                        .show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}