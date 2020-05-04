package ru.itis.mvp_firebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import ru.itis.mvp_firebase.databinding.FragmentEmailAuthBinding

class EmailAuthFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentEmailAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmailAuthBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_emailAuthFragment_to_registrationFragment)
        }
        binding.btnSignIn.setOnClickListener(signIn)
        binding.btnForgotPassword.setOnClickListener(resetPassword)

        return binding.root
    }

    private val signIn = View.OnClickListener {
        if (!validateEmail() && !validatePassword()) {
            Toast.makeText(activity, ERROR_VALIDATE, Toast.LENGTH_LONG).show()
        }
        else {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            binding.progressBar.visibility = View.VISIBLE
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    findNavController().navigate(R.id.action_emailAuthFragment_to_userDataFragment)
                }
                else {
                    Toast.makeText(activity, ERROR_LOGIN, Toast.LENGTH_LONG).show()
                }
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private val resetPassword = View.OnClickListener {
        if (!validateEmail()) {
            Toast.makeText(activity, ERROR_VALIDATE, Toast.LENGTH_LONG).show()
        }
        else {
            val email = binding.etEmail.text.toString()
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(activity, EMAIL_SENT, Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(activity, ERROR_EMAIL_SENT, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateEmail(): Boolean {
        return if (binding.etEmail.text.isNullOrEmpty()) {
            binding.etEmail.error = ERROR_MSG
            false
        } else {
            binding.etEmail.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        return if (binding.etPassword.text.isNullOrEmpty()) {
            binding.etPassword.error = ERROR_MSG
            false
        } else {
            binding.etPassword.error = null
            true
        }
    }

    companion object {
        const val ERROR_VALIDATE = "Try again"
        const val ERROR_MSG = "Required"
        const val EMAIL_SENT = "Password reset email was sent"
        const val ERROR_EMAIL_SENT = "Failed, try again"
        const val ERROR_LOGIN = "Login Failed"
    }

}
