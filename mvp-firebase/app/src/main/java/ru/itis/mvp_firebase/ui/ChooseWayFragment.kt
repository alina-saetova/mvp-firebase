package ru.itis.mvp_firebase.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.crashlytics.android.Crashlytics
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.itis.mvp_firebase.R
import ru.itis.mvp_firebase.databinding.FragmentChooseWayBinding
import ru.itis.mvp_firebase.di.App
import ru.itis.mvp_firebase.presenter.ChooseWayPresenter
import ru.itis.mvp_firebase.ui.view.ChooseWayView
import javax.inject.Inject
import javax.inject.Provider


class ChooseWayFragment : MvpAppCompatFragment(), ChooseWayView {

    private lateinit var binding: FragmentChooseWayBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var presenterProvider: Provider<ChooseWayPresenter>

    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.injectChooseWayFragment(this)
        super.onCreate(savedInstanceState)
        MobileAds.initialize(activity)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseWayBinding.inflate(inflater)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        setOnClickListeners()
        configureGoogleClient()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.crash -> Crashlytics.getInstance().crash()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setOnClickListeners() {
        binding.btnPhone.setOnClickListener {
            presenter.navigateToPhoneAuth()
        }
        binding.btnEmail.setOnClickListener {
            presenter.navigateToEmailAuth()
        }
        binding.btnToNavComp.setOnClickListener {
            presenter.navigateToNavComp()
        }
        binding.btnGoogle.setOnClickListener { signIn() }
    }

    private fun configureGoogleClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
    }

    private fun signIn() {
        showLoading()
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                account.idToken?.let { presenter.authWithGoogle(it) }
            } catch (e: ApiException) {
                showError()
            }
        }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun showError() {
        Toast.makeText(activity, ERROR_AUTH, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        const val ERROR_AUTH = "Authentication Failed"
    }
}
