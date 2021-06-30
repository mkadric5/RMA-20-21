package ba.unsa.etf.rma.viewmodel

import android.content.Context
import ba.unsa.etf.rma.data.GetMoviesResponse
import ba.unsa.etf.rma.data.Movie
import ba.unsa.etf.rma.data.MovieRepository
import ba.unsa.etf.rma.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieListViewModel(private val searchDone: ((movies: List<Movie>) -> Unit)?,
                         private val onError: (()->Unit)?, context: Context) {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    val favoriteMovies = MovieRepository.getFavorites(context)

//    fun search(query: String){
//        // Kreira se Coroutine na UI
//        scope.launch{
//            // Vrši se poziv servisa i suspendira se rutina dok se `withContext` ne završi
//            val result = MovieRepository.searchRequest(query)
//            // Prikaže se rezultat korisniku na glavnoj niti
//            when (result) {
//                is Result.Success<List<Movie>> -> searchDone?.invoke(result.data)
//                else -> onError?.invoke()
//            }
//        }
//    }

    fun search(query: String) {
        // Kreira se Coroutine na UI
        scope.launch {
            // Vrši se poziv servisa i suspendira se rutina dok se `withContext` ne završi
            val result = MovieRepository.searchRequest(query)
            // Prikaže se rezultat korisniku na glavnoj niti
            when (result) {
                is GetMoviesResponse -> searchDone?.invoke(result.movies)
                else -> onError?.invoke()
            }
        }
    }


    fun getFavorites(context: Context, onSuccess: (movies: List<Movie>) -> Unit,
                     onError: () -> Unit){
        scope.launch{
            val result = MovieRepository.getFavoriteMovies(context)
            when (result) {
                is List<Movie> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

    fun getUpcoming( onSuccess: (movies: List<Movie>) -> Unit,
                     onError: () -> Unit){
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = MovieRepository.getUpcomingMovies()

            // Display result of the network request to the user
            when (result) {
                is GetMoviesResponse -> onSuccess?.invoke(result.movies)
                else-> onError?.invoke()
            }
        }
    }
}