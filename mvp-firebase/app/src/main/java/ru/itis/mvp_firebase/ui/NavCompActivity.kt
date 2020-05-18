package ru.itis.mvp_firebase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_nav_comp.*
import ru.itis.mvp_firebase.R


class NavCompActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_comp)

        val navController = findNavController(R.id.navFragment)
        NavigationUI.setupWithNavController(navView, navController)
    }
}
