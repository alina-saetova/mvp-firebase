package ru.itis.mvp_firebase.di

import android.app.Application
import moxy.MvpFacade
import ru.itis.mvp_firebase.di.component.AppComponent
import ru.itis.mvp_firebase.di.component.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MvpFacade.init()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
