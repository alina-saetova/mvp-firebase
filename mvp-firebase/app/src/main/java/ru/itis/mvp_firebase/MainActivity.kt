package ru.itis.mvp_firebase

import android.os.Bundle
import moxy.MvpAppCompatActivity
import ru.itis.mvp_firebase.di.App
import ru.itis.mvp_firebase.navigation.Screens
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = SupportAppNavigator(this, R.id.main_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator.applyCommands(arrayOf(Replace(Screens.ChooseWayScreen)))
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
