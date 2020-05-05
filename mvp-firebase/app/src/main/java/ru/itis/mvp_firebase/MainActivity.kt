package ru.itis.mvp_firebase

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.crashlytics.android.Crashlytics
import moxy.MvpAppCompatActivity
import ru.itis.mvp_firebase.di.App

class MainActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            R.id.crash -> Crashlytics.getInstance().crash()
//        }
//        return true
//    }
}
