package ru.itis.mvp_firebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.itis.mvp_firebase.R
import ru.itis.mvp_firebase.databinding.FragmentPhoneAuthBinding
import ru.itis.mvp_firebase.di.App
import ru.itis.mvp_firebase.presenter.PhoneAuthPresenter
import ru.itis.mvp_firebase.ui.view.PhoneAuthView
import javax.inject.Inject
import javax.inject.Provider

class PhoneAuthFragment : MvpAppCompatFragment(), PhoneAuthView {

    private lateinit var binding: FragmentPhoneAuthBinding

    @Inject
    lateinit var presenterProvider: Provider<PhoneAuthPresenter>

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectPhoneAuthFragment(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhoneAuthBinding.inflate(inflater)
        setOnClickListeners()
        return binding.root
    }

    private fun setOnClickListeners() {
        binding.btnSignIn.setOnClickListener {
            presenter.sendCode(binding.etPhone.text.toString())
        }
    }

    override fun setPhoneValid(valid: Boolean) {
        if (valid) {
            binding.etPhone.error = null
        } else {
            binding.etPhone.error = ERROR_MSG
        }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun navigateToUserData() {
        findNavController().navigate(R.id.action_phoneAuthFragment_to_userDataFragment)
    }

    override fun showErrorToast(errorMsg: String) {
        Toast.makeText(activity, errorMsg, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val ERROR_MSG = "Required"
    }
}
