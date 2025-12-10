package ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.remote.Person
import ui.viewmodel.PeopleViewModel
import ui.viewmodel.UiState


@Composable
fun PeopleListScreen(viewModel: PeopleViewModel, onPersonClick: (Int) -> Unit) {
    val state by viewModel.people.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(onSearch = { viewModel.search(it) })

        when (state) {
            is UiState.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            is UiState.Error -> {
                val message = (state as UiState.Error).message
                Text(text = "Error: $message", modifier = Modifier.padding(16.dp))
            }
            is UiState.Success<*> -> {
                val list = (state as UiState.Success<List<Person>>).data
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(list) { person ->
                        PersonRow(person = person, onClick = {
                            val id = person.url.trimEnd('/').split("/").last().toIntOrNull() ?: 0
                            onPersonClick(id)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun PersonRow(person: Person, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() }) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = person.name, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Gender: ${person.gender} • Birth: ${person.birth_year}", style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Buscar personaje...") }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onSearch(text) }) {
            Text("Buscar")
        }
    }
}
