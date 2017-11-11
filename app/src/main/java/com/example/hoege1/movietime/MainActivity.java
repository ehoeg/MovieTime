package com.example.hoege1.movietime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FetchMovieData movieTask = new FetchMovieData();
        movieTask.execute();

        setContentView(R.layout.activity_main);
    }
}
