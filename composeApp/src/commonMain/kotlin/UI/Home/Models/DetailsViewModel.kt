package UI.Home.Models

import Models.Movie
import UI.Home.Repository.MoviesRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailsViewModel(private  val id: Int,
     private val repository: MoviesRepository) : ViewModel() {

    var state by mutableStateOf(UIState())
        private  set

    init {
        viewModelScope.launch {
            state = UIState(loading = true)
            state =  UIState(
                loading = false,
                movie = repository.fetchMovieById(id)
            )
        }
    }

    data class UIState(
        val loading: Boolean = false,
        val movie: Movie? = null
    )
}
