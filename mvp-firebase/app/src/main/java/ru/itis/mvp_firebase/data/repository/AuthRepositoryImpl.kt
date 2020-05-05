package ru.itis.mvp_firebase.data.repository

import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private var firebaseAuth: FirebaseAuth,
    private var phoneAuthProvider: PhoneAuthProvider
) : AuthRepository {

    override suspend fun registerWithEmailAndPassword(email: String, password: String) =
        withContext(Dispatchers.IO) {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                true
            } catch (e: FirebaseAuthException) {
                false
            }
        }

    override suspend fun authWithGoogle(cred: AuthCredential) = withContext(Dispatchers.IO) {
        try {
            firebaseAuth.signInWithCredential(cred).await()
            true
        } catch (e: FirebaseAuthException) {
            false
        }
    }

    override suspend fun authWithEmail(email: String, password: String) =
        withContext(Dispatchers.IO) {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                true
            } catch (e: FirebaseException) {
                false
            }
        }

    override suspend fun resetPassword(email: String) = withContext(Dispatchers.IO) {
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            true
        } catch (e: FirebaseException) {
            false
        }
    }

    override suspend fun authWithPhone(
        phoneNumber: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        phoneAuthProvider.verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
//            как я не пыталась, возможно только в мэйн треде (а может плохо искала, хз)
            TaskExecutors.MAIN_THREAD,
            callbacks
        )
    }

    override suspend fun signInWithCredential(cred: AuthCredential) = withContext(Dispatchers.IO) {
        try {
            firebaseAuth.signInWithCredential(cred).await()
            true
        } catch (e: FirebaseException) {
            false
        }
    }

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    override fun signOut() {
        firebaseAuth.signOut()
    }
}
