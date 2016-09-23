package com.indev.netflixroulette;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

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
