package UI.Shared

import Models.MoviesService
import UI.Detalle.DetalleView
import UI.Home.HomeView
import UI.Home.Models.DetailsViewModel
import UI.Home.Models.HomeViewModel
import UI.Home.Repository.MoviesRepository
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
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val repository  = RememberMoviesRepository()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeView(
                onMovieClick = { movie ->
                    navController.navigate("details/${ movie.id}")
                },
                vm = viewModel { HomeViewModel(repository) }
            )
        }
        composable(
            "details/{movieId}",
            arguments = listOf(navArgument("movieId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetalleView(
                vm = viewModel { DetailsViewModel(movieId, repository) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun RememberMoviesRepository(
    apiKey: String = stringResource(Res.string.API_KEY)
):MoviesRepository = remember {
    val client =
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                }
                )
            }
            install(DefaultRequest) {
                url {
                    protocol  = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    parameters.append("api_key", apiKey)
                }
            }
        }

    MoviesRepository(MoviesService(client))
}