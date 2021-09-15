package com.codingwithmitch.espressouitestexamples.ui.movie

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.codingwithmitch.espressouitestexamples.R
import com.codingwithmitch.espressouitestexamples.data.FakeMovieData
import com.codingwithmitch.espressouitestexamples.util.EspressoIdlingResource
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)

class MyMovieListFragmentTest{
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    val listItemInTest = 4
    val movieInTest = FakeMovieData.movies[listItemInTest]
    val directors = movieInTest.directors
    val verifyDirectorsValue = DirectorsFragment.stringBuilderForDirectors(directors!!)
    val actors = movieInTest.star_actors
    val verifyActorsValue = StarActorsFragment.stringBuilderForStarActors(actors!!)

    // recyclerView comes into View
    @Test
    fun test_isFragmentVisible_onAppLaunch() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    // Is correct movie in view?
    @Test
    fun test_SelectListItem_isDetailFragmentVisible() {
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<MoviesListAdapter.MovieViewHolder>(listItemInTest, click()))
        onView(withId(R.id.movie_title)).check(matches(withText(movieInTest.title)))
    }

    //select list item -> nav to DetailFragment -> press back
    @Test
    fun test_backNavigation_toMovieListFragment() {
        onView(withId(R.id.recycler_view))
            .perform(
                actionOnItemAtPosition<MoviesListAdapter.MovieViewHolder>(
                    listItemInTest,
                    click()))
        onView(withId(R.id.movie_title)).check(matches(withText(movieInTest.title)))
        pressBack()
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    //nav to DirectorsFragment
    @Test
    fun test_navDirectorsFragment_validateDirectorsList() {
        onView(withId(R.id.recycler_view))
            .perform(
                actionOnItemAtPosition<MoviesListAdapter.MovieViewHolder>(
                    listItemInTest,
                    click()))

        onView(withId(R.id.movie_title)).check(matches(withText(movieInTest.title)))

        onView(withId(R.id.movie_directiors)).perform(click())

        onView(withId(R.id.directors_text))
            .check(matches(withText(verifyDirectorsValue)))

    }

    @Test
    fun test_navStarActorsFragment_validateActorsList() {
        onView(withId(R.id.recycler_view))
            .perform(
                actionOnItemAtPosition<MoviesListAdapter.MovieViewHolder>(
                    listItemInTest,
                    click()))
        onView(withId(R.id.movie_title)).check(matches(withText(movieInTest.title)))

        onView(withId(R.id.movie_star_actors)).perform(click())

        onView(withId(R.id.star_actors_text))
            .check(matches(withText(verifyActorsValue)))
    }

}
