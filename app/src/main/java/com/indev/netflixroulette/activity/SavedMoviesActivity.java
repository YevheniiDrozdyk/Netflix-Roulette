package com.indev.netflixroulette.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.indev.netflixroulette.R;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class SavedMoviesActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Drawer mDrawer;
    private int mSavedMovies = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_movies);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setNavigationDrawer();
    }

    private void setNavigationDrawer() {
        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withHeader(R.layout.item_drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.item_drawer_saved_movies).withBadge(mSavedMovies + "")
                                .withIdentifier(1).withIcon(R.drawable.ic_grade_black_18dp),
                        new SectionDrawerItem().withName(R.string.item_drawer_search_features),
                        new PrimaryDrawerItem().withName(R.string.item_drawer_search_with_director)
                                .withIdentifier(2).withIcon(R.drawable.ic_perm_identity_black_18dp))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 2) {
                            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                            startActivity(intent);
                        }
                        return false;
                    }
                })
                .withSelectedItem(1)
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