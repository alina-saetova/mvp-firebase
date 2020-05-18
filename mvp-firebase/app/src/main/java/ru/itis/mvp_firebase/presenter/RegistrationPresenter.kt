package ru.itis.mvp_firebase.presenter

import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import ru.itis.mvp_firebase.data.repository.AuthRepository
import ru.itis.mvp_firebase.navigation.Screens
import ru.itis.mvp_firebase.ui.view.RegistrationView
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import javax.inject.Inject

@InjectViewState
class RegistrationPresenter @Inject constructor(
    private val repository: AuthRepository,
    private val router: Router
) : MvpPresenter<RegistrationView>() {

    fun signUp(email: String, password: String) {
        if (validateForm(email, password)) {
            viewState.showLoading()
            presenterScope.launch {
                val success = repository.registerWithEmailAndPassword(email, password)
                if (success) {
                    navigateToLogin()
                } else {
                    viewState.showError()
                    viewState.hideLoading()
                }
            }
        }
    }

    private fun navigateToLogin() {
        router.backTo(Screens.EmailAuthScreen)
    }

    private fun validateForm(email: String, password: String): Boolean {
        var valid = true
        if (email.isEmpty()) {
            valid = false
        }
        viewState.setEmailValid(valid)
        if (password.isEmpty()) {
            valid = false
        }
        viewState.setPasswordValid(valid)
        return valid
    }
}
