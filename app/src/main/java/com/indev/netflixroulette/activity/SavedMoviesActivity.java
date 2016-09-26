package com.indev.netflixroulette.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.indev.netflixroulette.R;
import com.indev.netflixroulette.util.Constants;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

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

    private void setDrawerClickListener() {
        mDrawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (drawerItem.getIdentifier() == Constants.ID_SEARCH_ACTIVITY) {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawer.setSelectionAtPosition(Constants.ID_SAVED_MOVIES_ACTIVITY);
    }
}