package com.example.hoege1.movietime.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.hoege1.movietime.R;

/**
 * Created by hoege1 on 12/4/17.
 */

public class MovieContract
{
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.hoege1.movietime";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVORITE = "favorite";
    public static final String PATH_POPULAR = "popular";
    public static final String PATH_NOW_PLAYING = "now_playing";
    public static final String PATH_TOP_RATED = "top_rated";

    // These indices are tied to the now playing, top rated, and popular tables
    // If these columns change these indices must also change
    public static final int COL_ROW_ID = 0;
    public static final int COL_NOW_PLAYING_ID = 0;
    public static final int COL_TOP_RATED_ID = 0;
    public static final int COL_POPULAR_ID = 0;
    public static final int COL_VOTE_COUNT = 1;
    public static final int COL_ID = 2;
    public static final int COL_VIDEO = 3;
    public static final int COL_VOTE_AVERAGE = 4;
    public static final int COL_TITLE = 5;
    public static final int COL_POPULARITY = 6;
    public static final int COL_POSTER_PATH = 7;
    public static final int COL_ORIGINAL_LANGUAGE = 8;
    public static final int COL_ORIGINAL_TITLE = 9;
    public static final int COL_BACKDROP_PATHS = 10;
    public static final int COL_ADULT = 11;
    public static final int COL_OVERVIEW = 12;
    public static final int COL_RELEASE_DATE = 13;

    public static final class FavoriteEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        // Table name
        public static final String TABLE_NAME = "favorite";

        // Columns
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_GENRE_IDS = "genre_ids";
        public static final String COLUMN_BACKDROP_PATHS = "backdrop_paths";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static String[] FAVORITE_COLUMNS = {
                // In this case the id needs to be fully qualified with a table name, since
                // the content provider joins the location & weather tables in the background
                // (both have an _id column)
                // On the one hand, that's annoying.  On the other, you can search the weather table
                // using the location set by the user, which is only in the Location table.
                // So the convenience is worth it.
                MovieContract.FavoriteEntry.TABLE_NAME + "." + MovieContract.FavoriteEntry._ID,
                MovieContract.FavoriteEntry.COLUMN_VOTE_COUNT,
                MovieContract.FavoriteEntry.COLUMN_MOVIE_ID,
                MovieContract.FavoriteEntry.COLUMN_VIDEO,
                MovieContract.FavoriteEntry.COLUMN_VOTE_AVERAGE,
                MovieContract.FavoriteEntry.COLUMN_TITLE,
                MovieContract.FavoriteEntry.COLUMN_POPULARITY,
                MovieContract.FavoriteEntry.COLUMN_POSTER_PATH,
                MovieContract.FavoriteEntry.COLUMN_ORIGINAL_LANGUAGE,
                MovieContract.FavoriteEntry.COLUMN_ORIGINAL_TITLE,
                MovieContract.FavoriteEntry.COLUMN_BACKDROP_PATHS,
                MovieContract.FavoriteEntry.COLUMN_ADULT,
                MovieContract.FavoriteEntry.COLUMN_OVERVIEW,
                MovieContract.FavoriteEntry.COLUMN_RELEASE_DATE
        };

        public static Uri buildFavoriteUri(long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class NowPlayingEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_NOW_PLAYING).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOW_PLAYING;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOW_PLAYING;

        // Table name
        public static final String TABLE_NAME = "now_playing";

        // Columns
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_GENRE_IDS = "genre_ids";
        public static final String COLUMN_BACKDROP_PATHS = "backdrop_paths";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static String[] NOW_PLAYING_COLUMNS = {
                // In this case the id needs to be fully qualified with a table name, since
                // the content provider joins the location & weather tables in the background
                // (both have an _id column)
                // On the one hand, that's annoying.  On the other, you can search the weather table
                // using the location set by the user, which is only in the Location table.
                // So the convenience is worth it.
                MovieContract.NowPlayingEntry.TABLE_NAME + "." + MovieContract.NowPlayingEntry._ID,
                MovieContract.NowPlayingEntry.COLUMN_VOTE_COUNT,
                MovieContract.NowPlayingEntry.COLUMN_MOVIE_ID,
                MovieContract.NowPlayingEntry.COLUMN_VIDEO,
                MovieContract.NowPlayingEntry.COLUMN_VOTE_AVERAGE,
                MovieContract.NowPlayingEntry.COLUMN_TITLE,
                MovieContract.NowPlayingEntry.COLUMN_POPULARITY,
                MovieContract.NowPlayingEntry.COLUMN_POSTER_PATH,
                MovieContract.NowPlayingEntry.COLUMN_ORIGINAL_LANGUAGE,
                MovieContract.NowPlayingEntry.COLUMN_ORIGINAL_TITLE,
                MovieContract.NowPlayingEntry.COLUMN_BACKDROP_PATHS,
                MovieContract.NowPlayingEntry.COLUMN_ADULT,
                MovieContract.NowPlayingEntry.COLUMN_OVERVIEW,
                MovieContract.NowPlayingEntry.COLUMN_RELEASE_DATE
        };

        public static Uri buildNowPlayingUri(long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TopRatedEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOP_RATED).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOP_RATED;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TOP_RATED;

        // Table name
        public static final String TABLE_NAME = "top_rated";

        // Columns
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_GENRE_IDS = "genre_ids";
        public static final String COLUMN_BACKDROP_PATHS = "backdrop_paths";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static String[] TOP_RATED_COLUMNS = {
                // In this case the id needs to be fully qualified with a table name, since
                // the content provider joins the location & weather tables in the background
                // (both have an _id column)
                // On the one hand, that's annoying.  On the other, you can search the weather table
                // using the location set by the user, which is only in the Location table.
                // So the convenience is worth it.
                MovieContract.TopRatedEntry.TABLE_NAME + "." + MovieContract.TopRatedEntry._ID,
                MovieContract.TopRatedEntry.COLUMN_VOTE_COUNT,
                MovieContract.TopRatedEntry.COLUMN_MOVIE_ID,
                MovieContract.TopRatedEntry.COLUMN_VIDEO,
                MovieContract.TopRatedEntry.COLUMN_VOTE_AVERAGE,
                MovieContract.TopRatedEntry.COLUMN_TITLE,
                MovieContract.TopRatedEntry.COLUMN_POPULARITY,
                MovieContract.TopRatedEntry.COLUMN_POSTER_PATH,
                MovieContract.TopRatedEntry.COLUMN_ORIGINAL_LANGUAGE,
                MovieContract.TopRatedEntry.COLUMN_ORIGINAL_TITLE,
                MovieContract.TopRatedEntry.COLUMN_BACKDROP_PATHS,
                MovieContract.TopRatedEntry.COLUMN_ADULT,
                MovieContract.TopRatedEntry.COLUMN_OVERVIEW,
                MovieContract.TopRatedEntry.COLUMN_RELEASE_DATE
        };

        public static Uri buildTopRatedUri(long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class PopularEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR;

        // Table name
        public static final String TABLE_NAME = "popular";

        // Columns
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_GENRE_IDS = "genre_ids";
        public static final String COLUMN_BACKDROP_PATHS = "backdrop_paths";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static String[] POPULAR_COLUMNS = {
                // In this case the id needs to be fully qualified with a table name, since
                // the content provider joins the location & weather tables in the background
                // (both have an _id column)
                // On the one hand, that's annoying.  On the other, you can search the weather table
                // using the location set by the user, which is only in the Location table.
                // So the convenience is worth it.
                MovieContract.PopularEntry.TABLE_NAME + "." + MovieContract.PopularEntry._ID,
                MovieContract.PopularEntry.COLUMN_VOTE_COUNT,
                MovieContract.PopularEntry.COLUMN_MOVIE_ID,
                MovieContract.PopularEntry.COLUMN_VIDEO,
                MovieContract.PopularEntry.COLUMN_VOTE_AVERAGE,
                MovieContract.PopularEntry.COLUMN_TITLE,
                MovieContract.PopularEntry.COLUMN_POPULARITY,
                MovieContract.PopularEntry.COLUMN_POSTER_PATH,
                MovieContract.PopularEntry.COLUMN_ORIGINAL_LANGUAGE,
                MovieContract.PopularEntry.COLUMN_ORIGINAL_TITLE,
                MovieContract.PopularEntry.COLUMN_BACKDROP_PATHS,
                MovieContract.PopularEntry.COLUMN_ADULT,
                MovieContract.PopularEntry.COLUMN_OVERVIEW,
                MovieContract.PopularEntry.COLUMN_RELEASE_DATE
        };

        public static Uri buildPopularUri(long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class MovieContractHelper
    {
        public static Uri getContentUri(String queryType)
        {
            Uri contentUri;
            if(queryType.equals("Top Rated"))
            {
                contentUri = MovieContract.TopRatedEntry.CONTENT_URI;
            }
            else if(queryType.equals("Now Playing"))
            {
                contentUri = MovieContract.NowPlayingEntry.CONTENT_URI;
            }
            else if(queryType.equals("Popular"))
            {
                contentUri = MovieContract.PopularEntry.CONTENT_URI;
            }
            else // Favorites
            {
                contentUri = MovieContract.FavoriteEntry.CONTENT_URI;
            }
            return contentUri;
        }

        public static String[] getProjection(String queryType)
        {
            String projection[];
            if(queryType.equals("Top Rated"))
            {
                projection = MovieContract.TopRatedEntry.TOP_RATED_COLUMNS;
            }
            else if(queryType.equals("Now Playing"))
            {
                projection = MovieContract.NowPlayingEntry.NOW_PLAYING_COLUMNS;
            }
            else if(queryType.equals("Popular"))
            {
                projection = MovieContract.PopularEntry.POPULAR_COLUMNS;
            }
            else // Favorites
            {
                projection = MovieContract.FavoriteEntry.FAVORITE_COLUMNS;
            }
            return projection;
        }
    }
}
