package com.example.petbook.util

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth

fun createUserAccount(
    auth: FirebaseAuth,
    email: String,
    password: String,
    context: Activity,
    onUserCreated: () -> Unit,
    onFail: (String) -> Unit) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(context) { task ->
            if (task.isSuccessful) {
                onUserCreated()
            } else {
                onFail(task.exception?.message.toString())
            }
        }
}