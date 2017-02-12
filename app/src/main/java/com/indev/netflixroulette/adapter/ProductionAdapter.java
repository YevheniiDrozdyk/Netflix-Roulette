package com.indev.netflixroulette.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indev.netflixroulette.model.Production;
import com.indev.netflixroulette.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter of queriedProductions.
 *
 * @author E.Drozdyk
 * @version 1.0 15 Oct 2016
 */
public class ProductionAdapter extends RecyclerView.Adapter<ProductionAdapter.Holder> {

    private List<Production> mProductionList;
    private Context mContext;

    public ProductionAdapter(List<Production> productions, Context context) {
        this.mProductionList = productions;
        this.mContext = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_production, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Production production = mProductionList.get(position);
        holder.bindProduction(production);
    }

    @Override
    public int getItemCount() {
        return (mProductionList != null ? mProductionList.size() : 0);
    }

    class Holder extends RecyclerView.ViewHolder {

        private Production production;
        private ImageView posterImageView;
        private TextView descriptionTextView;

        Holder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.list_item_poster_image_view);
            descriptionTextView = (TextView) itemView.findViewById(R.id.list_item_description_text_view);
        }

        private void bindProduction(Production production) {
            this.production = production;

            Picasso.with(mContext).load(this.production.getPoster())
                    .placeholder(R.drawable.ic_error_placeholder)
                    .resize(240, 240)
                    .centerCrop()
                    .into(posterImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = loadBitmap(posterImageView);
                            Palette.from(bitmap).generate(palette -> {
                                        descriptionTextView.setBackgroundColor(palette.getDarkVibrantColor(Color.BLACK));
                                        posterImageView.setBackgroundColor(palette.getVibrantColor(Color.WHITE));
                                    }
                            );
                        }

                        @Override
                        public void onError() {
                            descriptionTextView.setBackgroundColor(Color.RED);
                        }
                    });

            descriptionTextView.setText(this.production.getShowTitle());
        }
    }

    private Bitmap loadBitmap(ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        return bitmapDrawable.getBitmap();
    }
}