package ba.unsa.etf.rma.data

import ba.unsa.etf.rma.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<GetMoviesResponse>


    @GET("search/movie")
    suspend fun getSearchResults(
        @Query("api_key") apiKey: String,
        @Query("query") title: String
    ): Response<GetMoviesResponse>


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): Response<Movie>


    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): Response<GetMoviesResponse>


    @GET("movie/{movie_id}/credits")
    suspend fun getMovieActors(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): Response<GetCreditsResponse>
}