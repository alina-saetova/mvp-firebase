package ru.itis.mvp_firebase.presenter

import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import ru.itis.mvp_firebase.data.repository.AuthRepository
import ru.itis.mvp_firebase.navigation.Screens
import ru.itis.mvp_firebase.ui.view.ChooseWayView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ChooseWayPresenter @Inject constructor(
    private val repository: AuthRepository,
    private val router: Router
) : MvpPresenter<ChooseWayView>() {

    fun authWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        presenterScope.launch {
            val success = repository.authWithGoogle(credential)
            if (success) {
                navigateToUserData()
            } else {
                viewState.showError()
                viewState.hideLoading()
            }
        }
    }

    private fun navigateToUserData() {
        router.navigateTo(Screens.UserDataScreen)
    }

    fun navigateToEmailAuth() {
        router.navigateTo(Screens.EmailAuthScreen)
    }

    fun navigateToPhoneAuth() {
        router.navigateTo(Screens.PhoneAuthScreen)
    }

    fun navigateToNavComp() {
        router.navigateTo(Screens.NavCompScreen)
    }
}
