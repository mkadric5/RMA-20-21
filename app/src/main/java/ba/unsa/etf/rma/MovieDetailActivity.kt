package ba.unsa.etf.rma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.unsa.etf.rma.data.Movie
import ba.unsa.etf.rma.view.AdditionalInfoFragment
import ba.unsa.etf.rma.viewmodel.MovieDetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class MovieDetailActivity : AppCompatActivity() {
    private var movieDetailViewModel = MovieDetailViewModel(this@MovieDetailActivity::populateDetails,null)
    private var movieId: Long = 0
    private lateinit var title: TextView
    private lateinit var overview: TextView
    private lateinit var date: TextView
    private lateinit var genre : TextView
    private lateinit var website : TextView
    private lateinit var poster : ImageView
    private lateinit var backDrop: ImageView
    private lateinit var navBar: BottomNavigationView
    private val posterPath = "https://image.tmdb.org/t/p/w342"
    private val backdropPath = "https://image.tmdb.org/t/p/w500"

//    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.navigation_actors -> {
//                val actorsFragment = ActorsFragment.newInstance(movieId)
//                openFragment(actorsFragment,"actors")
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_similar_movies -> {
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        date = findViewById(R.id.movie_release_date)
        genre = findViewById(R.id.movie_genre)
        website = findViewById(R.id.movie_website)
        poster = findViewById(R.id.movie_poster)
        backDrop = findViewById(R.id.movie_backdrop)
        navBar = findViewById(R.id.navigation_detail)

        val extras = intent.extras

        if (extras != null) {
            movieDetailViewModel.getMovieDetails(extras.getLong("movie_id",0L))
            movieId = extras.getLong("movie_id",0L)
        }
        else {
            finish()
        }

        navBar.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.navigation_actors -> {
                    val actorsFragment = AdditionalInfoFragment.newInstance(movieId)
                    openFragment(actorsFragment,"actors")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_similar_movies -> {
                    val actorsFragment = AdditionalInfoFragment.newInstance(movieId)
                    openFragment(actorsFragment,"similar movies")
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

//        glumciListaAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,
//            listOf("Leonardo DiCaprio","Keanu Reeves","Tom Cruise","Morgan Freeman","Natalie Portman","Hugo Weaving"))
//        glumciLista.adapter = glumciListaAdapter

        website.setOnClickListener {
            showWebsite()
        }

        title.setOnClickListener {
            openVideoTrailer()
        }
    }

    private fun openFragment(fragment: Fragment,tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        val naStacku = supportFragmentManager.findFragmentByTag(tag)
        if (naStacku != null)
            transaction.replace(R.id.frameDetail,naStacku,tag)
        else {
            transaction.replace(R.id.frameDetail,fragment,tag)
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }

    private fun openVideoTrailer() {
//        val sendIntent = Intent().apply {
//            action = Intent.ACTION_SEARCH
//            setPackage("com.google.android.youtube")
//            putExtra("query", movie.title + " trailer")
//        }
//        if (sendIntent.resolveActivity(packageManager) != null) {
//            startActivity(sendIntent)
//        }
    }

    private fun showWebsite() {
//        val sendIntent = Intent().apply {
//            action = Intent.ACTION_VIEW
//            data = Uri.parse(movie.homepage)
//        }
//        if (sendIntent.resolveActivity(packageManager) != null) {
//            startActivity(sendIntent)
//        }
    }

    private fun populateDetails(movie: Movie) {
        title.text = movie.title
        overview.text = movie.overview
        date.text = movie.releaseDate
        genre.text = movie.genre
        website.text = movie.homepage

        val posterContext = poster.context
        var id: Int = posterContext.resources.getIdentifier(movie.genre, "drawable", posterContext.packageName)
        if (id == 0) id = posterContext.resources
            .getIdentifier("movie", "drawable", posterContext.packageName)
        poster.setImageResource(id)
        Glide.with(posterContext)
            .load(posterPath + movie.posterPath)
            .centerCrop()
            .placeholder(R.drawable.movie)
            .error(id)
            .fallback(id)
            .into(poster)

        val backdropContext = backDrop.context
        Glide.with(backdropContext)
            .load(backdropPath + movie.backdropPath)
            .centerCrop()
            .placeholder(R.drawable.backdrop)
            .error(R.drawable.backdrop)
            .fallback(R.drawable.backdrop)
            .into(backDrop)
    }
}