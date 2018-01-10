package com.example.hoege1.movietime;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by hoege1 on 1/8/18.
 */

public class Utility
{
    public static String getMovieSortType(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("MovieSortOrder", "Now Playing");
    }

    public static void updateMovieData(String queryString, Context context)
    {
        FetchMovieTask movieData = new FetchMovieTask(context, queryString);
        movieData.execute();
    }
}
