package ba.unsa.etf.rma.viewmodel

import android.util.Log
import ba.unsa.etf.rma.data.Movie
import ba.unsa.etf.rma.data.MovieRepository
import ba.unsa.etf.rma.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieRetrieved: ((movie: Movie) -> Unit)?,
                           private val actorsRetrieved: ((actors: List<String>) -> Unit)?) {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getMovieByTitle(name: String): Movie {
        val movies:ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepository.getFavoriteMovies())
        movies.addAll(MovieRepository.getRecentMovies())

        val movie = movies.find{movie -> movie.title == name }
        return movie?:Movie(0,"Test","Test","Test","Test","Test",null,null)
    }

    fun getMovieDetails(id: Long) {
        val movies:ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepository.getFavoriteMovies())
        movies.addAll(MovieRepository.getRecentMovies())

        val movie = movies.find{movie -> movie.id == id}
        if (movie != null)
            movieRetrieved?.invoke(movie)
        else {
            scope.launch{
                // Vrši se poziv servisa i suspendira se rutina dok se `withContext` ne završi
                val result = MovieRepository.movieDetailsRequest(id)
                // Prikaže se rezultat korisniku na glavnoj niti
                when (result) {
                    is Result.Success<Movie> -> movieRetrieved?.invoke(result.data)
                    else -> Log.v("meh","meh")
                }
            }
        }
    }

    fun getActorsById(id: Long) {
        scope.launch{
            // Vrši se poziv servisa i suspendira se rutina dok se `withContext` ne završi
            val result = MovieRepository.movieActors(id)
            // Prikaže se rezultat korisniku na glavnoj niti
            when (result) {
                is Result.Success<List<String>> -> actorsRetrieved?.invoke(result.data)
                else -> println(id)
            }
        }
    }

    fun getSimilarMovies(id: Long) {
        scope.launch{
            // Vrši se poziv servisa i suspendira se rutina dok se `withContext` ne završi
            val result = MovieRepository.similarMovies(id)
            // Prikaže se rezultat korisniku na glavnoj niti
            when (result) {
                is Result.Success<List<String>> -> actorsRetrieved?.invoke(result.data)
                else -> Log.v("meh","meh")
            }
        }
    }

    fun getActorsByTitle(movieTitle: String): List<String> {
        return listOf("Leonardo DiCaprio","Keanu Reeves","Tom Cruise","Morgan Freeman","Natalie Portman","Hugo Weaving")
    }

    fun getMovieById(id: Long): Movie {
        var movie = Movie(0,"test","test","test","test","test","test","test")
        scope.launch{
            // Vrši se poziv servisa i suspendira se rutina dok se `withContext` ne završi
            // Prikaže se rezultat korisniku na glavnoj niti
            val result = MovieRepository.movieDetailsRequest(id)
            if (result is Result.Success<Movie>)
                movie = result.data
        }
        return movie
    }
}