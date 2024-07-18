package com.phroton.notes


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CreateNotesTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun createNotesTest() {
        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    allOf(
                        withId(R.id.app_bar_main),
                        childAtPosition(
                            withId(R.id.drawer_layout),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.editorTitle),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editActivity),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Sample"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.editorContent),
                childAtPosition(
                    allOf(
                        withId(R.id.editorScrollView),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    0
                )
            )
        )
        appCompatEditText2.perform(scrollTo(), replaceText("Description "), closeSoftKeyboard())

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.action_bar),
                        2
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        val materialTextView = onView(
            allOf(
                withId(androidx.appcompat.R.id.title), withText("Save"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    allOf(
                        withId(R.id.app_bar_main),
                        childAtPosition(
                            withId(R.id.drawer_layout),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.editorTitle),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editActivity),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("w"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.editorContent),
                childAtPosition(
                    allOf(
                        withId(R.id.editorScrollView),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    0
                )
            )
        )
        appCompatEditText4.perform(scrollTo(), replaceText("with "), closeSoftKeyboard())

        val overflowMenuButton2 = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.action_bar),
                        2
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        overflowMenuButton2.perform(click())

        val materialTextView2 = onView(
            allOf(
                withId(androidx.appcompat.R.id.title), withText("Save"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(click())

        val floatingActionButton3 = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    allOf(
                        withId(R.id.app_bar_main),
                        childAtPosition(
                            withId(R.id.drawer_layout),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton3.perform(click())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.editorTitle),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.editActivity),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("3"), closeSoftKeyboard())

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.editorContent),
                childAtPosition(
                    allOf(
                        withId(R.id.editorScrollView),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    0
                )
            )
        )
        appCompatEditText6.perform(scrollTo(), replaceText("three"), closeSoftKeyboard())

        val overflowMenuButton3 = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.action_bar),
                        2
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        overflowMenuButton3.perform(click())

        val materialTextView3 = onView(
            allOf(
                withId(androidx.appcompat.R.id.title), withText("Color"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView3.perform(click())

        val materialRadioButton = onView(
            allOf(
                withId(R.id.radio_color_blue), withText("Blue"),
                childAtPosition(
                    allOf(
                        withId(R.id.radio_color_group),
                        childAtPosition(
                            withId(android.R.id.custom),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialRadioButton.perform(click())

        val overflowMenuButton4 = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.action_bar),
                        2
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        overflowMenuButton4.perform(click())

        val materialTextView4 = onView(
            allOf(
                withId(androidx.appcompat.R.id.title), withText("Save"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView4.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.notesList),
                childAtPosition(
                    withId(R.id.home_fragment_root),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.menu_editor_save), withContentDescription("Save"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.action_bar),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val recyclerView2 = onView(
            allOf(
                withId(R.id.notesList),
                childAtPosition(
                    withId(R.id.home_fragment_root),
                    1
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(androidx.appcompat.R.id.action_bar),
                        childAtPosition(
                            withId(androidx.appcompat.R.id.action_bar_container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
