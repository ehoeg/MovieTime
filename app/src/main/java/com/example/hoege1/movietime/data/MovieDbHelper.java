package com.example.hoege1.movietime.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

/**
 * Created by hoege1 on 12/4/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper
{
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        // Create a table to hold the movie details
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE" + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.PopularEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.MovieEntry.COLUMN_VOTE_COUNT + "INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_ID + "INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_VIDEO + "TEXT NOT NULL" +
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + "INTEGER NOT NULL" +
                MovieContract.MovieEntry.COLUMN_TITLE + "TEXT NOT NULL" +
                MovieContract.MovieEntry.COLUMN_POPULARITY + "REAL NOT NULL" +
                MovieContract.MovieEntry.COLUMN_POSTER_PATH + "TEXT NOT NULL" +
                MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE + "TEXT NOT NULL" +
                MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE + "TEXT NOT NULL" +
                MovieContract.MovieEntry.COLUMN_GENRE_IDS + "INTEGER NOT NULL" +
                MovieContract.MovieEntry.COLUMN_BACKDROP_PATHS +"TEXT NOT NULL" +
                MovieContract.MovieEntry.COLUMN_ADULT + "TEXT NOT NULL" +
                MovieContract.MovieEntry.COLUMN_OVERVIEW + "TEXT NOT NULL" +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE + "TEXT NOT NULL );";


        // Create a table to hold popular movies
        final String SQL_CREATE_POPULAR_MOVIES_TABLE = "CREATE TABLE " + MovieContract.PopularEntry.TABLE_NAME + " (" +
                MovieContract.PopularEntry._ID + " INTEGER PRIMARY KEY," +

                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + MovieContract.PopularEntry.COLUMN_MOVIE_KEY + ") REFERENCES " +
                MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry._ID + "), " +
                " );";

        // Create a table to hold now playing movies
        final String SQL_CREATE_NOW_PLAYING_MOVIES_TABLE = "CREATE TABLE " + MovieContract.NowPlayingEntry.TABLE_NAME + " (" +
                MovieContract.NowPlayingEntry._ID + " INTEGER PRIMARY KEY," +

                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + MovieContract.NowPlayingEntry.COLUMN_MOVIE_KEY + ") REFERENCES " +
                MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry._ID + "), " +
                " );";

        // Create a table to hold popular movies
        final String SQL_CREATE_TOP_RATED_MOVIES_TABLE = "CREATE TABLE " + MovieContract.NowPlayingEntry.TABLE_NAME + " (" +
                MovieContract.NowPlayingEntry._ID + " INTEGER PRIMARY KEY," +

                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + MovieContract.TopRatedEntry.COLUMN_MOVIE_KEY + ") REFERENCES " +
                MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry._ID + "), " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_POPULAR_MOVIES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_NOW_PLAYING_MOVIES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TOP_RATED_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.PopularEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.TopRatedEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.NowPlayingEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
