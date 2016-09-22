package com.indev.netflixroulette;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Admin on 22.09.2016.
 */

public interface NetflixRouletteApi {

    /**
     * See http://netflixroulette.net/api/api.php?actor=Harrison%20Ford
     */
    @GET("/api/api.php?")
    Observable<List<Production>> listProductions(@Query("actor") String actor);
}
