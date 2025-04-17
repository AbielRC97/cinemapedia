package UI.Shared

import Models.movies
import UI.Detalle.DetalleView
import UI.Home.HomeView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeView(
                onMovieClick = { movie ->
                    navController.navigate("details/${ movie.id}")
                }
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