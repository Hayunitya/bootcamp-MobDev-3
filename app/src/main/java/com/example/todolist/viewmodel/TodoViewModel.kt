package com.example.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.Todo
import com.example.todolist.repository.TodoRepository

class TodoViewModel : ViewModel() {

    private val repository = TodoRepository()

    private val _todos = MutableLiveData<List<Todo>>()
    val todos: LiveData<List<Todo>> = _todos

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadTodos() {
        _loading.value = true
        repository.getTodos(
            onSuccess = { list ->
                _todos.value = list
                _loading.value = false
            },
            onFailure = {
                _todos.value = emptyList()
                _loading.value = false
            }
        )
    }

    fun addTodo(todo: Todo) {
        repository.addTodo(
            todo,
            onSuccess = { loadTodos() },
            onFailure = { /* optional: tampilkan error */ }
        )
    }

    fun deleteTodo(todoId: String) {
        repository.deleteTodo(
            todoId,
            onSuccess = { loadTodos() },
            onFailure = { /* optional: tampilkan error */ }
        )
    }

    fun updateTodo(todo: Todo) {
        repository.updateTodo(
            todo,
            onSuccess = { loadTodos() },
            onFailure = { /* optional: tampilkan error */ }
        )
    }
}
