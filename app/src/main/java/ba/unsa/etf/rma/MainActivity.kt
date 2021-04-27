package ba.unsa.etf.rma

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.data.Movie
import com.google.android.material.bottomnavigation.BottomNavigationView

class MovieListAdapter(
    private var dataSet: List<Movie>,
    private val onItemClicked: (movie:Movie, view1: View, view2: View) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
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

        viewHolder.itemView.setOnClickListener{ onItemClicked(dataSet[position],viewHolder.imageMovie,viewHolder.titleMovie )}
    }
    // Vrati veličinu skupa
    override fun getItemCount() = dataSet.size

    fun updateMovies(movies: List<Movie>) {
        dataSet = movies
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_favorites -> {
                val favoritesFragment = FavoriteMoviesFragment.newInstance()
                openFragment(favoritesFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_recent -> {
                val recentFragments = RecentMoviesFragment.newInstance()
                openFragment(recentFragments)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                val searchFragment = SearchFragment.newInstance("")
                openFragment(searchFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

            sharedElementExitTransition = Fade()
            exitTransition = Fade()
        }

        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        bottomNavigation = findViewById(R.id.navigationBar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


        //Default fragment
        bottomNavigation.selectedItemId = R.id.navigation_favorites
        val favoritesFragment = FavoriteMoviesFragment.newInstance()
        openFragment(favoritesFragment)

//        if(intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain")
//            handleSendText(intent)
    }

//    private fun handleSendText(intent: Intent?) {
//        intent?.getStringExtra(Intent.EXTRA_TEXT)?.let {
//            bottomNavigation.selectedItemId = R.id.navigation_search
//            val searchFragment = SearchFragment.newInstance(it)
//            openFragment(searchFragment)
//        }
//    }

    //Funkcija za izmjenu fragmenta
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}


