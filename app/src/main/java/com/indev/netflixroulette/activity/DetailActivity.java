package com.indev.netflixroulette.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.indev.netflixroulette.constant.Constants;
import com.indev.netflixroulette.R;
import com.indev.netflixroulette.model.Production;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

/**
 * UI class, that shows details of the movie.
 *
 * @author E.Drozdyk
 * @version 1.0 15 Oct 2016
 */
public class DetailActivity extends AppCompatActivity {

    private Production mProduction;

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mProduction = (Production) getIntent().getSerializableExtra(Constants.EXTRA_PARAM);

        ImageView posterImageView = (ImageView) findViewById(R.id.poster_image_view);
        TextView descriptionTextView = (TextView) findViewById(R.id.description_text_view);

        ViewCompat.setTransitionName(posterImageView, Constants.IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(descriptionTextView, Constants.DESCRIPTION_TRANSITION_NAME);

        Picasso.with(this).load(mProduction.getPoster())
                .placeholder(R.drawable.ic_error_placeholder)
                .resize(240, 240)
                .centerCrop()
                .into(posterImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = loadBitmap(posterImageView);
                        Palette.from(bitmap).generate(palette ->
                                posterImageView.setBackgroundColor(palette.getVibrantColor(Color.WHITE)));
                    }

                    @Override
                    public void onError() {

                    }
                });

        getSupportActionBar().setTitle(getTitle(mProduction));
        descriptionTextView.setText(getDescription(mProduction));

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();
    }

    private Bitmap loadBitmap(ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        return bitmapDrawable.getBitmap();
    }

    @NonNull
    private String getTitle(Production production) {
        return production.getShowTitle();
    }

    @NonNull
    private String getDescription(Production production) {
        return "\n\n Released: " + production.getReleaseYear() +
                "\n\n Rating: " + production.getRating() +
                "\n\n Director: " + production.getDirector() +
                "\n\n Summary: " + production.getSummary();
    }

    public void onAddMovieClick(View view) {
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.background_nested_scroll_view);
        Snackbar.make(scrollView, "Movie was added!", Snackbar.LENGTH_SHORT).show();
        mRealm.beginTransaction();
        mRealm.copyToRealm(mProduction);
        mRealm.commitTransaction();
    }
}
