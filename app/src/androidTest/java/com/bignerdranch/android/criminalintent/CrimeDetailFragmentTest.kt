package com.bignerdranch.android.criminalintent

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.Date

@RunWith(AndroidJUnit4::class)
class CrimeDetailFragmentTest {

    private lateinit var scenario: FragmentScenario<CrimeDetailFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun should_updateCrimeTitle_when_titleIsTyped() {
        Espresso.onView(ViewMatchers.withId(R.id.crime_title))
            .perform(ViewActions.typeText("title_test"))
        scenario.onFragment { fragment ->
            assertEquals("title_test", fragment.crime.title)
        }
    }

    @Test
    fun should_reflectCurrentDateOnButton() {
        scenario.onFragment { fragment ->
            assertEquals(
                SimpleDateFormat("yyyy-MM-dd").format(Date()),
                SimpleDateFormat("yyyy-MM-dd").format(fragment.crime.date)
            )
        }
    }

    @Test
    fun should_updateCrimeToSolved_when_isSolvedIsChecked() {
        Espresso.onView(ViewMatchers.withId(R.id.crime_solved)).perform(ViewActions.click())
        scenario.onFragment { fragment ->
            assertEquals(true, fragment.crime.isSolved)
        }
    }

    @Test
    fun should_updateCrimeToNotSolved_when_isSolvedNotChecked() {
        scenario.onFragment { fragment ->
            assertEquals(false, fragment.crime.isSolved)
        }
    }
}