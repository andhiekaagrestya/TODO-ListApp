package com.itulah.todo_listapps.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.itulah.todo_listapps.R
import com.itulah.todo_listapps.databinding.ItemTodoBinding

class HomeAdapter(private val todo: ArrayList<ToDo>, private val documentsId: ArrayList<String>) :
    RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder =
        ListViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(todo[position], documentsId[position])

    override fun getItemCount(): Int = todo.size

    inner class ListViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ToDo, docs: String) {
            binding.apply {
                if (item.status == true) {
                    btnComplete.isEnabled = false
                    root.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.core_color
                        )
                    )
                    btnComplete.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    tvTodo.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    tvTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    tvDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    tvUser.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    tvAssign.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    icDate.setColorFilter(ContextCompat.getColor(itemView.context, R.color.white))
                }
                tvTitle.text = item.title
                tvDate.text = item.date
                tvTodo.text = item.description
                tvUser.text = item.assignTo
                btnComplete.setOnClickListener {
                    MaterialAlertDialogBuilder(
                        itemView.context
                    ).setTitle("Are you sure complete the task?")
                        .setMessage("Choose option")
                        .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
                        .setPositiveButton("Yes") { _, _ ->
                            Firebase.firestore.collection("todo").document(docs)
                                .update(mapOf("status" to true))
                            it.findNavController().navigate(R.id.navigation_home)
                        }
                        .show()
                }
            }
        }
    }

}