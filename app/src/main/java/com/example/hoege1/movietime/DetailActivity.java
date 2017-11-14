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
                try
                {
                    // Display the movie poster
                    String moviePosterPath = movieHelper.getMoviePosterFromJson(movieDataStr);
                    Picasso.with(this).load(moviePosterPath).into((ImageView) findViewById(R.id.detail_movie_image_view));

                    // Display the movie title
                    String movieTitle = movieHelper.getMovieTitleFromJson(movieDataStr);
                    TextView textView = this.findViewById(R.id.detail_movie_text_view);
                    textView.setText(movieTitle);
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundColor(Color.rgb(0,100,0));
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
