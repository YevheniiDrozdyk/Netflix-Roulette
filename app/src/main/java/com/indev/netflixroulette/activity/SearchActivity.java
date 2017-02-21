package com.indev.netflixroulette.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.indev.netflixroulette.constant.Constants;
import com.indev.netflixroulette.R;
import com.indev.netflixroulette.adapter.ProductionAdapter;
import com.indev.netflixroulette.adapter.RecyclerViewItemClickListener;
import com.indev.netflixroulette.network.NetflixRouletteApi;
import com.indev.netflixroulette.network.NetflixRouletteService;
import com.indev.netflixroulette.model.Production;
import com.mikepenz.materialdrawer.Drawer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * UI class, that shows searched movies.
 *
 * @author E.Drozdyk
 * @version 1.0 15 Oct 2016
 */
public class SearchActivity extends BaseActivity {

    private NetflixRouletteApi mNetflixService;
    private CompositeSubscription mSubscriptions;
    private List<Production> mProductions;
    private ProductionAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setNavigationDrawer(toolbar);

        defaultInitialisation();

        if (savedInstanceState == null || !savedInstanceState.containsKey(Constants.PRODUCTION_KEY)) {
            //Auto-search after startup
            startSearchProductions(Constants.PLACEHOLDER_DIRECTOR);
            getSupportActionBar().setTitle(Constants.PLACEHOLDER_DIRECTOR);
        } else {
            mProductions = (List<Production>)
                    savedInstanceState.getSerializable(Constants.PRODUCTION_KEY);
            setRecyclerViewAdapter();
            getSupportActionBar().setTitle(mProductions.get(0).getDirector());
        }
    }

    private void defaultInitialisation() {
        mSubscriptions = new CompositeSubscription();
        mProductions = new ArrayList<>();
        mAdapter = new ProductionAdapter(mProductions, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.production_recycler_view);
        int countOfColumns;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            countOfColumns = 2;
        } else {
            countOfColumns = 3;
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, countOfColumns));
        mRecyclerView.setAdapter(mAdapter);
        setRecyclerViewItemClickListener(mRecyclerView);
        mNetflixService = NetflixRouletteService.createNetflixRouletteService();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mProductions != null) {
            outState.putSerializable(Constants.PRODUCTION_KEY, (Serializable) mProductions);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSubscriptions == null || mSubscriptions.isUnsubscribed()) {
            mSubscriptions = new CompositeSubscription();
        }
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
                    startSearchProductions(query);
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

    private void startSearchProductions(String director) {
        mProgressDialog = ProgressDialog.show(this, "Loading queriedProductions...", "Please wait...", true);
        mProductions.clear();
        mSubscriptions.add(
                mNetflixService.findProductions(director)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Production>>() {

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                mProgressDialog.dismiss();
                                Snackbar.make(mRecyclerView, "No result found... Try again!", Snackbar.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNext(List<Production> productions) {
                                mProgressDialog.dismiss();
                                mProductions.addAll(productions);
                                setRecyclerViewAdapter();
                            }
                        }));
    }

    private void setRecyclerViewAdapter() {
        mAdapter = new ProductionAdapter(mProductions, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setRecyclerViewItemClickListener(RecyclerView recyclerView) {
        recyclerView.addOnItemTouchListener(
                new RecyclerViewItemClickListener(this, (view, position) -> {
                    Intent detailIntent = new Intent(SearchActivity.this, DetailActivity.class);
                    detailIntent.putExtra(Constants.EXTRA_PARAM, mProductions.get(position));

                    Pair imagePair = new Pair<>(view.findViewById(R.id.list_item_poster_image_view), Constants.IMAGE_TRANSITION_NAME);
                    Pair description = new Pair<>(view.findViewById(R.id.list_item_description_text_view), Constants.TITLE_TRANSITION_NAME);
                    ActivityOptionsCompat transitionActivityOptions =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    SearchActivity.this, imagePair, description);

                    ActivityCompat.startActivity(SearchActivity.this,
                            detailIntent, transitionActivityOptions.toBundle());
                }));
    }

    private void setNavigationDrawer(Toolbar toolbar) {
        Drawer drawer = super.onCreateDrawer(toolbar, Constants.ID_SEARCH_ACTIVITY);
        drawer.setOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (drawerItem.getIdentifier() == Constants.ID_SAVED_MOVIES_ACTIVITY) {
                        Intent intent = new Intent(getApplicationContext(), SavedMoviesActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    return false;
                }
        );
    }
}
