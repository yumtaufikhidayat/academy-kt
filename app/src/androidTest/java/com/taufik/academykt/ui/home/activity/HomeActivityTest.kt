package com.taufik.academykt.ui.home.activity

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.taufik.academykt.R
import com.taufik.academykt.utils.DataDummy
import com.taufik.academykt.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeActivityTest {

    private val dummyCourse = DataDummy.generateDummyCourses()

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadCourses() {
        delayTwoSecond()
        onView(withId(R.id.rvAcademy)).check(matches(isDisplayed()))
        onView(withId(R.id.rvAcademy)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyCourse.size))
    }

    @Test
    fun loadDetailCourse() {
        delayTwoSecond()
        onView(withId(R.id.rvAcademy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        delayTwoSecond()
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle)).check(matches(withText(dummyCourse[0].title)))
        onView(withId(R.id.tvDate)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDate)).check(matches(withText("Deadline ${dummyCourse[0].deadline}")))
    }

    @Test
    fun loadModule() {
        delayTwoSecond()
        onView(withId(R.id.rvAcademy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        delayTwoSecond()
        onView(withId(R.id.btnStart)).perform(click())
        delayTwoSecond()
        onView(withId(R.id.rvModule)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailModule() {
        delayTwoSecond()
        onView(withId(R.id.rvAcademy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        delayTwoSecond()
        onView(withId(R.id.btnStart)).perform(click())
        delayTwoSecond()
        onView(withId(R.id.rvModule)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        delayTwoSecond()
        onView(withId(R.id.webView)).check(matches(isDisplayed()))
    }

    @Test
    fun loadBookmarks() {
        onView(withText("Bookmark")).perform(click())
        delayTwoSecond()
        onView(withId(R.id.rvBookmark)).check(matches(isDisplayed()))
        onView(withId(R.id.rvBookmark)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyCourse.size))
    }

    private fun delayTwoSecond() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}