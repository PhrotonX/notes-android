package com.phroton.notes;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.phroton.notes.ui.editor.EditorActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CreateNoteInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public ActivityScenarioRule<EditorActivity> editActivityRule = new ActivityScenarioRule<>(EditorActivity.class);

    @Test
    public void addNotes(){
        for(int i = 0; i < 10; i++){
            onView(withId(R.id.fab))
                    .perform(click())
                    .check(matches(isDisplayed()));
            onView(withId(R.id.editorTitle))
                    .perform(typeText("Sample note " + i));
            onView(withId(R.id.editorContent))
                    .perform(typeText("Sample description " + i));
            onView(withId(R.id.menu_editor_save))
                    .perform(click())
                    .check(matches(isDisplayed()));
        }
    }
}
