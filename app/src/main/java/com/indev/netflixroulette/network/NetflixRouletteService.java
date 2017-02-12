package com.indev.netflixroulette.network;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Creates service with Netflix Roulette API.
 *
 * @author E.Drozdyk
 * @version 1.0 15 Oct 2016
 */
public class NetflixRouletteService {

    private NetflixRouletteService() {
    }

    public static NetflixRouletteApi createNetflixRouletteService() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://netflixroulette.net");
        return builder.build().create(NetflixRouletteApi.class);
    }
}