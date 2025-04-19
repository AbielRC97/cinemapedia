package UI.Shared

import Models.MoviesService
import Models.movies
import UI.Detalle.DetalleView
import UI.Home.HomeView
import UI.Home.Models.HomeViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cinemapedia.composeapp.generated.resources.API_KEY
import cinemapedia.composeapp.generated.resources.Res
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val client = remember {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
    val apiKey = stringResource(Res.string.API_KEY)
    val viewModel = viewModel {
        HomeViewModel(MoviesService(apiKey, client))
    }
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeView(
                onMovieClick = { movie ->
                    navController.navigate("details/${ movie.id}")
                },
                vm = viewModel
            )
        }
        composable(
            "details/{movieId}",
            arguments = listOf(navArgument("movieId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            DetalleView(
                movie = movies.first {  it.id == movieId },
                onBack = { navController.popBackStack() }
            )
        }
    }
}