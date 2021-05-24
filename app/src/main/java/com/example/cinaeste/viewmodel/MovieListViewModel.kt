package com.example.cinaeste.viewmodel

import android.util.Log
import com.example.cinaeste.data.Movie
import com.example.cinaeste.data.MovieRepository
import com.example.cinaeste.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction0

class MovieListViewModel(private val searchDone: ((movies: List<Movie>) -> Unit)?,
                         private val onError: (()->Unit)?
) {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getFavoriteMovies():List<Movie>{
        return MovieRepository.getFavoriteMovies();
    }

    fun getRecentMovies():List<Movie>{
        return MovieRepository.getRecentMovies();
    }

    fun search(query: String){

        // Create a new coroutine on the UI thread
        scope.launch{

            // Make the network call and suspend execution until it finishes
            val result = MovieRepository.searchRequest(query)

            // Display result of the network request to the user
            when (result) {
                is Result.Success<List<Movie>> -> searchDone?.invoke(result.data)
                else-> onError?.invoke()
            }
        }
    }
}