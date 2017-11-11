package com.example.hoege1.movietime;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment
{

    private ArrayAdapter<String> mMovieAdapter;

    public MovieFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        updateMovieData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        mMovieAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_poster, // The name of the layout ID.
                        R.id.list_item_movie_textview, // The ID of the textview to populate.
                        new ArrayList());

        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_movie_data);
        listView.setAdapter(mMovieAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    private void updateMovieData()
    {
        FetchMovieData movieData = new FetchMovieData();
        movieData.execute();
    }

    public class FetchMovieData extends AsyncTask<Void, Void, List<String>>
    {
        private final String LOG_TAG = FetchMovieData.class.getSimpleName();

        private List<String> getMoviePosterFromJson(String movieJsonStr)
                throws JSONException
        {
            final String OWM_RESULTS = "results";
            final String OWM_POSTER_PATH = "poster_path";
            List<String> posterArray = new ArrayList<String>();

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray resultsArray = movieJson.getJSONArray(OWM_RESULTS);

            for(int i=0; i<resultsArray.length(); i++)
            {
                JSONObject movieData = resultsArray.getJSONObject(i);
                posterArray.add(movieData.get(OWM_POSTER_PATH).toString());

                Log.d(LOG_TAG, posterArray.get(i));
            }

            return posterArray;
        }

        @Override
        protected List<String> doInBackground(Void... voids)
        {
            List<String> posterArray = new ArrayList<String>();

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try
            {
                // Construct the URL for the movie database
                final String FORECAST_BASE_URL = "https://api.themoviedb.org/3";
                final String QUERY_STRING = "movie/now_playing";
                final String LANGUAGE_STRING = "en-US";
                final String PAGE_NUM_STRING = "1";
                final String API_KEY_STRING = "7f85df63fe917ac57f3c002f0b52ede6";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendEncodedPath(QUERY_STRING)
                        .appendQueryParameter("api_key", API_KEY_STRING)
                        .appendQueryParameter("language", LANGUAGE_STRING)
                        .appendQueryParameter("page", PAGE_NUM_STRING)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.d(LOG_TAG, url.toString());

                // Create the request to OpenMovieDB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (urlConnection != null)
                {
                    urlConnection.disconnect();
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                // Parse the JSON data
                try
                {
                    posterArray = getMoviePosterFromJson(buffer.toString());
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                return posterArray;
            }
            catch(IOException e)
            {
                Log.e(LOG_TAG, "Error ", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<String> moviePosterResultStr)
        {
            if (moviePosterResultStr != null)
            {
                mMovieAdapter.clear();
                for(String moviePosterString : moviePosterResultStr)
                {
                    mMovieAdapter.add(moviePosterString);
                }
            }
        }
    }

}
