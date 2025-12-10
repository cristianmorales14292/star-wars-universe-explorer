package ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import data.remote.Person
import  data.repository.SwRepository



class PeopleViewModel(private val repo: SwRepository) : ViewModel() {
    private val _people = MutableStateFlow<UiState<List<Person>>>(UiState.Loading)
    val people: StateFlow<UiState<List<Person>>> = _people

    init { loadPeople() }

    fun loadPeople(page: Int = 1) {
        viewModelScope.launch {
            _people.value = UiState.Loading
            try {
                val resp = repo.getPeople(page)
                _people.value = UiState.Success(resp.results)
            } catch (e: Exception) {
                _people.value = UiState.Error(e.localizedMessage ?: "Error desconocido")
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            _people.value = UiState.Loading
            try {
                val resp = repo.searchPeople(query)
                _people.value = UiState.Success(resp.results)
            } catch (e: Exception) {
                _people.value = UiState.Error(e.localizedMessage ?: "Error")
            }
        }
    }
}

