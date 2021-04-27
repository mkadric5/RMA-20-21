package com.example.cinaeste

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.unsa.etf.rma.MovieDetailActivity
import ba.unsa.etf.rma.R
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntentInstrumentedTest {
    @get:Rule
    var intentsRule: IntentsTestRule<MovieDetailActivity> = IntentsTestRule(MovieDetailActivity::class.java,false,false)

    fun withImage(@DrawableRes id: Int) = object : TypeSafeMatcher<View>(){
        override fun describeTo(description: Description) {
            description.appendText("Drawable does not contain image with id: $id")
        }

        override fun matchesSafely(item: View): Boolean {
            val context:Context = item.context
            val bitmap: Bitmap? = context.getDrawable(id)?.toBitmap()
            return item is ImageView && item.drawable.toBitmap().sameAs(bitmap)
        }

    }

    @Test
    fun testDetailActivityInstantiation(){
        val pokreniDetalje: Intent = Intent(MovieDetailActivity::javaClass.name)
        pokreniDetalje.putExtra("movie_title","Matrix")
        intentsRule.launchActivity(pokreniDetalje)
        onView(withId(R.id.movie_title)).check(matches(withText("Matrix")))
        onView(withId(R.id.movie_genre)).check(matches(withText("action")))
        onView(withId(R.id.movie_overview)).check(matches(withSubstring("Keanu Reeves")))
        onView(withId(R.id.movie_poster)).check(matches(withImage(R.drawable.action)))

    }

    @Test
    fun testLinksIntent(){
        val pokreniDetalje: Intent = Intent(MovieDetailActivity::javaClass.name)
        pokreniDetalje.putExtra("movie_title","Matrix")
        intentsRule.launchActivity(pokreniDetalje)
        onView(withId(R.id.movie_website)).perform(click())
        intended(hasAction(Intent.ACTION_VIEW))
    }
}