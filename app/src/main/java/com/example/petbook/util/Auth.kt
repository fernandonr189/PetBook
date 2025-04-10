package com.example.petbook.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

enum class OnboardingStatus {
    NOT_STARTED, PROFILE_COMPLETE, PET_PROFILE_COMPLETE, ERROR
}

fun passwordReset(
    auth: FirebaseAuth, email: String, onEmailSent: () -> Unit, onFail: (String?) -> Unit
) {
    auth.sendPasswordResetEmail(email).addOnSuccessListener {
        onEmailSent()
    }.addOnFailureListener { exception ->
        onFail(exception.message)
    }
}

fun signUpFirebase(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onFail: (String?) -> Unit,
    onSuccess: (FirebaseUser) -> Unit
) {
    auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { result ->
        if (result.user == null) {
            onFail("User is null")
        } else {
            onSuccess(result.user!!)
        }
    }.addOnFailureListener { exception ->
        onFail(exception.message)
    }
}

fun loginFirebase(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onFail: (String?) -> Unit,
    onSuccess: (FirebaseUser) -> Unit
) {
    auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { result ->
        if (result.user != null) {
            onSuccess(result.user!!)
        } else {
            onFail("User does not exist")
        }
    }.addOnFailureListener { exception ->
        onFail(exception.message)
    }
}