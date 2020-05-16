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
import ru.itis.mvp_firebase.ui.view.`PhoneAuthView$$State`

@ExtendWith(MockKExtension::class)
class PhoneAuthPresenterTest {

    @MockK
    lateinit var mockViewState: `PhoneAuthView$$State`

    lateinit var presenter: PhoneAuthPresenter

    @MockK
    lateinit var mockAuthRepository: AuthRepository

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @BeforeEach
    fun setUp() {
        presenter = spyk(PhoneAuthPresenter(mockAuthRepository))
        presenter.setViewState(mockViewState)
    }

    @Test
    fun sendCodeInvalidPhone() {
        every { mockViewState.showErrorToast(any()) } just Runs
        every { presenter["validatePhone"](any<String>()) } returns false

        val inputPhone = "123456"
        presenter.sendCode(inputPhone)

        verify {
            mockViewState.showErrorToast(any())
        }
    }
}
