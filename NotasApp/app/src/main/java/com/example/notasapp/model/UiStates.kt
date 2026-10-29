package com.example.notasapp.model

import com.example.notasapp.model.response.NoteResponse

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val nombre: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class CreateNoteState {
    object Loading : CreateNoteState()
    data class Success(val message: String) : CreateNoteState()
    data class Error(val message: String) : CreateNoteState()
}

sealed class NoteListState {
    object Loading : NoteListState()
    data class Success(val notes: List<NoteResponse>) : NoteListState()
    data class Error(val message: String) : NoteListState()
}