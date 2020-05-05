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
import ru.itis.mvp_firebase.databinding.FragmentEmailAuthBinding
import ru.itis.mvp_firebase.di.App
import ru.itis.mvp_firebase.presenter.EmailAuthPresenter
import ru.itis.mvp_firebase.ui.view.EmailAuthView
import javax.inject.Inject
import javax.inject.Provider

class EmailAuthFragment : MvpAppCompatFragment(), EmailAuthView {

    private lateinit var binding: FragmentEmailAuthBinding

    @Inject
    lateinit var presenterProvider: Provider<EmailAuthPresenter>

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectEmailAuthFragment(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmailAuthBinding.inflate(inflater)
        setOnClickListeners()
        return binding.root
    }

    private fun setOnClickListeners() {
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_emailAuthFragment_to_registrationFragment)
        }
        binding.btnSignIn.setOnClickListener {
            presenter.signIn(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
        binding.btnForgotPassword.setOnClickListener {
            presenter.resetPassword(binding.etEmail.text.toString())
        }
    }

    override fun setEmailValid(valid: Boolean) {
        if (valid) {
            binding.etEmail.error = null
        } else {
            binding.etEmail.error = ERROR_MSG
        }
    }

    override fun setPasswordValid(valid: Boolean) {
        if (valid) {
            binding.etPassword.error = null
        } else {
            binding.etPassword.error = ERROR_MSG
        }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun navigateToUserData() {
        findNavController().navigate(R.id.action_emailAuthFragment_to_userDataFragment)
    }

    override fun showErrorToast(errorMsg: String) {
        Toast.makeText(activity, errorMsg, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val ERROR_MSG = "Required"
    }
}
