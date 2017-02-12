package com.indev.netflixroulette.network;

import com.indev.netflixroulette.model.Production;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Defines method for work with Netflix Roulette APi.
 *
 * @author E.Drozdyk
 * @version 1.0 15 Oct 2016
 */
public interface NetflixRouletteApi {

    /**
     * Finds all queriedProductions.
     *
     * @param director name.
     * @return list of queriedProductions.
     */
    @GET("/api/api.php?")
    Observable<List<Production>> findProductions(@Query("director") String director);
}