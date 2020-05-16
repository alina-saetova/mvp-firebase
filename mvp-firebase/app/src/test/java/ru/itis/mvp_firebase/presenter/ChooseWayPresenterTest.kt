package ru.itis.mvp_firebase.presenter

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import ru.itis.mvp_firebase.CoroutinesTestExtension
import ru.itis.mvp_firebase.data.repository.AuthRepository
import ru.itis.mvp_firebase.ui.view.`ChooseWayView$$State`

@ExtendWith(MockKExtension::class)
class ChooseWayPresenterTest {

    @MockK
    lateinit var mockAuthRepository: AuthRepository

    @MockK
    lateinit var mockViewState: `ChooseWayView$$State`

    lateinit var presenter: ChooseWayPresenter

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @BeforeEach
    fun setUp() {
        presenter = spyk(ChooseWayPresenter(mockAuthRepository))
        presenter.setViewState(mockViewState)
    }

    @Test
    fun authWithGoogle() {
        coEvery { mockAuthRepository.authWithGoogle(any()) } returns true

//        как вот это протестить в authWithGoogle я хз
//        GoogleAuthProvider.getCredential
        presenter.authWithGoogle("qweqweq")

        verify {
            mockViewState.navigateToUserData()
        }
    }
}
