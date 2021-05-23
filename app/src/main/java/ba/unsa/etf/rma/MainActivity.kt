package ba.unsa.etf.rma

import android.content.Intent
import android.os.Build
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
import ba.unsa.etf.rma.view.FavoriteMoviesFragment
import ba.unsa.etf.rma.view.RecentMoviesFragment
import ba.unsa.etf.rma.view.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_favorites -> {
                val favoritesFragment = FavoriteMoviesFragment.newInstance()
                openFragment(favoritesFragment, "favorites")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_recent -> {
                val recentFragments = RecentMoviesFragment.newInstance()
                openFragment(recentFragments,"recent")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                val searchFragment = SearchFragment.newInstance("")
                openFragment(searchFragment,"search")
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
        openFragment(favoritesFragment,"favorites")

        Intent(this, LatestMovieService::class.java).also {
        //Različito pokretanje u ovisnosti od verzije
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(it)
                return
            }
            startService(it)
        }

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
    private fun openFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        val naStacku = supportFragmentManager.findFragmentByTag(tag)
        if (naStacku != null) {
            transaction.replace(R.id.container, naStacku)
            transaction.addToBackStack(tag)
        }
        else transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}


