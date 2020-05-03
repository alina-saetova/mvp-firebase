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
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_emailAuthFragment_to_registrationFragment)
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            binding.progressBar.visibility = View.VISIBLE
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    findNavController().navigate(R.id.action_emailAuthFragment_to_userDataFragment)
                }
                else {
                    Toast.makeText(activity, "Login Failed", Toast.LENGTH_LONG).show()
                }
                binding.progressBar.visibility = View.GONE
            }
        }
        return binding.root
    }

}
