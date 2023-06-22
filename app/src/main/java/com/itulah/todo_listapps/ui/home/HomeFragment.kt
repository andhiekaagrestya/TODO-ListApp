package com.itulah.todo_listapps.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.itulah.todo_listapps.R
import com.itulah.todo_listapps.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private lateinit var todoList: ArrayList<ToDo>
    private lateinit var docs: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoList = arrayListOf()
        docs = arrayListOf()
        binding.rvTask.layoutManager = LinearLayoutManager(requireContext())
        db.collection("todo")
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    var i = 0
                    for (data in it.documents) {
                        docs.add(it.documents[i].id)
                        val todo = data.toObject(ToDo::class.java)
                        if (todo != null) {
                            todoList.add(todo)
                            i++
                        }
                    }
                    binding.rvTask.adapter = HomeAdapter(todoList, docs)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ERROR GET TODO", exception.message!!)
            }
        Firebase.auth.currentUser?.let {
//            binding.tvHi.text = "Hi, ${it.email?.substring(0, it.email?.length?.minus(10)!!)}"
            binding.tvHi.text = "Hi, ${it.email?.substring(0, it.email?.lastIndexOf("@")!!)}"
        }
        binding.btnAdd.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_home_to_addTodoFragment)
        }
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            it.findNavController().navigate(R.id.action_navigation_home_to_startedFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}