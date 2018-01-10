package com.example.hoege1.movietime.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hoege1 on 12/4/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper
{
    private final String LOG_TAG = MovieDbHelper.class.getSimpleName();

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie";

    public MovieDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        // Create a table to hold the movie details
        //final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE" + MovieContract.FavoriteEntry.TABLE_NAME + " (" +
        //        MovieContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY," +
        //        MovieContract.FavoriteEntry.COLUMN_POPULAR_ID + "INTEGER, " +
        //        MovieContract.FavoriteEntry.COLUMN_TOP_RATED_ID + "INTEGER, " +
        //        MovieContract.FavoriteEntry.COLUMN_NOW_PLAYING_ID + "INTEGER );";


        // Create a table to hold popular movies
        final String SQL_CREATE_POPULAR_MOVIES_TABLE = "CREATE TABLE IF NOT EXISTS " + MovieContract.PopularEntry.TABLE_NAME + " (" +
                MovieContract.PopularEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.PopularEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_VIDEO + " INTEGER NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_VOTE_AVERAGE + " INTEGER NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                //MovieContract.PopularEntry.COLUMN_GENRE_IDS + " INTEGER NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_BACKDROP_PATHS + " TEXT NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_ADULT + " INTEGER NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.PopularEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL );";

        // Create a table to hold now playing movies
        final String SQL_CREATE_NOW_PLAYING_MOVIES_TABLE = "CREATE TABLE IF NOT EXISTS " + MovieContract.NowPlayingEntry.TABLE_NAME + " (" +
                MovieContract.NowPlayingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.NowPlayingEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_VIDEO + " INTEGER NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                //MovieContract.NowPlayingEntry.COLUMN_GENRE_IDS + "INTEGER NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_BACKDROP_PATHS + " TEXT NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_ADULT + " INTEGER NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.NowPlayingEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL );";

        // Create a table to hold top rated movies
        final String SQL_CREATE_TOP_RATED_MOVIES_TABLE = "CREATE TABLE IF NOT EXISTS " + MovieContract.TopRatedEntry.TABLE_NAME + " (" +
                MovieContract.TopRatedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.TopRatedEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_VIDEO + " INTEGER NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                //MovieContract.TopRatedEntry.COLUMN_GENRE_IDS + "INTEGER NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_BACKDROP_PATHS + " TEXT NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_ADULT + " INTEGER NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.TopRatedEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL );";

        Log.d(LOG_TAG, SQL_CREATE_TOP_RATED_MOVIES_TABLE);
        Log.d(LOG_TAG, SQL_CREATE_NOW_PLAYING_MOVIES_TABLE);
        Log.d(LOG_TAG, SQL_CREATE_POPULAR_MOVIES_TABLE);

        //sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);
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
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavoriteEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.PopularEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.TopRatedEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.NowPlayingEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
