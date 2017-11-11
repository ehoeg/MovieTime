package com.example.hoege1.movietime;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hoege1 on 11/10/17.
 */

public class FetchMovieData extends AsyncTask
{
    private final String LOG_TAG = FetchMovieData.class.getSimpleName();

    @Override
    protected Object doInBackground(Object[] objects)
    {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try
        {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL = "https://api.themoviedb.org/3";
            final String QUERY_STRING = "discover/movie?primary_release_date.gte=2014-09-15&primary_release_date.lte=2014-10-22";
            final String API_KEY_STRING = "&api_key=7f85df63fe917ac57f3c002f0b52ede6";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendEncodedPath(QUERY_STRING)
                    .appendEncodedPath(API_KEY_STRING)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.e(LOG_TAG, url.toString());

        }
        catch(IOException e)
        {
            Log.e(LOG_TAG, "Error ", e);
        }

        return null;
    }
}
