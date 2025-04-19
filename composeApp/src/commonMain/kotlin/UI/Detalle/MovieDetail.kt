package UI.Detalle

import Models.Movie
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun MovieDetail(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = movie.backdrop ?: movie.poster,
            contentDescription =  movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().aspectRatio(16f/9f)
        )
        Text(
            text = movie.overview,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = buildAnnotatedString {
                property("Original language", movie.originalLanguage)
                property("Original Title", movie.originalTitle)
                property("Popularity", movie.popularity.toString())
                property("Release Date", movie.releaseDate)
                property("Vote Average", movie.voteAverage.toString(), true)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
        )
    }
}

private fun AnnotatedString.Builder.property(name:String, value: String, end: Boolean = false) {
    withStyle( ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if(!end) {
            append("\n")
        }
    }
}