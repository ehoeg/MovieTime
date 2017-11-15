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
