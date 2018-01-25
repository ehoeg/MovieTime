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
import com.example.hoege1.movietime.data.MovieProvider;

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

    private static final String SELECTED_KEY = "selected_position";

    private static final int MOVIE_LOADER = 0;
    private static boolean initMovieLoader = true;

    private static boolean mNowPlayingDbLoaded = false;
    private static boolean mTopRatedDbLoaded = false;
    private static boolean mPopularDbLoaded = false;
    private static boolean mFavoritesDbLoaded = false;

    public MovieFragment()
    {
        // empty constructor
    }

    @Override
    public void onResume()
    {
        super.onResume();

        String queryType = Utility.getMovieSortType(getActivity());
        if(!queryType.equals(mMovieSortOrder))
        {
            mMovieSortOrder = queryType;

            // Only load the databases once - movies don't change often
            if(queryType.equals("Popular") && !mPopularDbLoaded)
            {
                mPopularDbLoaded = true;

                // delete any entries before adding new ones
                getContext().getContentResolver().delete(MovieContract.PopularEntry.CONTENT_URI, null, null);

                // add new entries
                Utility.updateMovieData(queryType, getActivity());
            }
            else if(queryType.equals("Now Playing") && !mNowPlayingDbLoaded)
            {
                mNowPlayingDbLoaded = true;

                // delete any entries before adding new ones
                getContext().getContentResolver().delete(MovieContract.NowPlayingEntry.CONTENT_URI, null, null);

                // add new entries
                Utility.updateMovieData(queryType, getActivity());
            }
            else if (queryType.equals("Top Rated") && !mTopRatedDbLoaded)
            {
                mTopRatedDbLoaded = true;

                // delete any entries before adding new ones
                getContext().getContentResolver().delete(MovieContract.TopRatedEntry.CONTENT_URI, null, null);

                // add new entries
                Utility.updateMovieData(queryType, getActivity());
            }
            else if (queryType.equals("Favorites") && !mFavoritesDbLoaded)
            {
                mFavoritesDbLoaded = true;

                // delete any entries before adding new ones
                //getContext().getContentResolver().delete(MovieContract.FavoriteEntry.CONTENT_URI, null, null);

                // add new entries
                //Utility.updateMovieData(queryType, getActivity());
            }
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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Cursor cursor = mMovieAdapter.getCursor();
                cursor.moveToPosition(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("movieTitle", cursor.getString(MovieContract.COL_ORIGINAL_TITLE));
                intent.putExtra("queryTable", mMovieSortOrder);
                startActivity(intent);
            }
        });

        return rootView;
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

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        Uri contentUri;
        String projection[];

        contentUri = MovieContract.MovieContractHelper.getContentUri(mMovieSortOrder);
        projection = MovieContract.MovieContractHelper.getProjection(mMovieSortOrder);

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
