package UI.Home.Repository

import Models.Movie
import Models.MoviesService
import Models.RemoteMovie
import Models.RemoteResult

class MoviesRepository(private val moviesService: MoviesService) {
    suspend fun fetchPopularMovies(): List<Movie> {
        return moviesService.fetchPopularMovies().results.map { it.toDomainMovie() }
    }

    suspend fun fetchMovieById(id:Int): Movie {
        return moviesService.fetchMovieById(id).toDomainMovie()
    }

    private  fun RemoteMovie.toDomainMovie() = Movie(
        id = id,
        title = title,
        poster = "https://image.tmdb.org/t/p/w185/$posterPath",
        backdrop = backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" },
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        popularity = popularity,
        voteAverage = voteAverage,
        overview = overview,
        releaseDate = releaseDate
    )
}