package com.example.hoege1.movietime;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoege1.movietime.data.MovieContract;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class DetailActivity extends AppCompatActivity
{
    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    private String mMovieTitle;
    private JSONObject mMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.getSupportActionBar().setTitle("Movie Details");

        Intent intent = this.getIntent();
        String movieTitle = intent.getStringExtra("movieTitle");
        String movieTable = intent.getStringExtra("queryTable");
        Uri detailUri = MovieContract.MovieContractHelper.getContentUri(movieTable);

        String selection = "original_title =?";
        String selectionArgs[] = { movieTitle.toString() };
        final Cursor cursor = this.getContentResolver().query(detailUri, MovieContract.MovieContractHelper.getProjection(movieTable), selection, selectionArgs, null);

        // Make sure we got a result
        if(cursor.moveToFirst())
        {
            // Display the movie title
            TextView movieTitleTextView = this.findViewById(R.id.detail_movie_text_view);
            movieTitleTextView.setTextColor(Color.WHITE);
            movieTitleTextView.setBackgroundColor(Color.rgb(0,100,0));
            movieTitleTextView.setText(cursor.getString(MovieContract.COL_ORIGINAL_TITLE));

            // Display the movie poster
            String moviePosterPath = cursor.getString(MovieContract.COL_POSTER_PATH);
            Picasso.with(this).load(moviePosterPath).into((ImageView) findViewById(R.id.detail_movie_image_view));

            // Display the movie description
            String movieDescription = cursor.getString(MovieContract.COL_OVERVIEW);
            TextView movieDescriptionTextView = this.findViewById(R.id.detail_movie_description_text_view);
            movieDescriptionTextView.setText(movieDescription);

            // Display the release date
            String movieReleaseDate = cursor.getString(MovieContract.COL_RELEASE_DATE);
            TextView movieReleaseDateTextView = this.findViewById(R.id.detail_movie_release_date_text_view);
            movieReleaseDateTextView.setText(movieReleaseDate);

            // Display the user rating
            String movieRating = cursor.getString(MovieContract.COL_VOTE_AVERAGE) + " / 10";
            TextView movieRatingTextView = this.findViewById(R.id.detail_movie_user_rating_text_view);
            movieRatingTextView.setText(movieRating);

            final Button favoriteButton = findViewById(R.id.favorite_button);
            favoriteButton.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    ContentValues favoriteValues = new ContentValues();

                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_VOTE_COUNT, cursor.getString(MovieContract.COL_VOTE_COUNT));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_MOVIE_ID, cursor.getString(MovieContract.COL_ID));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_VIDEO, cursor.getString(MovieContract.COL_VIDEO));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_VOTE_AVERAGE, cursor.getString(MovieContract.COL_VOTE_AVERAGE));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_TITLE, cursor.getString(MovieContract.COL_TITLE));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_POPULARITY, cursor.getString(MovieContract.COL_POPULARITY));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_POSTER_PATH, cursor.getString(MovieContract.COL_POSTER_PATH));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_ORIGINAL_LANGUAGE, cursor.getString(MovieContract.COL_ORIGINAL_LANGUAGE));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_ORIGINAL_TITLE, cursor.getString(MovieContract.COL_ORIGINAL_TITLE));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_BACKDROP_PATHS, cursor.getString(MovieContract.COL_BACKDROP_PATHS));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_ADULT, cursor.getString(MovieContract.COL_ADULT));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_OVERVIEW, cursor.getString(MovieContract.COL_OVERVIEW));
                    favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_RELEASE_DATE, cursor.getString(MovieContract.COL_RELEASE_DATE));

                    // Add new entry
                    getApplicationContext().getContentResolver().insert(MovieContract.FavoriteEntry.CONTENT_URI, favoriteValues);

                    Toast toast = Toast.makeText(getApplicationContext(), "Added movie to favorites", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }
}
