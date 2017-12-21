package com.example.hoege1.movietime;


import android.app.LoaderManager;
import android.content.ClipData;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
//import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.example.hoege1.movietime.data.MovieContract;
import com.example.hoege1.movietime.data.MovieDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>
{
    private final String LOG_TAG = MovieFragment.class.getSimpleName();

    private MovieAdapter mMovieAdapter;
    private static String mMovieSortOrder = "default";

    private static final String TOP_RATED_STRING = "Top Rated";
    private static final String POPULAR_STRING = "Popular";
    private static final String NOW_PLAYING_STRING = "Now Playing";

    private static final String SELECTED_KEY = "selected_position";

    private static final int MOVIE_LOADER = 0;
    private static boolean initMovieLoader = true;

    private static String[] NOW_PLAYING_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            MovieContract.NowPlayingEntry.TABLE_NAME + "." + MovieContract.NowPlayingEntry._ID,
            MovieContract.NowPlayingEntry.COLUMN_VOTE_COUNT,
            MovieContract.NowPlayingEntry.COLUMN_ID,
            MovieContract.NowPlayingEntry.COLUMN_VIDEO,
            MovieContract.NowPlayingEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.NowPlayingEntry.COLUMN_TITLE,
            MovieContract.NowPlayingEntry.COLUMN_POPULARITY,
            MovieContract.NowPlayingEntry.COLUMN_POSTER_PATH,
            MovieContract.NowPlayingEntry.COLUMN_ORIGINAL_LANGUAGE,
            MovieContract.NowPlayingEntry.COLUMN_ORIGINAL_TITLE,
            MovieContract.NowPlayingEntry.COLUMN_BACKDROP_PATHS,
            MovieContract.NowPlayingEntry.COLUMN_ADULT,
            MovieContract.NowPlayingEntry.COLUMN_OVERVIEW,
            MovieContract.NowPlayingEntry.COLUMN_RELEASE_DATE
    };

    private static String[] TOP_RATED_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            MovieContract.TopRatedEntry.TABLE_NAME + "." + MovieContract.NowPlayingEntry._ID,
            MovieContract.TopRatedEntry.COLUMN_VOTE_COUNT,
            MovieContract.TopRatedEntry.COLUMN_ID,
            MovieContract.TopRatedEntry.COLUMN_VIDEO,
            MovieContract.TopRatedEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.TopRatedEntry.COLUMN_TITLE,
            MovieContract.TopRatedEntry.COLUMN_POPULARITY,
            MovieContract.TopRatedEntry.COLUMN_POSTER_PATH,
            MovieContract.TopRatedEntry.COLUMN_ORIGINAL_LANGUAGE,
            MovieContract.TopRatedEntry.COLUMN_ORIGINAL_TITLE,
            MovieContract.TopRatedEntry.COLUMN_BACKDROP_PATHS,
            MovieContract.TopRatedEntry.COLUMN_ADULT,
            MovieContract.TopRatedEntry.COLUMN_OVERVIEW,
            MovieContract.TopRatedEntry.COLUMN_RELEASE_DATE
    };

    private static String[] POPULAR_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            MovieContract.PopularEntry.TABLE_NAME + "." + MovieContract.NowPlayingEntry._ID,
            MovieContract.PopularEntry.COLUMN_VOTE_COUNT,
            MovieContract.PopularEntry.COLUMN_ID,
            MovieContract.PopularEntry.COLUMN_VIDEO,
            MovieContract.PopularEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.PopularEntry.COLUMN_TITLE,
            MovieContract.PopularEntry.COLUMN_POPULARITY,
            MovieContract.PopularEntry.COLUMN_POSTER_PATH,
            MovieContract.PopularEntry.COLUMN_ORIGINAL_LANGUAGE,
            MovieContract.PopularEntry.COLUMN_ORIGINAL_TITLE,
            MovieContract.PopularEntry.COLUMN_BACKDROP_PATHS,
            MovieContract.PopularEntry.COLUMN_ADULT,
            MovieContract.PopularEntry.COLUMN_OVERVIEW,
            MovieContract.PopularEntry.COLUMN_RELEASE_DATE
    };

    // These indices are tied to NOW_PLAYING_COLUMNS.  If NOW_PLAYING_COLUMNS changes, these must change
    static final int COL_NOW_PLAYING_ID = 0;
    static final int COL_VOTE_COUNT = 1;
    static final int COL_ID = 2;
    static final int COL_VIDEO = 3;
    static final int COL_VOTE_AVERAGE = 4;
    static final int COL_TITLE = 5;
    static final int COL_POPULARITY = 6;
    static final int COL_POSTER_PATH = 7;
    static final int COL_ORIGINAL_LANGUAGE = 8;
    static final int COL_ORIGINAL_TITLE = 9;
    static final int COL_BACKDROP_PATHS = 10;
    static final int COL_ADULT = 11;
    static final int COL_OVERVIEW = 12;
    static final int COL_RELEASE_DATE = 13;

    public MovieFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onResume()
    {
        super.onResume();

        String queryType = getMovieSortType(getActivity());
        if(!queryType.equals(mMovieSortOrder))
        {
            mMovieSortOrder = queryType;
            updateMovieData();
        }

        // Call the movie loader
        if(initMovieLoader)
        {
            getLoaderManager().initLoader(MOVIE_LOADER, null, this);
            initMovieLoader = false;
        }
        else
        {
            getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        // Inflate the root view and create the Movie Adapter
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        mMovieAdapter = new MovieAdapter(getActivity(), null, 0);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movie);
        gridView.setAdapter(mMovieAdapter);

        //getLoaderManager().restartLoader(MOVIE_LOADER, null, this);

        return rootView;
    }

    public static String getMovieSortType(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("MovieSortOrder", "Now Playing");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.movie_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.settings)
        {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return true;
    }

    public void updateMovieData()
    {
        FetchMovieTask movieData = new FetchMovieTask(getActivity());
        movieData.execute();
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        Uri contentUri;
        String projection[];
        if(mMovieSortOrder.equals(TOP_RATED_STRING))
        {
            contentUri = MovieContract.TopRatedEntry.CONTENT_URI;
            projection = TOP_RATED_COLUMNS;
        }
        else if(mMovieSortOrder.equals(NOW_PLAYING_STRING))
        {
            contentUri = MovieContract.NowPlayingEntry.CONTENT_URI;
            projection = NOW_PLAYING_COLUMNS;
        }
        else // POPULAR_STRING
        {
            contentUri = MovieContract.PopularEntry.CONTENT_URI;
            projection = POPULAR_COLUMNS;
        }

        return new android.support.v4.content.CursorLoader(getActivity(),
                contentUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor)
    {
        cursor.moveToFirst();
        mMovieAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader)
    {
        mMovieAdapter.swapCursor(null);
    }
}
