package com.indev.netflixroulette.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.indev.netflixroulette.util.Constants;
import com.indev.netflixroulette.R;
import com.indev.netflixroulette.util.Production;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        Production production = (Production) getIntent().getSerializableExtra(Constants.EXTRA_PARAM);

        ImageView posterImageView = (ImageView) findViewById(R.id.poster_image_view);
        TextView descriptionTextView = (TextView) findViewById(R.id.description_text_view);

        ViewCompat.setTransitionName(posterImageView, Constants.IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(descriptionTextView, Constants.DESCRIPTION_TRANSITION_NAME);

        Picasso.with(this).load(production.getPoster())
                .placeholder(R.drawable.ic_error_placeholder)
                .resize(240, 240)
                .centerCrop()
                .into(posterImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = loadBitmap(posterImageView);
                        Palette.from(bitmap).generate(
                                new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        posterImageView.setBackgroundColor(palette.getVibrantColor(Color.WHITE));
                                    }
                                }
                        );
                    }

                    @Override
                    public void onError() {

                    }
                });

        getSupportActionBar().setTitle(getTitle(production));
        descriptionTextView.setText(getDescription(production));
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

}