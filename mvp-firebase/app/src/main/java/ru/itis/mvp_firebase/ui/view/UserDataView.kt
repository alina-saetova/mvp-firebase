package ru.itis.mvp_firebase.ui.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.itis.mvp_firebase.data.Note

@AddToEndSingle
interface UserDataView : MvpView {

    fun updateList(list: MutableList<Note>)
    fun showErrorToast(errorMsg: String)
    fun setNote(note: Note)
}
