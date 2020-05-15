package ru.itis.mvp_firebase.presenter

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import ru.itis.mvp_firebase.CoroutinesTestExtension
import ru.itis.mvp_firebase.data.repository.AuthRepository
import ru.itis.mvp_firebase.ui.view.`EmailAuthView$$State`

@ExtendWith(MockKExtension::class)
class EmailAuthPresenterTest {

    @MockK
    lateinit var mockAuthRepository: AuthRepository

    @MockK
    lateinit var mockViewState: `EmailAuthView$$State`

    lateinit var presenter: EmailAuthPresenter

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @BeforeEach
    fun setUp() {
        presenter = spyk(EmailAuthPresenter(mockAuthRepository))
        presenter.setViewState(mockViewState)
    }

    @Test
    fun signInWhenEmptyPassword() {
        val inputEmail = "qwerty@gmail.com"
        val inputPassword = ""

        every { mockViewState.setEmailValid(true) } just Runs
        every { mockViewState.setPasswordValid(false) } just Runs
        every { mockViewState.showErrorToast(any()) } just Runs

        presenter.signIn(inputEmail, inputPassword)

        verify {
            mockViewState.setEmailValid(true)
            mockViewState.setPasswordValid(false)
            mockViewState.showErrorToast(any())
        }
    }

    @Test
    fun signInWhenInvalidInput() {
        val inputEmail = "qwerty@gmail.com"
        val inputPassword = "123456"
        coEvery {
            mockAuthRepository.authWithEmail(inputEmail, inputPassword)
        } returns false

        every { mockViewState.showLoading() } just Runs
        every { mockViewState.hideLoading() } just Runs
        every { mockViewState.setEmailValid(true) } just Runs
        every { mockViewState.setPasswordValid(true) } just Runs
        every { mockViewState.showErrorToast(any()) } just Runs

        presenter.signIn(inputEmail, inputPassword)

        verifyOrder {
            mockViewState.setEmailValid(true)
            mockViewState.setPasswordValid(true)
            mockViewState.showLoading()
            mockViewState.showErrorToast(any())
            mockViewState.hideLoading()
        }
    }

    @Test
    fun signInSuccess() {
        val inputEmail = "qwerty@gmail.com"
        val inputPassword = "123456"
        coEvery {
            mockAuthRepository.authWithEmail(inputEmail, inputPassword)
        } returns true

        every { mockViewState.showLoading() } just Runs
        every { mockViewState.hideLoading() } just Runs
        every { mockViewState.showErrorToast(any()) } just Runs
        every { mockViewState.navigateToUserData() } just Runs
        every { mockViewState.setEmailValid(true) } just Runs
        every { mockViewState.setPasswordValid(true) } just Runs

        presenter.signIn(inputEmail, inputPassword)

        verify {
            mockViewState.setEmailValid(true)
            mockViewState.setPasswordValid(true)
            mockViewState.showLoading()
            mockViewState.navigateToUserData()

        }
        verify(exactly = 0) {
            mockViewState.hideLoading()
            mockViewState.showErrorToast(any())
        }
    }

    @Test
    fun resetPasswordSuccess() {
        val inputEmail = "qwerty@gmail.com"
        coEvery {
            mockAuthRepository.resetPassword(inputEmail)
        } returns true

        every { mockViewState.showLoading() } just Runs
        every { mockViewState.hideLoading() } just Runs
        every { mockViewState.showErrorToast(any()) } just Runs
        every { mockViewState.setEmailValid(true) } just Runs

        presenter.resetPassword(inputEmail)

        verify {
            mockViewState.setEmailValid(true)
            mockViewState.showLoading()
            mockViewState.showErrorToast(any())
            mockViewState.hideLoading()
        }
    }
}
