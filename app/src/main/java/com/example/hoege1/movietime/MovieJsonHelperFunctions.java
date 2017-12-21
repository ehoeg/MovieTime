package com.example.hoege1.movietime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoege1 on 11/13/17.
 */

public class MovieJsonHelperFunctions
{
    public MovieJsonHelperFunctions()
    {
        // Required empty public constructor
    }

    public Integer getMovieVoteCount(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = movieJson.get("vote_count").toString();

        return movieJson.getInt("vote_count");
    }

    public String getMovieId(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = movieJson.get("id").toString();

        return str;
    }

    public String getMovieVideo(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = movieJson.get("video").toString();

        return str;
    }

    public String getMovieTitle(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = movieJson.get("title").toString();

        return str;
    }

    public String getMoviePopularity(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = movieJson.get("popularity").toString();

        return str;
    }

    public String getMoviePosterPath(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = "http://image.tmdb.org/t/p/w780" + movieJson.get("poster_path").toString();

        return str;
    }

    public String getMovieOriginalLanguage(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = movieJson.get("original_language").toString();

        return str;
    }

    public String getMovieOriginalTitle(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = movieJson.get("original_title").toString();

        return str;
    }

    public String getMovieBackdropPath(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = movieJson.get("backdrop_path").toString();

        return str;
    }

    public String getMovieAdult(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = movieJson.get("adult").toString();

        return str;
    }

    public String getMovieOverview(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = movieJson.get("overview").toString();

        return str;
    }

    public String getMovieReleaseDate(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String str;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        str = movieJson.get("release_date").toString();

        return str;
    }

    public String getMoviePosterFromJson(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String posterPath;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        posterPath = "http://image.tmdb.org/t/p/w780" + movieJson.get("poster_path").toString();

        return posterPath;
    }

    public String getMovieTitleFromJson(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String movieTitle;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        movieTitle = movieJson.get("title").toString();

        return movieTitle;
    }

    public String getMovieDescriptionFromJson(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String movieDescription;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        movieDescription = movieJson.get("overview").toString();

        return movieDescription;
    }

    public String getMovieReleaseDateFromJson(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String releaseDate;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        releaseDate = movieJson.get("release_date").toString();

        return releaseDate;
    }

    public String getMovieUserRatingFromJson(String movieJsonStr)
            throws JSONException, MalformedURLException
    {
        String userRating;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        userRating = movieJson.get("vote_average").toString() + " / 10";

        return userRating;
    }
}
