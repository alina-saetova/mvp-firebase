package ru.itis.mvp_firebase.ui

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.itis.mvp_firebase.R
import ru.itis.mvp_firebase.data.Note
import ru.itis.mvp_firebase.databinding.FragmentUserDataBinding
import ru.itis.mvp_firebase.di.App
import ru.itis.mvp_firebase.ui.adapter.NoteAdapter
import ru.itis.mvp_firebase.presenter.UserDataPresenter
import ru.itis.mvp_firebase.ui.view.UserDataView
import javax.inject.Inject
import javax.inject.Provider

class UserDataFragment : MvpAppCompatFragment(), UserDataView {

    private lateinit var binding: FragmentUserDataBinding
    private lateinit var adapter: NoteAdapter

    @Inject
    lateinit var presenterProvider: Provider<UserDataPresenter>

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectUserDataFragment(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDataBinding.inflate(inflater)
        binding.fab.setOnClickListener(addNote)
        adapter = NoteAdapter()
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    private val addNote = View.OnClickListener {
        val view = layoutInflater.inflate(R.layout.dialog_view, null)
        MaterialAlertDialogBuilder(context)
            .setTitle(DIALOG_TITLE)
            .setView(view)
            .setNegativeButton(DIALOG_CANCEL) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(DIALOG_SAVE) { dialog, _ ->
                val title = view.findViewById<EditText>(R.id.et_title).text.toString()
                val content = view.findViewById<EditText>(R.id.et_content).text.toString()
                presenter.addNote(title, content)
                dialog.dismiss()
            }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_user_data, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signOut -> {
                presenter.signOut()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updateList(list: MutableList<Note>) {
        adapter.update(list)
    }

    override fun showErrorToast(errorMsg: String) {
        Toast.makeText(activity, errorMsg, Toast.LENGTH_LONG).show()
    }

    override fun setNote(note: Note) {
        adapter.setNote(note)
    }

    override fun navigateUp() {
        findNavController().navigate(R.id.action_userDataFragment_to_chooseWayFragment)
    }

    companion object {
        const val DIALOG_TITLE = "Add a note"
        const val DIALOG_CANCEL = "cancel"
        const val DIALOG_SAVE = "save"
    }
}
