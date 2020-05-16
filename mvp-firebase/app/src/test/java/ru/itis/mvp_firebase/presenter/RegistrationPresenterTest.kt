package ru.itis.mvp_firebase.presenter

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import ru.itis.mvp_firebase.CoroutinesTestExtension
import ru.itis.mvp_firebase.data.repository.AuthRepository
import ru.itis.mvp_firebase.ui.view.`RegistrationView$$State`

@ExtendWith(MockKExtension::class)
class RegistrationPresenterTest {

    @MockK
    lateinit var mockAuthRepository: AuthRepository

    @MockK
    lateinit var mockViewState: `RegistrationView$$State`

    lateinit var presenter: RegistrationPresenter

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @BeforeEach
    fun setUp() {
        presenter = spyk(RegistrationPresenter(mockAuthRepository), recordPrivateCalls = true)
        presenter.setViewState(mockViewState)
    }

    @Test
    fun signUpSuccess() {
        every { presenter["validateForm"](any<String>(), any<String>()) } returns true
        every { mockViewState.showLoading() } just runs
        every { mockViewState.navigateToLogin() } just runs
        coEvery {
            mockAuthRepository.registerWithEmailAndPassword(any(), any())
        } returns true

        val inputEmail = "qwerty@gmail.com"
        val inputPassword = "123456"
        presenter.signUp(inputEmail, inputPassword)

         verify {
             mockViewState.showLoading()
             mockViewState.navigateToLogin()
         }
    }

    @Test
    fun signUpFailed() {
        every { presenter["validateForm"](any<String>(), any<String>()) } returns true
        every { mockViewState.showLoading() } just runs
        every { mockViewState.showError() } just runs
        every { mockViewState.hideLoading() } just runs
        coEvery {
            mockAuthRepository.registerWithEmailAndPassword(any(), any())
        } returns false

        val inputEmail = "qwerty@gmail.com"
        val inputPassword = "123456"
        presenter.signUp(inputEmail, inputPassword)

        verifyOrder {
            mockViewState.showLoading()
            mockViewState.showError()
            mockViewState.hideLoading()
        }
    }
}
