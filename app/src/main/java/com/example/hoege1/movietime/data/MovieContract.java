package com.example.hoege1.movietime.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

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

    public static final class FavoriteEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        // Table name
        public static final String TABLE_NAME = "favorite";

        public static final String COLUMN_POPULAR_ID = "popular_id";
        public static final String COLUMN_TOP_RATED_ID = "top_rated_id";
        public static final String COLUMN_NOW_PLAYING_ID = "now_playing_id";

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
        public static final String COLUMN_ID = "id";
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
        public static final String COLUMN_ID = "id";
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
        public static final String COLUMN_ID = "id";
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

        public static Uri buildPopularUri(long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}