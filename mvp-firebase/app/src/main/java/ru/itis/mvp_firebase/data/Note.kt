package ru.itis.mvp_firebase.data

data class Note(
    val id: String = "",
    val userId: String = "",
    var title: String = "",
    var content: String = ""
)
