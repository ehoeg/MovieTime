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
        final String OWM_TITLE = "title";
        String movieTitle;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        movieTitle = movieJson.get("title").toString();

        return movieTitle;
    }
}
