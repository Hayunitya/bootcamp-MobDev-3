package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.EditTodoDialogFragment
import com.example.todolist.databinding.ItemTodoBinding
import com.example.todolist.model.Todo

class TodoAdapter(
    private var list: List<Todo>,
    private val fragmentManager: FragmentManager,
    private val onDeleteClick: (Todo) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = list[position]
        holder.binding.apply {
            tvTitle.text = todo.title
            tvDescription.text = todo.description

            btnDelete.setOnClickListener { onDeleteClick(todo) }

            btnUpdate.setOnClickListener {
                val dialog = EditTodoDialogFragment(todo)
                dialog.show(fragmentManager, "EditTodoDialog")
            }
        }
    }

    fun updateData(newList: List<Todo>) {
        list = newList
        notifyDataSetChanged()
    }
}
