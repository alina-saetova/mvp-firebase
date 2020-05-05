package ru.itis.mvp_firebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.itis.mvp_firebase.databinding.FragmentRegistrationBinding
import ru.itis.mvp_firebase.di.App
import ru.itis.mvp_firebase.presenter.RegistrationPresenter
import ru.itis.mvp_firebase.ui.view.RegistrationView
import javax.inject.Inject
import javax.inject.Provider

class RegistrationFragment : MvpAppCompatFragment(), RegistrationView {

    private lateinit var binding: FragmentRegistrationBinding

    @Inject
    lateinit var presenterProvider: Provider<RegistrationPresenter>

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectRegFragment(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater)
        setOnClickListeners()
        return binding.root
    }

    private fun setOnClickListeners() {
        binding.btnSignUp.setOnClickListener {
            presenter.signUp(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
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

    override fun navigateToLogin() {
        findNavController().navigateUp()
    }

    override fun showError() {
        Toast.makeText(activity, ERROR_CREATE, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val ERROR_MSG = "Required"
        const val ERROR_CREATE = "Registration failed"
    }
}
