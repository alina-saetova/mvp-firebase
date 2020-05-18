package ru.itis.mvp_firebase.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.itis.mvp_firebase.MainActivity
import ru.itis.mvp_firebase.di.module.FirebaseModule
import ru.itis.mvp_firebase.di.module.NavigationModule
import ru.itis.mvp_firebase.di.module.RepositoryModule
import ru.itis.mvp_firebase.ui.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        FirebaseModule::class,
        NavigationModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun injectRegFragment(registrationFragment: RegistrationFragment)
    fun injectChooseWayFragment(chooseWayFragment: ChooseWayFragment)
    fun inject(mainActivity: MainActivity)
    fun injectEmailAuthFragment(emailAuthFragment: EmailAuthFragment)
    fun injectPhoneAuthFragment(phoneAuthFragment: PhoneAuthFragment)
    fun injectUserDataFragment(userDataFragment: UserDataFragment)
}
