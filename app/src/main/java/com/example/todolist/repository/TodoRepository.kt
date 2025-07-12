package com.example.todolist.repository

import com.example.todolist.model.Todo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TodoRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun addTodo(todo: Todo, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        val docRef = db.collection("users")
            .document(userId)
            .collection("todos")
            .document()

        val todoWithId = todo.copy(id = docRef.id)

        docRef.set(todoWithId)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun getTodos(
        onSuccess: (List<Todo>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .collection("todos")
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { result ->
                val todos = result.map { doc ->
                    doc.toObject(Todo::class.java).copy(id = doc.id)
                }
                onSuccess(todos)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun deleteTodo(todoId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .collection("todos")
            .document(todoId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun updateTodo(todo: Todo, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .collection("todos")
            .document(todo.id)
            .set(todo)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
