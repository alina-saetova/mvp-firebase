package ru.itis.mvp_firebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import ru.itis.mvp_firebase.databinding.FragmentPhoneAuthBinding
import java.util.concurrent.TimeUnit


class PhoneAuthFragment : Fragment() {

    private lateinit var binding: FragmentPhoneAuthBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private var mVerificationId = ""
    private var mResendToken: ForceResendingToken? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance()
        binding = FragmentPhoneAuthBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnSignIn.setOnClickListener(sendCodeClickListener)
        return binding.root
    }


    private val sendCodeClickListener = View.OnClickListener {
        if (!validatePhone()) {
            Toast.makeText(activity, ERROR_VALIDATE, Toast.LENGTH_LONG).show()
        } else {
            activity?.let { it1 ->
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    binding.etPhone.text.toString(),
                    60,
                    TimeUnit.SECONDS,
                    it1,
                    mCallbacks
                )
            }
        }
    }

    private fun validatePhone(): Boolean {
        return if (binding.etPhone.text.isNullOrEmpty()) {
            binding.etPhone.error = ERROR_MSG
            false
        } else {
            binding.etPhone.error = null
            true
        }
    }

    private val mCallbacks: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: ForceResendingToken
            ) {
                mVerificationId = s
                mResendToken = forceResendingToken
            }
        }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        binding.progressBar.visibility = View.VISIBLE
        activity?.let {
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_phoneAuthFragment_to_userDataFragment)
                    } else {
                        Toast.makeText(activity, ERROR_AUTH, Toast.LENGTH_LONG).show()
                    }
                    binding.progressBar.visibility = View.GONE
                }
        }
    }

    companion object {
        const val ERROR_VALIDATE = "Try again"
        const val ERROR_MSG = "Required"
        const val ERROR_AUTH = "Authentication failed"
    }
}
