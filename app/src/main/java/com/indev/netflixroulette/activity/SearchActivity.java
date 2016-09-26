package com.indev.netflixroulette.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.indev.netflixroulette.util.Constants;
import com.indev.netflixroulette.R;
import com.indev.netflixroulette.adapter.ProductionAdapter;
import com.indev.netflixroulette.adapter.RecyclerViewItemClickListener;
import com.indev.netflixroulette.util.NetflixRouletteApi;
import com.indev.netflixroulette.util.NetflixRouletteService;
import com.indev.netflixroulette.util.Production;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SearchActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private NetflixRouletteApi mNetflixService;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private List<Production> mProductionList;
    private ProductionAdapter mProductionAdapter;
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mSubscriptions = new CompositeSubscription();
        mProductionList = new ArrayList<>();
        mProductionAdapter = new ProductionAdapter(mProductionList, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.production_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mProductionAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewItemClickListener(this, (view, position) -> {
                    Intent detailIntent = new Intent(SearchActivity.this, DetailActivity.class);
                    detailIntent.putExtra(Constants.EXTRA_PARAM, mProductionList.get(position));

                    Pair imagePair = new Pair<>(view.findViewById(R.id.list_item_poster_image_view), Constants.IMAGE_TRANSITION_NAME);
                    Pair titlePair = new Pair<>(view.findViewById(R.id.list_item_title_text_view), Constants.TITLE_TRANSITION_NAME);
                    Pair descriptionPair = new Pair<>(view.findViewById(R.id.list_item_info_layout), Constants.DESCRIPTION_TRANSITION_NAME);

                    ActivityOptionsCompat transitionActivityOptions =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    SearchActivity.this, imagePair, titlePair, descriptionPair);

                    ActivityCompat.startActivity(SearchActivity.this,
                            detailIntent, transitionActivityOptions.toBundle());
                }));

        mNetflixService = NetflixRouletteService.createNetflixRouletteService();

        //Auto-search after startup for testing
        startSearch(Constants.PLACEHOLDER_DIRECTOR);
        getSupportActionBar().setTitle(Constants.PLACEHOLDER_DIRECTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSubscriptions == null || mSubscriptions.isUnsubscribed()) {
            mSubscriptions = new CompositeSubscription();
        }
    }

    private void startSearch(String query) {
        mProgressDialog = ProgressDialog.show(this, "Loading productions...", "Please wait...", true);
        mProductionList.clear();
        mSubscriptions.add(
                mNetflixService.listProductions(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Production>>() {

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<Production> productions) {
                                mProgressDialog.dismiss();
                                for (Production p : productions) {
                                    mProductionList.add(p);
                                }
                                mProductionAdapter = new ProductionAdapter(mProductionList, getApplicationContext());
                                mRecyclerView.setAdapter(mProductionAdapter);

                                //Alert user if no result are returned from the service
                                if (mProductionList == null) {
                                    Snackbar.make(mRecyclerView, "No result found.", Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_view_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.trim().length() > 0) {
                    startSearch(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}