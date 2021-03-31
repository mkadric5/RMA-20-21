package ba.unsa.etf.rma.data

object MovieRepository {
    fun getFavoriteMovies(): List<Movie> {
        return favoriteMovies()
    }

    fun getRecentMovies(): List<Movie> {
        return recentMovies()
    }
}