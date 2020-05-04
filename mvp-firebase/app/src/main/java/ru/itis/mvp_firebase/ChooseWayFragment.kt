package ru.itis.mvp_firebase

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import ru.itis.mvp_firebase.databinding.FragmentChooseWayBinding

class ChooseWayFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentChooseWayBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseWayBinding.inflate(inflater)
        setOnClickListeners()
        configureGoogleClient()
        return binding.root
    }

    private fun setOnClickListeners() {
        binding.btnPhone.setOnClickListener(this)
        binding.btnEmail.setOnClickListener(this)
        binding.btnGoogle.setOnClickListener(this)
    }

    private fun configureGoogleClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun signIn() {
        binding.progressBar.visibility = View.VISIBLE
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        activity?.let {
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_chooseWayFragment_to_userDataFragment)
                    } else {
                        Toast.makeText(activity, ERROR_AUTH, Toast.LENGTH_LONG).show()
                    }
                    binding.progressBar.visibility = View.GONE
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(activity, ERROR_AUTH, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnPhone -> findNavController().navigate(R.id.action_chooseWayFragment_to_phoneAuthFragment)
            R.id.btnEmail -> findNavController().navigate(R.id.action_chooseWayFragment_to_emailAuthFragment)
            R.id.btnGoogle -> signIn()
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        const val ERROR_AUTH = "Authentication Failed"
    }
}
