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

import com.indev.netflixroulette.R;
import com.indev.netflixroulette.adapter.ProductionAdapter;
import com.indev.netflixroulette.adapter.RecyclerViewItemClickListener;
import com.indev.netflixroulette.util.NetflixRouletteApi;
import com.indev.netflixroulette.util.NetflixRouletteService;
import com.indev.netflixroulette.util.Production;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SearchActivity extends AppCompatActivity {

    private NetflixRouletteApi netflixService;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    private List<Production> productionList;
    private ProductionAdapter productionAdapter;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        productionList = new ArrayList<>();
        productionAdapter = new ProductionAdapter(productionList, this);
        recyclerView = (RecyclerView) findViewById(R.id.production_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(productionAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerViewItemClickListener(this, (view, position) -> {
                    Intent detailIntent = new Intent(SearchActivity.this, DetailActivity.class);
                    detailIntent.putExtra(DetailActivity.EXTRA_PARAM, productionList.get(position));

                    Pair imagePair = new Pair<>(view.findViewById(R.id.list_item_poster_image_view), DetailActivity.IMAGE_TRANSITION_NAME);
                    Pair titlePair = new Pair<>(view.findViewById(R.id.list_item_title_text_view), DetailActivity.TITLE_TRANSITION_NAME);
                    Pair backgroundPair = new Pair<>(view.findViewById(R.id.list_item_info_layout), DetailActivity.BACKGROUND_TRANSITION_NAME);

                    ActivityOptionsCompat transitionActivityOptions =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    SearchActivity.this, imagePair, titlePair, backgroundPair);

                    ActivityCompat.startActivity(SearchActivity.this,
                            detailIntent, transitionActivityOptions.toBundle());
                }));

        netflixService = NetflixRouletteService.createNetflixRouletteService();

        //Auto-search after startup for testing
        startSearch("Quentin Tarantino");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (subscriptions == null || subscriptions.isUnsubscribed()) {
            subscriptions = new CompositeSubscription();
        }
    }

    private void startSearch(String query) {
        progressDialog = ProgressDialog.show(this, "Loading productions...", "Please wait...", true);
        productionList.clear();
        subscriptions.add(
                netflixService.listProductions(query)
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
                                progressDialog.dismiss();
                                for (Production p : productions) {
                                    productionList.add(p);
                                }
                                productionAdapter = new ProductionAdapter(productionList, getApplicationContext());
                                recyclerView.setAdapter(productionAdapter);

                                //Alert user if no result are returned from the service
                                if (productionList == null) {
                                    Snackbar.make(recyclerView, "No result found.", Snackbar.LENGTH_LONG).show();
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