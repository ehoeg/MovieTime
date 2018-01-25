package com.example.hoege1.movietime.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by hoege1 on 12/4/17.
 */

public class MovieProvider extends ContentProvider
{
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mMovieHelper;

    static final int FAVORITE = 100;
    static final int NOW_PLAYING = 200;
    static final int TOP_RATED = 300;
    static final int POPULAR = 400;

    @Override
    public boolean onCreate()
    {
        mMovieHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {
        Cursor retCursor;
        switch (sUriMatcher.match(uri))
        {
            case FAVORITE:
            {
                retCursor = mMovieHelper.getReadableDatabase().query(
                        MovieContract.FavoriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case NOW_PLAYING:
            {
                retCursor = mMovieHelper.getReadableDatabase().query(
                        MovieContract.NowPlayingEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case TOP_RATED:
            {
                retCursor = mMovieHelper.getReadableDatabase().query(
                        MovieContract.TopRatedEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case POPULAR:
            {
                retCursor = mMovieHelper.getReadableDatabase().query(
                        MovieContract.PopularEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Register the cursor to notify anyone using the content provider if a query is made
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match)
        {
            case FAVORITE:
                return MovieContract.FavoriteEntry.CONTENT_TYPE;
            case NOW_PLAYING:
                return MovieContract.NowPlayingEntry.CONTENT_TYPE;
            case TOP_RATED:
                return MovieContract.TopRatedEntry.CONTENT_TYPE;
            case POPULAR:
                return MovieContract.PopularEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues)
    {
        final SQLiteDatabase db = mMovieHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match)
        {
            case FAVORITE:
            {
                long _id = db.insert(MovieContract.FavoriteEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                {
                    returnUri = MovieContract.FavoriteEntry.buildFavoriteUri(_id);
                }
                else
                {
                    returnUri = MovieContract.FavoriteEntry.buildFavoriteUri(_id);
                    //throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            case NOW_PLAYING:
            {
                long _id = db.insert(MovieContract.NowPlayingEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                {
                    returnUri = MovieContract.NowPlayingEntry.buildNowPlayingUri(_id);
                }
                else
                {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            case TOP_RATED:
            {
                long _id = db.insert(MovieContract.TopRatedEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                {
                    returnUri = MovieContract.TopRatedEntry.buildTopRatedUri(_id);
                }
                else
                {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            case POPULAR:
            {
                long _id = db.insert(MovieContract.PopularEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                {
                    returnUri = MovieContract.PopularEntry.buildPopularUri(_id);
                }
                else
                {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify anyone listening if a change to the database has been made
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        final SQLiteDatabase db = mMovieHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (selection == null)
        {
            selection = "1";
        }
        switch (match) {
            case FAVORITE:
                rowsDeleted = db.delete(MovieContract.FavoriteEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case NOW_PLAYING:
                rowsDeleted = db.delete(MovieContract.NowPlayingEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case TOP_RATED:
                rowsDeleted = db.delete(MovieContract.TopRatedEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case POPULAR:
                rowsDeleted = db.delete(MovieContract.PopularEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0)
        {
            // Notify anyone listening if a change to the database has been made
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        final SQLiteDatabase db = mMovieHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case FAVORITE:
                rowsUpdated = db.update(MovieContract.FavoriteEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case NOW_PLAYING:
                rowsUpdated = db.update(MovieContract.NowPlayingEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case TOP_RATED:
                rowsUpdated = db.update(MovieContract.TopRatedEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case POPULAR:
                rowsUpdated = db.update(MovieContract.PopularEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0)
        {
            // Notify anyone listening if a change to the database has been made
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_FAVORITE, FAVORITE);
        matcher.addURI(authority, MovieContract.PATH_NOW_PLAYING, NOW_PLAYING);
        matcher.addURI(authority, MovieContract.PATH_TOP_RATED, TOP_RATED);
        matcher.addURI(authority, MovieContract.PATH_POPULAR, POPULAR);

        // For each type of URI you want to add, create a corresponding code.
        return matcher;
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mMovieHelper.close();
        super.shutdown();
    }
}
