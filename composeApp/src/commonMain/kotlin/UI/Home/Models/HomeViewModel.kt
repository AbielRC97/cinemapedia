package UI.Home.Models

import Models.Movie
import Models.MoviesService
import Models.RemoteMovie
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(
    private val moviesService: MoviesService
) :  ViewModel() {
    var state by mutableStateOf(UIState())
        private  set
    init {
        viewModelScope.launch {
            state = UIState(loading = true)
            state = UIState(loading = false,
                movies = moviesService.fetchPopularMovies().results.map { it.toDomainMovie() }
            )
        }
    }
    data class UIState (
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}

private  fun RemoteMovie.toDomainMovie() = Movie(
    id = id,
    title = title,
    poster = "https://image.tmdb.org/t/p/w500/$posterPath"
)