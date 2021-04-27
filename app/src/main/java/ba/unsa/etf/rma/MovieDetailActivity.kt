package ba.unsa.etf.rma

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.data.Movie
import ba.unsa.etf.rma.viewmodel.MovieDetailViewModel
import org.w3c.dom.Text

class MovieDetailActivity : AppCompatActivity() {
    private var movieDetailViewModel = MovieDetailViewModel()
    private lateinit var movie: Movie
    private lateinit var title: TextView
    private lateinit var overview: TextView
    private lateinit var date: TextView
    private lateinit var genre : TextView
    private lateinit var website : TextView
    private lateinit var poster : ImageView
    private lateinit var glumciLista: ListView
    private lateinit var glumciListaAdapter: ArrayAdapter<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        date = findViewById(R.id.movie_release_date)
        genre = findViewById(R.id.movie_genre)
        website = findViewById(R.id.movie_website)
        poster = findViewById(R.id.movie_poster)
        glumciLista = findViewById(R.id.glumciLista)

        val extras = intent.extras

        if (extras != null) {
            movie = movieDetailViewModel.getMovieByTitle(extras.getString("movie_title",""))
            populateDetails(movie)
        }
        else {
            finish()
        }

        glumciListaAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,
            listOf("Leonardo DiCaprio","Keanu Reeves","Tom Cruise","Morgan Freeman","Natalie Portman","Hugo Weaving"))
        glumciLista.adapter = glumciListaAdapter

        website.setOnClickListener {
            showWebsite()
        }

        title.setOnClickListener {
            openVideoTrailer()
        }
    }

    private fun openVideoTrailer() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEARCH
            setPackage("com.google.android.youtube")
            putExtra("query", movie.title + " trailer")
        }
        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(sendIntent)
        }
    }

    private fun showWebsite() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(movie.homepage)
        }
        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(sendIntent)
        }
    }

    private fun populateDetails(movie: Movie) {
        title.text = movie.title
        overview.text = movie.overview
        date.text = movie.releaseDate
        genre.text = movie.genre
        website.text = movie.homepage

        val context = poster.context
        var id: Int = context.resources.getIdentifier(movie.genre, "drawable", context.packageName)
        if (id == 0) id=context.resources
            .getIdentifier("movie", "drawable", context.packageName)
        poster.setImageResource(id)
    }
}