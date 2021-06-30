package ba.unsa.etf.rma.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.MovieDetailActivity
import ba.unsa.etf.rma.R
import ba.unsa.etf.rma.data.Movie
import ba.unsa.etf.rma.viewmodel.MovieListViewModel
import android.util.Pair as UtilPair

class FavoriteMoviesFragment: Fragment() {
    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieListAdapter
    private var movieListViewModel = MovieListViewModel(null,null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { movieListViewModel= MovieListViewModel(it) }
        val moviesObserver = Observer<List<Movie>> { movies ->
            favoriteMoviesAdapter.updateMovies(movies)
        }
        movieListViewModel.favoriteMovies.observe(this, moviesObserver)
    }

    fun onSuccess(movies:List<Movie>){
        favoriteMoviesAdapter.updateMovies(movies)
    }
    fun onError() {
        val toast = Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
        toast.show()
    }
    

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.favorites_fragment, container, false)
        favoriteMovies = view.findViewById(R.id.recentMovies)
        favoriteMovies.layoutManager = GridLayoutManager(activity, 2)
        favoriteMoviesAdapter = MovieListAdapter(arrayListOf()) { movie,view1,view2 -> showMovieDetails(movie,view1,view2) }
        favoriteMovies.adapter = favoriteMoviesAdapter
//        favoriteMoviesAdapter.updateMovies(movieListViewModel.getFavoriteMovies())
        return view
    }

    companion object {
        fun newInstance(): FavoriteMoviesFragment = FavoriteMoviesFragment()
    }

    private fun showMovieDetails(movie: Movie, view1: View, view2: View) {
        val intent = Intent(activity, MovieDetailActivity::class.java).apply {
            putExtra("movie_id", movie.id)
        }

        val options = ActivityOptions.makeSceneTransitionAnimation(activity, UtilPair.create(view1,"poster"),
            UtilPair.create(view2,"title"))
        startActivity(intent, options.toBundle())
    }
}