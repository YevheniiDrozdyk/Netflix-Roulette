package com.indev.netflixroulette.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.indev.netflixroulette.R;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;

public class SavedMoviesActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Drawer mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_movies);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setDrawer();
    }

    private void setDrawer() {
        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withHeader(R.layout.item_drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.item_drawer_saved_movies).withBadge("0")
                                .withIdentifier(1).withIcon(R.drawable.ic_grade_black_18dp),
                        new SectionDrawerItem().withName(R.string.item_drawer_search_features),
                        new PrimaryDrawerItem().withName(R.string.item_drawer_search_with_title).withBadge("1")
                                .withIdentifier(2).withIcon(R.drawable.ic_description_black_18dp),
                        new PrimaryDrawerItem().withName(R.string.item_drawer_search_with_director).withBadge("2")
                                .withIdentifier(3).withIcon(R.drawable.ic_perm_identity_black_18dp))
                .build();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}