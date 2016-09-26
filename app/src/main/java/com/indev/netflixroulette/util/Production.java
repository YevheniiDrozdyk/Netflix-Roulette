package com.indev.netflixroulette.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Production implements Serializable {

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

    /**
     * @return The showTitle
     */
    public String getShowTitle() {
        return showTitle;
    }

    /**
     * @param showTitle The show_title
     */
    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    /**
     * @return The releaseYear
     */
    public String getReleaseYear() {
        return releaseYear;
    }

    /**
     * @param releaseYear The release_year
     */
    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * @return The rating
     */
    public String getRating() {
        return rating + "/5";
    }

    /**
     * @param rating The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * @return The director
     */
    public String getDirector() {
        return director;
    }

    /**
     * @param director The director
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The poster
     */
    public String getPoster() {
        return poster;
    }

    /**
     * @param poster The poster
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

}