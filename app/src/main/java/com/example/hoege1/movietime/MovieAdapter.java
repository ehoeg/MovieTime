package com.example.hoege1.movietime;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by hoege1 on 12/10/17.
 */

public class MovieAdapter extends CursorAdapter
{
    public static class ViewHolder
    {
        public final ImageView posterView;

        public ViewHolder(View view)
        {
            posterView = (ImageView) view.findViewById(R.id.movie_poster_image_view);
        }
    }

    public MovieAdapter(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        // Read poster image url from cursor
        String posterPath = cursor.getString(MovieFragment.COL_POSTER_PATH);

        // Use picasso to load the grid view
        Picasso.with(context).load(posterPath).into((ImageView) viewHolder.posterView);
    }
}
