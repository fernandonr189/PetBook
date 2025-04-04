package com.example.petbook.util

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth

fun signUpFirebase(
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

fun loginFirebase(
    auth: FirebaseAuth,
    email: String,
    password: String,
    context: Activity,
    onLoginSuccessful: () -> Unit,
    onFail: (String) -> Unit) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(context) { task ->
            if (task.isSuccessful) {
                onLoginSuccessful()
            } else {
                onFail(task.exception?.message.toString())
            }
        }
}