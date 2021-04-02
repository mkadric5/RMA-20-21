package ba.unsa.etf.rma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.data.Movie
import ba.unsa.etf.rma.viewmodel.MovieListViewModel

class MyAdapter(
    private val dataSet: List<Movie>,
    private val onItemClicked: (movie:Movie) -> Unit
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    /**
     *Klasa za pružanje referenci na sve elemente view-a
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleMovie: TextView
        val imageMovie: ImageView
        init {
            // Definisanje akcija na elemente
            titleMovie = view.findViewById(R.id.movieTitle)
            imageMovie = view.findViewById(R.id.movieImage)
        }
    }
    // Kreiraj novi view
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Kreiraj novi view koji definiše UI elementa liste
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_movie, viewGroup, false)
        return ViewHolder(view)
    }
    // Izmijeni sadržaj view-a
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //Pokupi element iz skupa podataka i zamijeni
        //sadržaj View sa odgovarajućim
        viewHolder.titleMovie.text = dataSet[position].title
        val movieGenre = dataSet[position].genre
        val context = viewHolder.imageMovie.context
        var id: Int = context.resources.getIdentifier(movieGenre,"drawable", context.packageName)
        if (id == 0) viewHolder.imageMovie.setImageResource(R.drawable.movie)
        else viewHolder.imageMovie.setImageResource(id)

        viewHolder.itemView.setOnClickListener{ onItemClicked(dataSet[position]) }
    }
    // Vrati veličinu skupa
    override fun getItemCount() = dataSet.size
}

class MainActivity : AppCompatActivity() {
    private lateinit var searchBtn: ImageButton
    private lateinit var searchFld: EditText
    private lateinit var favoriteMoviesView: RecyclerView
    private lateinit var recentMoviesView: RecyclerView
    private lateinit var favoriteMoviesAdapter: MyAdapter
    private lateinit var recentMoviesAdapter: MyAdapter
    private var movieListViewModel: MovieListViewModel = MovieListViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBtn = findViewById(R.id.searchBtn)
        searchFld = findViewById(R.id.searchFld)

        favoriteMoviesView = findViewById(R.id.favoriteMoviesList)
        recentMoviesView = findViewById(R.id.recentMoviesList)

        favoriteMoviesAdapter = MyAdapter(movieListViewModel.getFavoriteMovies()) { movie -> showMovieDetails(movie) }
        recentMoviesAdapter = MyAdapter(movieListViewModel.getRecentMovies()) {movie -> showMovieDetails(movie)}

        favoriteMoviesView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recentMoviesView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        favoriteMoviesView.adapter = favoriteMoviesAdapter
        recentMoviesView.adapter = recentMoviesAdapter

        favoriteMoviesAdapter.notifyDataSetChanged()
        recentMoviesAdapter.notifyDataSetChanged()

        if(intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain")
            handleSendText(intent)

        searchBtn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                //putExtra(Intent.EXTRA_TEXT, "textMessage")
                type = "text/plain"
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            searchFld.setText(it)
        }
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("movie_title",movie.title)
        }
        startActivity(intent)
    }
}


