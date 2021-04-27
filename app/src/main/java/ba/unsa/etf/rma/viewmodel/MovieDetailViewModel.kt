package ba.unsa.etf.rma.viewmodel

import ba.unsa.etf.rma.data.Movie
import ba.unsa.etf.rma.data.MovieRepository

class MovieDetailViewModel {
    fun getMovieByTitle(name: String): Movie {
        var movies:ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepository.getFavoriteMovies())
        movies.addAll(MovieRepository.getRecentMovies())

        val movie = movies.find{movie -> movie.title.equals(name)}
        return movie?:Movie(0,"Test","Test","Test","Test","Test")
    }

    fun getActorsByTitle(movieTitle: String): List<String> {
        return listOf("Leonardo DiCaprio","Keanu Reeves","Tom Cruise","Morgan Freeman","Natalie Portman","Hugo Weaving")
    }
}