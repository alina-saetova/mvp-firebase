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

class RegistrationFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            binding.progressBar.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    findNavController().navigateUp()
                }
                else {
                    Toast.makeText(activity, "FAILED", Toast.LENGTH_LONG).show()
                }
                binding.progressBar.visibility = View.GONE
            }
        }
        return binding.root
    }

}
