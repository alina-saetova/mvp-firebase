package ru.itis.mvp_firebase

import android.os.Bundle
import android.util.Log
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

        binding.btnSignIn.setOnClickListener(sendCodeClickListener)
//        binding.btnSignIn.setOnClickListener{verifyPhoneNumberWithCode(binding.etCode.text.toString())}
        return binding.root
    }


    private val sendCodeClickListener = View.OnClickListener {
        activity?.let { it1 ->
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                binding.etPhone.text.toString(),
                60,
                TimeUnit.SECONDS,
                it1,
                mCallbacks)
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

    private fun verifyPhoneNumberWithCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(mVerificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        activity?.let {
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity, "SUCCESS", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_phoneAuthFragment_to_userDataFragment)
                    } else {
                        Toast.makeText(activity, task.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
