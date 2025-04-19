package UI.Detalle

import UI.Home.Models.DetailsViewModel
import UI.Shared.LoadingIndicator
import UI.Shared.ScreenView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleView(vm: DetailsViewModel, onBack: ()-> Unit) {
    val state =  vm.state
    ScreenView {
        Scaffold(topBar =  {
            TopAppBar(
                title = { Text( state.movie?.title ?: "" ) },
                navigationIcon = {
                    IconButton(onClick = onBack ){
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                }
            )
        }) { padding ->
            LoadingIndicator(enabled =  state.loading)
            state.movie?.let { movie ->
                Column(
                    modifier = Modifier.padding(padding).verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = movie.poster,
                        contentDescription =  movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().aspectRatio(16f/9f)
                    )
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 1,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}