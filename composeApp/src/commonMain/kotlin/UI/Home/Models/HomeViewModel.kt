package UI.Home.Models

import Models.Movie
import Models.movies
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel :  ViewModel() {
    var state by mutableStateOf(UIState())
        private  set
    init {
        viewModelScope.launch {
            state = UIState(loading = true)
            delay(5000)
            state = UIState(loading = false, movies = movies)
        }
    }
    data class UIState (
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}
