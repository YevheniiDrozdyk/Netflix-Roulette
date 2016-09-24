package com.indev.netflixroulette;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NetflixRouletteApi {

    /**
     * See http://netflixroulette.net/api/api.php?director=Quentin%20Tarantino
     */
    @GET("/api/api.php?")
    Observable<List<Production>> listProductions(@Query("director") String actor);
}
