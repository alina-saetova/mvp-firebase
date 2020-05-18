package ru.itis.mvp_firebase.ui.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ChooseWayView : MvpView {

    fun showLoading()
    fun hideLoading()
    fun showError()
}
