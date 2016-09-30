package com.indev.netflixroulette.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.indev.netflixroulette.R;
import com.indev.netflixroulette.Constants;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;

public class BaseActivity extends AppCompatActivity {

    private Drawer mDrawer;

    public Drawer onCreateDrawer(Toolbar toolbar, int position) {
        return mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withHeader(R.layout.item_drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.item_drawer_saved_movies)
                                .withIdentifier(Constants.ID_SAVED_MOVIES_ACTIVITY).withIcon(R.drawable.ic_grade_black_18dp),
                        new SectionDrawerItem().withName(R.string.item_drawer_search_features),
                        new PrimaryDrawerItem().withName(R.string.item_drawer_search_with_director)
                                .withIdentifier(Constants.ID_SEARCH_ACTIVITY).withIcon(R.drawable.ic_perm_identity_black_18dp))
                .withSelectedItem(position)
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