package com.indev.netflixroulette.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.indev.netflixroulette.R;
import com.indev.netflixroulette.constant.Constants;
import com.mikepenz.materialdrawer.Drawer;

/**
 * UI class, that shows saved movies.
 *
 * @author E.Drozdyk
 * @version 1.0 15 Oct 2016
 */
public class SavedMoviesActivity extends BaseActivity {

    private Drawer mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = super.onCreateDrawer(toolbar, Constants.ID_SAVED_MOVIES_ACTIVITY);
        setDrawerClickListener();
    }

    /**
     * Listens clicks on navigation drawer.
     */
    private void setDrawerClickListener() {
        mDrawer.setOnDrawerItemClickListener((view, position, drawerItem) -> {
            if (drawerItem.getIdentifier() == Constants.ID_SEARCH_ACTIVITY) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawer.setSelectionAtPosition(Constants.ID_SAVED_MOVIES_ACTIVITY);
    }
}