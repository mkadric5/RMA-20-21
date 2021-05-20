package ba.unsa.etf.rma.data

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

    fun getFavoriteMovies(): List<Movie> {
        return favoriteMovies()
    }

    fun getRecentMovies(): List<Movie> {
        return recentMovies()
    }

    suspend fun searchRequest(
        query: String
    ): Result<List<Movie>>{
        return withContext(Dispatchers.IO) {
            try {
                val movies = arrayListOf<Movie>()
                val url1 = "https://api.themoviedb.org/3/search/movie?api_key=$tmdb_api_key&query=$query" //1
                val url = URL(url1) //2
                (url.openConnection() as? HttpURLConnection)?.run { //3
                    val result = this.inputStream.bufferedReader().use { it.readText() } //4
                    val jo = JSONObject(result)//5
                    val results = jo.getJSONArray("results")//6
                    for (i in 0 until results.length()) {//7
                        val movie = results.getJSONObject(i)
                        val title = movie.getString("title")
                        val id = movie.getInt("id")
                        val posterPath = movie.getString("poster_path")
                        val overview = movie.getString("overview")
                        val releaseDate = movie.getString("release_date")
                        movies.add(Movie(id.toLong(), title, overview, releaseDate, null, null, posterPath,null))
                        if (i == 5) break
                    }
                }
                return@withContext Result.Success(movies)//8
            }
            catch (e: MalformedURLException) {
                return@withContext Result.Error(Exception("Cannot open HttpURLConnection"))
            } catch (e: IOException) {
                return@withContext Result.Error(Exception("Cannot read stream"))
            } catch (e: JSONException) {
                return@withContext Result.Error(Exception("Cannot parse JSON"))
            }
        }
    }

    suspend fun movieDetailsRequest(id: Long): Result<Movie> {
        return withContext(Dispatchers.IO) {
            try {
                val url1 = "https://api.themoviedb.org/3/movie/$id?api_key=$tmdb_api_key" //1
                val url = URL(url1)
                var movie = Movie(0,"test","test","test","test","test","test","test")
                (url.openConnection() as? HttpURLConnection)?.run { //3
                    val result = this.inputStream.bufferedReader().use { it.readText() } //4
                    val jo = JSONObject(result)//5
                    movie.title = jo.getString("title")
                    movie.id = jo.getLong("id")
                    movie.genre = jo.getJSONArray("genres").getJSONObject(0).getString("name")
                    movie.posterPath = jo.getString("poster_path")
                    movie.backdropPath = jo.getString("backdrop_path")
                    movie.overview = jo.getString("overview")
                    movie.releaseDate = jo.getString("release_date")
                    movie.homepage = jo.getString("homepage")
                }
                return@withContext Result.Success(movie)
            }
            catch (e: MalformedURLException) {
                return@withContext Result.Error(Exception("Cannot open HttpURLConnection"))
            } catch (e: IOException) {
                return@withContext Result.Error(Exception("Cannot read stream"))
            } catch (e: JSONException) {
                return@withContext Result.Error(Exception("Cannot parse JSON"))
            }
        }
    }

    suspend fun movieActors(id: Long): Result<List<String>> {
        return withContext(Dispatchers.IO) {
            try {
                val url1 = "https://api.themoviedb.org/3/movie/$id/credits?api_key=$tmdb_api_key" //1
                val url = URL(url1)
                var actors = mutableListOf<String>()
                (url.openConnection() as? HttpURLConnection)?.run { //3
                    val result = this.inputStream.bufferedReader().use { it.readText() } //4
                    val jo = JSONObject(result)//5
                    val results = jo.getJSONArray("cast")
                    for (i in 0 until results.length()) {
                        val actor = results.getJSONObject(i)
                        actors.add(actor.getString("name"))
                    }
                }
                return@withContext Result.Success(actors)
            }
            catch (e: MalformedURLException) {
                return@withContext Result.Error(Exception("Cannot open HttpURLConnection"))
            } catch (e: IOException) {
                return@withContext Result.Error(Exception("Cannot read stream"))
            } catch (e: JSONException) {
                return@withContext Result.Error(Exception("Cannot parse JSON"))
            }
        }
    }

    suspend fun similarMovies(id: Long): Result<List<String>> {
        return withContext(Dispatchers.IO) {
            try {
                val url1 = "https://api.themoviedb.org/3/movie/$id/similar?api_key=$tmdb_api_key" //1
                val url = URL(url1)
                var titles = mutableListOf<String>()
                (url.openConnection() as? HttpURLConnection)?.run { //3
                    val result = this.inputStream.bufferedReader().use { it.readText() } //4
                    val jo = JSONObject(result)//5
                    val results = jo.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val movie = results.getJSONObject(i)
                        titles.add(movie.getString("title"))
                    }
                }
                return@withContext Result.Success(titles)
            }
            catch (e: MalformedURLException) {
                return@withContext Result.Error(Exception("Cannot open HttpURLConnection"))
            } catch (e: IOException) {
                return@withContext Result.Error(Exception("Cannot read stream"))
            } catch (e: JSONException) {
                return@withContext Result.Error(Exception("Cannot parse JSON"))
            }
        }
    }

    suspend fun getUpcomingMovies(
    ) : GetMoviesResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getUpcomingMovies()
            return@withContext response.body()
        }
    }
}