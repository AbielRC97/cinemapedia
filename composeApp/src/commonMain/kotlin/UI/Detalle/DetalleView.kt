package UI.Detalle

import UI.Home.Models.DetailsViewModel
import UI.Shared.LoadingIndicator
import UI.Shared.ScreenView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleView(vm: DetailsViewModel, onBack: ()-> Unit) {
    val state =  vm.state
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    ScreenView {
        Scaffold(topBar =  {
            TopAppBarDetail(
                title = state.movie?.title ?: "",
                onBack = onBack,
                scrollBehavior = scrollBehavior
            )
        }) { padding ->
            LoadingIndicator(enabled =  state.loading, modifier =  Modifier.padding(padding))
            state.movie?.let { movie ->
                MovieDetail(movie = movie, modifier =  Modifier.padding(padding))
            }
        }
    }
}