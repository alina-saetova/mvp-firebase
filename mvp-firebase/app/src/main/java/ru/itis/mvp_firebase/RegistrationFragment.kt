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
import ru.itis.mvp_firebase.databinding.FragmentRegistrationBinding
import java.util.*

class RegistrationFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener(signUp)
        return binding.root
    }

    private val signUp = View.OnClickListener {
        if (!validateData()) {
            Toast.makeText(activity, ERROR_VALIDATE, Toast.LENGTH_LONG).show()
        } else {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            binding.progressBar.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    findNavController().navigateUp()
                }
                else {
                    Toast.makeText(activity, ERROR_CREATE, Toast.LENGTH_LONG).show()
                }
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun validateData(): Boolean {
        var valid = true
        if (binding.etEmail.text.isNullOrEmpty()) {
            binding.etEmail.error = ERROR_MSG
            valid = false
        }
        else {
            binding.etEmail.error = null
        }
        if (binding.etPassword.text.isNullOrEmpty()) {
            binding.etPassword.error = ERROR_MSG
            valid = false
        }
        else {
            binding.etPassword.error = null
        }
        return valid
    }

    companion object {
        const val ERROR_VALIDATE = "Try again"
        const val ERROR_MSG = "Required"
        const val ERROR_CREATE = "Registration failed"
    }
}
