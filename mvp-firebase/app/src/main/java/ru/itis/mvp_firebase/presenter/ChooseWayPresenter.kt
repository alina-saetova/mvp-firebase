package ru.itis.mvp_firebase.presenter

import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import ru.itis.mvp_firebase.data.repository.AuthRepository
import ru.itis.mvp_firebase.ui.view.ChooseWayView
import javax.inject.Inject

@InjectViewState
class ChooseWayPresenter @Inject constructor(
    private val repository: AuthRepository
) : MvpPresenter<ChooseWayView>() {

    fun authWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        presenterScope.launch {
            val success = repository.authWithGoogle(credential)
            if (success) {
                viewState.navigateToUserData()
            } else {
                viewState.showError()
                viewState.hideLoading()
            }
        }
    }
}
