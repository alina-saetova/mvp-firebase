package ru.itis.mvp_firebase.presenter

import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import ru.itis.mvp_firebase.data.repository.AuthRepository
import ru.itis.mvp_firebase.ui.view.PhoneAuthView
import javax.inject.Inject

@InjectViewState
class PhoneAuthPresenter @Inject constructor(
    private val repository: AuthRepository
) : MvpPresenter<PhoneAuthView>() {

    fun sendCode(phoneNumber: String) {
        if (!validatePhone(phoneNumber)) {
            viewState.showErrorToast(ERROR_VALIDATE)
        } else {
            presenterScope.launch(Dispatchers.IO) {
                repository.authWithPhone(phoneNumber, mCallbacks)
            }
        }
    }

    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                viewState.showErrorToast(e.message.toString())
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {

            }
        }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        viewState.showLoading()
        presenterScope.launch {
            val success = repository.signInWithCredential(credential)
            if (success) {
                viewState.navigateToUserData()
            } else {
                viewState.showErrorToast(ERROR_AUTH)
                viewState.hideLoading()
            }
        }
    }

    private fun validatePhone(phoneNumber: String): Boolean {
        var valid = true
        if (phoneNumber.isEmpty()) {
            valid = false
        }
        viewState.setPhoneValid(valid)
        return valid
    }

    companion object {
        const val ERROR_VALIDATE = "Try again"
        const val ERROR_AUTH = "Authentication failed"
    }
}
