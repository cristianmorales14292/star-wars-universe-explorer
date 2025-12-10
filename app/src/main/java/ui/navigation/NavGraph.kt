package ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import ui.viewmodel.PeopleViewModel
import ui.screens.PeopleListScreen
import androidx.navigation.navArgument
import  androidx.compose.material.Scaffold
import  androidx.compose.material.TopAppBar
import  androidx.compose.material.Text
import  androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp


@Composable
fun AppNavHost(viewModel: PeopleViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "people_list") {
        composable("people_list") {
            PeopleListScreen(viewModel = viewModel, onPersonClick = { id ->
                navController.navigate("person_detail/$id")
            })
        }
        composable("person_detail/{personId}", arguments = listOf(navArgument("personId") {
            type = NavType.IntType
        })) { backStack ->
            val id = backStack.arguments?.getInt("personId") ?: 0
            // Aquí mostrarías PersonDetailScreen(personId = id)
            // Por simplicidad ahora solo mostramos Scaffold con id
            Scaffold(topBar = { TopAppBar(title = { Text("Detalle") }) }) {
                Text("Mostrar detalle del personaje con id = $id", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
