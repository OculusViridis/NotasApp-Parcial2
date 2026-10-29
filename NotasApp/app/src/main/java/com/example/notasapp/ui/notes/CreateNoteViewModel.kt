package com.example.notasapp.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notasapp.data.NoteRepository
import com.example.notasapp.model.CreateNoteState
import kotlinx.coroutines.launch

class CreateNoteViewModel : ViewModel() {
    private val repository = NoteRepository()

    private val _createState = MutableLiveData<CreateNoteState>()
    val createState: LiveData<CreateNoteState> get() = _createState

    fun crearNota(titulo: String, contenido: String) {
        if (titulo.isBlank() || contenido.isBlank()) {
            _createState.value = CreateNoteState.Error("Completa el título y el contenido")
            return
        }

        _createState.value = CreateNoteState.Loading
        viewModelScope.launch {
            try {
                val response = repository.crearNota(titulo, contenido)
                if (response.isSuccessful && response.body()?.exito == true) {
                    _createState.value = CreateNoteState.Success("Nota guardada con éxito")
                } else {
                    _createState.value = CreateNoteState.Error("No se pudo guardar la nota")
                }
            } catch (e: Exception) {
                _createState.value = CreateNoteState.Error("Error de red: ${e.localizedMessage}")
            }
        }
    }
}