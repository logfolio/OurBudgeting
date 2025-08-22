package com.fintern.ourbudgeting.ui.common.model

import androidx.annotation.StringRes
import com.fintern.ourbudgeting.R

sealed class FirebaseError(@param:StringRes val messageResId: Int) {
    object NetworkError : FirebaseError(R.string.error_network)
    object FirestoreError : FirebaseError(R.string.error_firestore)
    object ImageUploadError : FirebaseError(R.string.error_image_upload)
    object UnknownError : FirebaseError(R.string.error_unknown)
}