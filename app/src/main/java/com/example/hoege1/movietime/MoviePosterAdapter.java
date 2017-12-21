package com.example.hoege1.movietime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by hoege1 on 11/12/17.
 */

public class MoviePosterAdapter extends ArrayAdapter

{
    private Context context;
    private LayoutInflater inflater;
    private String[] imageUrls;

    public MoviePosterAdapter(Context context, String[] imageUrls)
    {
        super(context, R.layout.list_item_movie, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (null == convertView)
        {
            convertView = inflater.inflate(R.layout.list_item_movie, parent, false);
        }

        Picasso.with(context).load(imageUrls[position]).into((ImageView) convertView);

        return convertView;
    }
}
