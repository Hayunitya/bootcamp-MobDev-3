package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.FragmentCreateBinding
import com.example.todolist.model.Todo
import com.example.todolist.viewmodel.TodoViewModel

class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        binding.btnSimpan.setOnClickListener {
            val title = binding.etTodo.text?.toString()?.trim()
            val desc = binding.etDesc.text?.toString()?.trim()

            if (!title.isNullOrEmpty()) {
                val todo = Todo(
                    title = title,
                    description = desc ?: ""
                )

                viewModel.addTodo(todo)

                Toast.makeText(requireContext(), "To-do saved successfully!", Toast.LENGTH_SHORT).show()
                binding.etTodo.text?.clear()
                binding.etDesc.text?.clear()
            } else {
                Toast.makeText(requireContext(), "To-do title cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
