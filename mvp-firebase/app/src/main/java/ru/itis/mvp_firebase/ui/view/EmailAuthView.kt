package ru.itis.mvp_firebase.ui.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface EmailAuthView : MvpView {

    fun setEmailValid(valid: Boolean)
    fun setPasswordValid(valid: Boolean)

    fun showLoading()
    fun hideLoading()

    fun navigateToUserData()
    fun showErrorToast(errorMsg: String)
}
