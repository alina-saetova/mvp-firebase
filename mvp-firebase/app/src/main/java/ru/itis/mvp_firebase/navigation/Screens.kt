package ru.itis.mvp_firebase.navigation

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import ru.itis.mvp_firebase.MainActivity
import ru.itis.mvp_firebase.ui.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context): Intent? {
            return Intent(context, MainActivity::class.java)
        }
    }

    object NavCompScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context): Intent? {
            return Intent(context, NavCompActivity::class.java)
        }
    }

    object ChooseWayScreen: SupportAppScreen() {
        override fun getFragment(): Fragment = ChooseWayFragment()
    }

    object EmailAuthScreen: SupportAppScreen() {
        override fun getFragment(): Fragment = EmailAuthFragment()
    }

    object PhoneAuthScreen: SupportAppScreen() {
        override fun getFragment(): Fragment = PhoneAuthFragment()
    }

    object RegistrationScreen: SupportAppScreen() {
        override fun getFragment(): Fragment = RegistrationFragment()
    }

    object UserDataScreen: SupportAppScreen() {
        override fun getFragment(): Fragment = UserDataFragment()
    }
}
