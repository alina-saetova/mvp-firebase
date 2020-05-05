package ru.itis.mvp_firebase.di.module

import dagger.Binds
import dagger.Module
import ru.itis.mvp_firebase.data.repository.AuthRepository
import ru.itis.mvp_firebase.data.repository.AuthRepositoryImpl
import ru.itis.mvp_firebase.data.repository.NoteRepository
import ru.itis.mvp_firebase.data.repository.NoteRepositoryImpl
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository

    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}
