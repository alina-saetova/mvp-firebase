package ru.itis.mvp_firebase.presenter

import com.google.firebase.auth.FirebaseUser
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import ru.itis.mvp_firebase.CoroutinesTestExtension
import ru.itis.mvp_firebase.data.repository.AuthRepository
import ru.itis.mvp_firebase.data.repository.NoteRepository
import ru.itis.mvp_firebase.ui.view.UserDataView
import ru.itis.mvp_firebase.ui.view.`UserDataView$$State`

@ExtendWith(MockKExtension::class)
class UserDataPresenterTest {

    @MockK
    lateinit var mockAuthRepository: AuthRepository

    @MockK
    lateinit var mockNoteRepository: NoteRepository

    @MockK
    lateinit var mockUser: FirebaseUser

    @MockK
    lateinit var mockViewState: `UserDataView$$State`

    lateinit var presenter: UserDataPresenter

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @BeforeEach
    fun setUp() {
        every { mockAuthRepository.getCurrentUser() } returns mockUser
        presenter = spyk(UserDataPresenter(mockAuthRepository, mockNoteRepository))
        presenter.setViewState(mockViewState)
    }

    @Test
    fun onFirstViewAttachWhenUserNotNull() {
        val mockView = mockk<UserDataView>()

        every { mockViewState.attachView(mockView) } just Runs
        every { mockNoteRepository.addListener(any()) } just Runs

        presenter.attachView(mockView)
        verify(exactly = 0) {
            mockViewState.navigateUp()
        }
    }

    @Test
    fun signOut() {
        every { mockAuthRepository.signOut() } just Runs
        every { mockViewState.navigateUp() } just Runs

        presenter.signOut()

        verify {
            mockAuthRepository.signOut()
            mockViewState.navigateUp()
        }
    }
}
