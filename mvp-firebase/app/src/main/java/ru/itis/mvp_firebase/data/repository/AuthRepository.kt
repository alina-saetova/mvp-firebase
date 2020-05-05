package ru.itis.mvp_firebase.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider

interface AuthRepository {

    suspend fun registerWithEmailAndPassword(email: String, password: String): Boolean

    suspend fun authWithGoogle(cred: AuthCredential): Boolean

    suspend fun authWithEmail(email: String, password: String): Boolean

    suspend fun resetPassword(email: String): Boolean

    suspend fun authWithPhone(
        phoneNumber: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

    suspend fun signInWithCredential(cred: AuthCredential): Boolean

    fun getCurrentUser(): FirebaseUser?
    fun signOut()
}
