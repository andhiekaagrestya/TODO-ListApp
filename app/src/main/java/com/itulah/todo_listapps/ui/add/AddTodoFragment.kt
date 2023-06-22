package com.itulah.todo_listapps.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.itulah.todo_listapps.R
import com.itulah.todo_listapps.databinding.FragmentAddTodoBinding
import com.itulah.todo_listapps.utils.showDatePicker

class AddTodoFragment : Fragment() {

    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTodoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etDate.setOnClickListener {
            showDatePicker(parentFragmentManager, binding.etDate)
        }
        binding.btnAdd.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            val date = binding.etDate.text.toString().trim()
            val assignTo = binding.etAssign.text.toString().trim()
            if (title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty() && assignTo.isNotEmpty()) {
                addToDoList(assignTo, date, description, title)
            } else {
                SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                    .setContentText("Field must not empty")
                    .setConfirmText("Try Again")
                    .show()
            }
        }
    }

    private fun addToDoList(assignTo: String, date: String, description: String, title: String) {
        val todo = hashMapOf(
            "assignTo" to assignTo,
            "date" to date,
            "description" to description,
            "title" to title,
            "status" to false,
        )
        db.collection("todo")
            .add(todo)
            .addOnSuccessListener {
                SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Success add ToDo List")
                    .setConfirmText("OK")
                    .show()
                findNavController().navigate(R.id.action_addTodoFragment_to_navigation_home)
            }.addOnFailureListener {
                SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                    .setContentText(it.message.toString())
                    .setConfirmText("Try Again")
                    .show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}