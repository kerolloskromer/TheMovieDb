package com.kromer.themoviedb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kromer.themoviedb.data.source.local.MoviesDao
import com.kromer.themoviedb.data.source.local.MyDatabase
import com.kromer.themoviedb.domain.model.Movie
import com.kromer.themoviedb.presentation.ui.main.MainActivity
import com.kromer.themoviedb.presentation.ui.main.list.MovieViewHolder
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var dao: MoviesDao
    private lateinit var db: MyDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MyDatabase::class.java).build()
        dao = db.getMoviesDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertMoviesAndReadResult() {
        val movie1 = Movie("1", 1.1, 1.1, "movie 1", "movie 1 overview", "2020-11-03", "url")
        val movie2 = Movie("2", 1.1, 1.1, "movie 2", "movie 2 overview", "2020-11-04", "url")
        val movies = arrayListOf(movie1, movie2)
        runBlocking {
            dao.insert(movies)
            val result = dao.getByDate("2020-11-03")
            assertEquals(result[0].title, "movie 1")
        }
    }

    @Test
    fun recyclerViewTest() {
        /* clicking the item */
        onView(withId(R.id.recyclerView)).perform(
            actionOnItemAtPosition<MovieViewHolder>(
                0,
                click()
            )
        )

        /* showing bottom sheet */
        onView(withId(R.id.bottomSheet)).check(matches(isDisplayed()))
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.kromer.themoviedb", appContext.packageName)
    }
}