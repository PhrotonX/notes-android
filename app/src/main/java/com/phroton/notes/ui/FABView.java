package com.phroton.notes.ui;

import android.content.Intent;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 *  Manages floating action button by handling View.OnClickListener and ActivityResultLauncher&lt;Intent&gt;.
 * */
public abstract class FABView extends Fragment {
    private FloatingActionButton mFab;

    private ActivityResultLauncher<Intent> mFabIntent = null;

    /**
     * @return The instance FloatingActionButton set into view.
     * */
    FloatingActionButton getFab(){
        return mFab;
    }

    /**
     * @return The instance of code for handling FloatingActionButton intent.
     * */
    ActivityResultLauncher<Intent> getFabIntent(){
        return mFabIntent;
    }

    /**
     *  Sets up the entirely of FloatingActionButton.
     *
     * @param fab The floating action button to be set.
     * */
    void setFloatingActionButton(FloatingActionButton fab){
        mFabIntent = onFabIntent();

        mFab = fab;
        mFab.setOnClickListener(onFabClick());
    }

    /**
     * Set the View.OnClickListener for the floating action button of the app, invoked within setUpFab().
     *
     * @return The click listener to be set for floating action button.
     * */
    abstract View.OnClickListener onFabClick();

    /**
     * Sets the code for handling the result of FloatingActionButton Intent, invoked within setUpFab().
     *
     * @return The instance of code for handling the result of FloatingActionButton Intent.
     * */
    abstract ActivityResultLauncher<Intent> onFabIntent();
}
