package com.indev.netflixroulette.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Entity of production.
 */
@RealmClass
public class Production implements Serializable, RealmModel {

    @SerializedName("show_title")
    @Expose
    private String showTitle;

    @SerializedName("release_year")
    @Expose
    private String releaseYear;

    @Expose
    private String rating;

    @Expose
    private String director;

    @Expose
    private String summary;

    @Expose
    private String poster;

    public Production() {

    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRating() {
        return rating + "/5";
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}