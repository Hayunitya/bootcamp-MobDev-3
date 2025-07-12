package com.example.todolist

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.DialogEditTodoBinding
import com.example.todolist.model.Todo
import com.example.todolist.viewmodel.TodoViewModel

class EditTodoDialogFragment(private val todo: Todo) : DialogFragment() {

    private lateinit var binding: DialogEditTodoBinding
    private lateinit var viewModel: TodoViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogEditTodoBinding.inflate(LayoutInflater.from(context))
        viewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        binding.etTitle.setText(todo.title)
        binding.etDesc.setText(todo.description)

        return AlertDialog.Builder(requireContext())
            .setTitle("Edit To-do")
            .setView(binding.root)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = binding.etTitle.text?.toString()?.trim() ?: ""
                val newDesc = binding.etDesc.text?.toString()?.trim() ?: ""

                if (newTitle.isEmpty()) {
                    Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val updatedTodo = todo.copy(title = newTitle, description = newDesc)
                viewModel.updateTodo(updatedTodo)

                Toast.makeText(requireContext(), "To-do updated", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
