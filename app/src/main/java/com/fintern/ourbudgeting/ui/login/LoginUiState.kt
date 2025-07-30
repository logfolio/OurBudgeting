package com.fintern.ourbudgeting.ui.login

import com.google.firebase.auth.FirebaseUser

data class LoginUiState(
    val currentUser: FirebaseUser? = null,
)