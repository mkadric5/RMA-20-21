package ba.unsa.etf.rma.viewmodel

import ba.unsa.etf.rma.data.Movie
import ba.unsa.etf.rma.data.MovieRepository

class MovieListViewModel {
    fun getFavoriteMovies(): List<Movie> {
        return MovieRepository.getFavoriteMovies()
    }

    fun getRecentMovies(): List<Movie> {
        return MovieRepository.getRecentMovies()
    }
}