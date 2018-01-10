package com.example.hoege1.movietime;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hoege1.movietime.data.MovieContract;

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
 * Created by hoege1 on 12/10/17.
 */

public class FetchMovieTask extends AsyncTask<Void, Void, Void>
{
    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
    private String mQueryString;

    // Construct the URL for the movie database
    final String FORECAST_BASE_URL = "https://api.themoviedb.org/3";
    final String LANGUAGE_STRING = "en-US";
    final String PAGE_NUM_STRING = "1";
    final String API_KEY_STRING = "7f85df63fe917ac57f3c002f0b52ede6";

    private final Context mContext;

    public FetchMovieTask(Context context, String queryString)
    {
        mContext = context;
        mQueryString = queryString;
    }

    @Override
        protected Void doInBackground(Void... voids)
    {
        List<String> posterArray = new ArrayList<String>();

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String QUERY_STRING = "movie/now_playing";
        if(mQueryString.equals("Now Playing"))
        {
            QUERY_STRING = "movie/now_playing";
        }
        else if(mQueryString.equals("Popular"))
        {
            QUERY_STRING = "movie/popular";
        }
        else if(mQueryString.equals("Top Rated"))
        {
            QUERY_STRING = "movie/top_rated";
        }

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
                JSONObject movieJson = new JSONObject(bufferString);
                JSONArray resultsArray = movieJson.getJSONArray("results");

                for(int i=0; i<resultsArray.length(); i++)
                {
                    JSONObject movieData = resultsArray.getJSONObject(i);

                    ContentValues movieValues = new ContentValues();

                    if(mQueryString.equals("Now Playing"))
                    {
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_VOTE_COUNT, movieData.getInt("vote_count"));
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_MOVIE_ID, movieData.getInt("id"));
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_VIDEO, movieData.getBoolean("video"));
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_VOTE_AVERAGE, movieData.getDouble("vote_average"));
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_TITLE, movieData.getString("title"));
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_POPULARITY, movieData.getDouble("popularity"));
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_POSTER_PATH, "http://image.tmdb.org/t/p/w780" + movieData.get("poster_path").toString());
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_ORIGINAL_LANGUAGE, movieData.getString("original_language"));
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_ORIGINAL_TITLE, movieData.getString("original_title"));
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_BACKDROP_PATHS, movieData.getString("backdrop_path"));
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_ADULT, movieData.getBoolean("adult"));
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_OVERVIEW, movieData.getString("overview"));
                        movieValues.put(MovieContract.NowPlayingEntry.COLUMN_RELEASE_DATE, movieData.getString("release_date"));

                        // Add new entry
                        mContext.getContentResolver().insert(MovieContract.NowPlayingEntry.CONTENT_URI, movieValues);
                    }
                    else if(mQueryString.equals("Top Rated"))
                    {
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_VOTE_COUNT, movieData.getInt("vote_count"));
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_MOVIE_ID, movieData.getInt("id"));
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_VIDEO, movieData.getBoolean("video"));
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_VOTE_AVERAGE, movieData.getDouble("vote_average"));
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_TITLE, movieData.getString("title"));
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_POPULARITY, movieData.getDouble("popularity"));
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_POSTER_PATH, "http://image.tmdb.org/t/p/w780" + movieData.get("poster_path").toString());
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_ORIGINAL_LANGUAGE, movieData.getString("original_language"));
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_ORIGINAL_TITLE, movieData.getString("original_title"));
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_BACKDROP_PATHS, movieData.getString("backdrop_path"));
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_ADULT, movieData.getBoolean("adult"));
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_OVERVIEW, movieData.getString("overview"));
                        movieValues.put(MovieContract.TopRatedEntry.COLUMN_RELEASE_DATE, movieData.getString("release_date"));

                        // add new entry
                        mContext.getContentResolver().insert(MovieContract.TopRatedEntry.CONTENT_URI, movieValues);
                    }
                    else if(mQueryString.equals("Popular"))
                    {
                        movieValues.put(MovieContract.PopularEntry.COLUMN_VOTE_COUNT, movieData.getInt("vote_count"));
                        movieValues.put(MovieContract.PopularEntry.COLUMN_MOVIE_ID, movieData.getInt("id"));
                        movieValues.put(MovieContract.PopularEntry.COLUMN_VIDEO, movieData.getBoolean("video"));
                        movieValues.put(MovieContract.PopularEntry.COLUMN_VOTE_AVERAGE, movieData.getDouble("vote_average"));
                        movieValues.put(MovieContract.PopularEntry.COLUMN_TITLE, movieData.getString("title"));
                        movieValues.put(MovieContract.PopularEntry.COLUMN_POPULARITY, movieData.getDouble("popularity"));
                        movieValues.put(MovieContract.PopularEntry.COLUMN_POSTER_PATH, "http://image.tmdb.org/t/p/w780" + movieData.get("poster_path").toString());
                        movieValues.put(MovieContract.PopularEntry.COLUMN_ORIGINAL_LANGUAGE, movieData.getString("original_language"));
                        movieValues.put(MovieContract.PopularEntry.COLUMN_ORIGINAL_TITLE, movieData.getString("original_title"));
                        movieValues.put(MovieContract.PopularEntry.COLUMN_BACKDROP_PATHS, movieData.getString("backdrop_path"));
                        movieValues.put(MovieContract.PopularEntry.COLUMN_ADULT, movieData.getBoolean("adult"));
                        movieValues.put(MovieContract.PopularEntry.COLUMN_OVERVIEW, movieData.getString("overview"));
                        movieValues.put(MovieContract.PopularEntry.COLUMN_RELEASE_DATE, movieData.getString("release_date"));

                        // add new entry
                        mContext.getContentResolver().insert(MovieContract.PopularEntry.CONTENT_URI, movieValues);
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        catch(IOException e)
        {
            Log.e(LOG_TAG, "Error ", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
    }
}
