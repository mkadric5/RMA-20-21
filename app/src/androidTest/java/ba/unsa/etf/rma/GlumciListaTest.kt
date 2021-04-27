package ba.unsa.etf.rma

import ba.unsa.etf.rma.viewmodel.MovieDetailViewModel
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GlumciListaTest {
    @get:Rule
    var intentsRule: IntentsTestRule<MainActivity> =
        IntentsTestRule(MainActivity::class.java, false, false)

    @get:Rule
    var detailRule: IntentsTestRule<MovieDetailActivity> =
        IntentsTestRule(MovieDetailActivity::class.java, false, false)

    //Zadatak 1
    @Test
    fun testActors() {
        val pokreniDetalje: Intent = Intent(MovieDetailActivity::javaClass.name)
        pokreniDetalje.putExtra("movie_title", "Pulp Fiction")
        detailRule.launchActivity(pokreniDetalje)
        val actors = MovieDetailViewModel().getActorsByTitle("Pulp Fiction")
        for (actor in actors)
            onView(withId(R.id.glumciLista)).check(matches(isDisplayed()))
        //Zadatak 2
        @Test
        fun testSEND() {
            var intent: Intent = Intent()
            intent.putExtra(Intent.EXTRA_TEXT, "Neki tekst")
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intentsRule.launchActivity(intent)
            onView(withId(R.id.searchText)).check(matches(withText("Neki tekst")))
        }
    }
}