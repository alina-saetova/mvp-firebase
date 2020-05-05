package ru.itis.mvp_firebase.presenter

import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import ru.itis.mvp_firebase.data.repository.AuthRepository
import ru.itis.mvp_firebase.ui.view.EmailAuthView
import javax.inject.Inject

@InjectViewState
class EmailAuthPresenter @Inject constructor(
    private val repository: AuthRepository
) : MvpPresenter<EmailAuthView>() {

    fun signIn(email: String, password: String) {
        if (!validateEmail(email) && !validatePassword(password)) {
            viewState.showErrorToast(ERROR_VALIDATE)
        } else {
            viewState.showLoading()
            presenterScope.launch {
                val success = repository.authWithEmail(email, password)
                if (success) {
                    viewState.navigateToUserData()
                } else {
                    viewState.showErrorToast(ERROR_LOGIN)
                    viewState.hideLoading()
                }
            }
        }

    }

    fun resetPassword(email: String) {
        if (!validateEmail(email)) {
            viewState.showErrorToast(ERROR_VALIDATE)
        } else {
            viewState.showLoading()
            presenterScope.launch {
                val success = repository.resetPassword(email)
                if (success) {
                    viewState.showErrorToast(EMAIL_SENT)
                } else {
                    viewState.showErrorToast(ERROR_EMAIL_SENT)
                }
                viewState.hideLoading()
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        var valid = true
        if (email.isEmpty()) {
            valid = false
        }
        viewState.setEmailValid(valid)
        return valid
    }

    private fun validatePassword(password: String): Boolean {
        var valid = true
        if (password.isEmpty()) {
            valid = false
        }
        viewState.setPasswordValid(valid)
        return valid
    }

    companion object {
        const val ERROR_VALIDATE = "Try again"
        const val ERROR_LOGIN = "Login Failed"
        const val EMAIL_SENT = "Password reset email was sent"
        const val ERROR_EMAIL_SENT = "Failed, try again"
    }

}
