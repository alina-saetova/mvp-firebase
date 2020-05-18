package ru.itis.mvp_firebase.presenter

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import ru.itis.mvp_firebase.data.Note
import ru.itis.mvp_firebase.data.repository.AuthRepository
import ru.itis.mvp_firebase.data.repository.NoteRepository
import ru.itis.mvp_firebase.navigation.Screens
import ru.itis.mvp_firebase.ui.view.UserDataView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class UserDataPresenter @Inject constructor(
    private val authRepository: AuthRepository,
    private val noteRepository: NoteRepository,
    private val router: Router
) : MvpPresenter<UserDataView>() {

    var user: FirebaseUser? = authRepository.getCurrentUser()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (user == null) {
            navigateUp()
        } else {
            noteRepository.addListener(postListener)
        }
    }

    fun addNote(title: String, content: String) {
        presenterScope.launch {
            noteRepository.addNote(title, content)
        }
    }

    fun signOut() {
        authRepository.signOut()
        navigateUp()
    }

    private val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val list = mutableListOf<Note>()
            dataSnapshot.children.forEach {
                val note = it.getValue(Note::class.java)
                note?.let { it1 -> list.add(it1) }
            }
            viewState.updateList(list)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            viewState.showErrorToast(ERROR_FETCH)
        }
    }

    private fun navigateUp() {
        router.backTo(Screens.ChooseWayScreen)
    }

    companion object {
        const val ERROR_FETCH = "Failed fetching data"
    }
}
