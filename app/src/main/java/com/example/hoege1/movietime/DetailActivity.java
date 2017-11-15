package com.example.hoege1.movietime;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

        MovieJsonHelperFunctions movieHelper = new MovieJsonHelperFunctions();

        Intent intent = this.getIntent();
        String movieDataStr = intent.getStringExtra(Intent.EXTRA_TEXT);
        Log.d(LOG_TAG, movieDataStr);
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT))
        {
            try
            {
                // Display the movie poster
                String moviePosterPath = movieHelper.getMoviePosterFromJson(movieDataStr);
                Picasso.with(this).load(moviePosterPath).into((ImageView) findViewById(R.id.detail_movie_image_view));

                // Display the movie title
                String movieTitle = movieHelper.getMovieTitleFromJson(movieDataStr);
                TextView movieTitleTextView = this.findViewById(R.id.detail_movie_text_view);
                movieTitleTextView.setText(movieTitle);
                movieTitleTextView.setTextColor(Color.WHITE);
                movieTitleTextView.setBackgroundColor(Color.rgb(0,100,0));

                // Display the movie description
                String movieDescription = movieHelper.getMovieDescriptionFromJson(movieDataStr);
                TextView movieDescriptionTextView = this.findViewById(R.id.detail_movie_description_text_view);
                movieDescriptionTextView.setText(movieDescription);

                // Display the release date
                String movieReleaseDate = movieHelper.getMovieReleaseDateFromJson(movieDataStr);
                TextView movieReleaseDateTextView = this.findViewById(R.id.detail_movie_release_date_text_view);
                movieReleaseDateTextView.setText(movieReleaseDate);

                // Display the user rating
                String movieRating = movieHelper.getMovieUserRatingFromJson(movieDataStr);
                TextView movieRatingTextView = this.findViewById(R.id.detail_movie_user_rating_text_view);
                movieRatingTextView.setText(movieRating);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
