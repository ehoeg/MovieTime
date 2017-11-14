package com.example.hoege1.movietime;


import android.content.ClipData;
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
import android.widget.GridView;

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
public class MovieFragment extends Fragment
{
    private ArrayAdapter<ClipData.Item> mMovieAdapter;

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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
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
        private String[] mMoviePosterArray;
        private List<String> mMovieTitleArray = new ArrayList<String>();
        private JSONArray mMovieJsonArray;

        // Construct the URL for the movie database
        final String FORECAST_BASE_URL = "https://api.themoviedb.org/3";
        final String QUERY_STRING = "movie/now_playing";
        final String LANGUAGE_STRING = "en-US";
        final String PAGE_NUM_STRING = "1";
        final String API_KEY_STRING = "7f85df63fe917ac57f3c002f0b52ede6";

        private List<String> getMoviePosterFromJson(String movieJsonStr)
                throws JSONException, MalformedURLException
        {
            final String OWM_RESULTS = "results";
            final String OWM_POSTER_PATH = "poster_path";
            List<String> posterArray = new ArrayList<String>();

            JSONObject movieJson = new JSONObject(movieJsonStr);
            mMovieJsonArray = movieJson.getJSONArray(OWM_RESULTS);
            JSONArray resultsArray = movieJson.getJSONArray(OWM_RESULTS);

            for(int i=0; i<resultsArray.length(); i++)
            {
                JSONObject movieData = resultsArray.getJSONObject(i);
                posterArray.add("http://image.tmdb.org/t/p/w185" + movieData.get(OWM_POSTER_PATH).toString());
            }

            return posterArray;
        }

        private List<String> getMovieTitleFromJson(String movieJsonStr)
                throws JSONException, MalformedURLException
        {
            final String OWM_RESULTS = "results";
            final String OWM_TITLE = "title";
            List<String> movieTitleArray = new ArrayList<String>();

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray resultsArray = movieJson.getJSONArray(OWM_RESULTS);

            for(int i=0; i<resultsArray.length(); i++)
            {
                JSONObject movieData = resultsArray.getJSONObject(i);
                movieTitleArray.add(movieData.get(OWM_TITLE).toString());
            }
            return movieTitleArray;
        }

        @Override
        protected List<String> doInBackground(Void... voids)
        {
            List<String> posterArray = new ArrayList<String>();

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try
            {
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
                if (inputStream == null)
                {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null)
                {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (urlConnection != null)
                {
                    urlConnection.disconnect();
                }

                if (buffer.length() == 0)
                {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                // Parse the JSON data
                try
                {
                    String bufferString = buffer.toString();
                    posterArray = getMoviePosterFromJson(bufferString);
                    mMovieTitleArray = getMovieTitleFromJson(bufferString);
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
                mMoviePosterArray = new String[moviePosterResultStr.size()];
                mMoviePosterArray = moviePosterResultStr.toArray(mMoviePosterArray);

                GridView gridview = (GridView) getActivity().findViewById(R.id.movie_fragment_grid_view);
                gridview.setAdapter(new MoviePosterAdapter(getActivity(), mMoviePosterArray));

                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
                    {
                        try
                        {
                            String movieData = mMovieJsonArray.getJSONObject(position).toString();
                            Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, movieData);
                            startActivity(intent);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

}
