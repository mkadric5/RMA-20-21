package ba.unsa.etf.rma.data

import android.content.Context
import androidx.lifecycle.LiveData
import ba.unsa.etf.rma.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

object MovieRepository {
    private const val tmdb_api_key = BuildConfig.TMDB_API_KEY

    fun getFavorites(context: Context) : LiveData<List<Movie>> {
        var db = AppDatabase.getInstance(context)
        var movies = db!!.movieDao().getAll()
        return movies
    }
    suspend fun writeFavorite(context: Context,movie:Movie) : String?{
        return withContext(Dispatchers.IO) {
            try{
                var db = AppDatabase.getInstance(context)
                db!!.movieDao().insertAll(movie)
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    suspend fun getUpcomingMovies(
    ) : GetMoviesResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getUpcomingMovies()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun searchRequest(query: String): GetMoviesResponse? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getSearchResults(BuildConfig.TMDB_API_KEY,query)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getMovieDetails(id: Long): Movie? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getMovieDetails(id,BuildConfig.TMDB_API_KEY)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getSimilarMovies(id: Long): GetMoviesResponse? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getSimilarMovies(id,BuildConfig.TMDB_API_KEY)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getMovieActors(id: Long): GetCreditsResponse? {
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getMovieActors(id,BuildConfig.TMDB_API_KEY)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
}