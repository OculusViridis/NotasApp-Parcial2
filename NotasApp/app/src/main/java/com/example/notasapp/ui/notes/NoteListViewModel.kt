package com.example.notasapp.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notasapp.data.NoteRepository
import com.example.notasapp.model.NoteListState
import kotlinx.coroutines.launch

class NoteListViewModel : ViewModel() {
    private val repository = NoteRepository()

    private val _listState = MutableLiveData<NoteListState>()
    val listState: LiveData<NoteListState> get() = _listState

    fun cargarNotas() {
        _listState.value = NoteListState.Loading
        viewModelScope.launch {
            try {
                val response = repository.obtenerNotas()
                if (response.isSuccessful && response.body() != null) {
                    _listState.value = NoteListState.Success(response.body()!!)
                } else {
                    _listState.value = NoteListState.Error("Error al obtener las notas")
                }
            } catch (e: Exception) {
                _listState.value = NoteListState.Error("Error de conexión: ${e.localizedMessage}")
            }
        }
    }
}