package com.example.tushar.popularmovies;

import android.content.Context;
import android.graphics.Movie;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tushar on 01-11-2016.
 */
public class Favourite_Adapter extends ArrayAdapter<Movie> {

    Context context;
    ArrayList<Movie> favoutitelist;

    public Favourite_Adapter(Context context, ArrayList<Movie> list) {
        super(context,0,list);
        this.context = context;
        this.favoutitelist = list;
    }

    public class ViewHolder {
        TextView title;
        TextView rating;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.favourites_adapter_layout, null);

            ViewHolder vh = new ViewHolder();

            vh.title = (TextView) convertView.findViewById(R.id.movie_title);
            vh.rating = (TextView) convertView.findViewById(R.id.movie_rating);


            convertView.setTag(vh);

        }

        ViewHolder vh = (ViewHolder) convertView.getTag();

        Mov movie =(Movie) getItem(position);
        vh.title.setText(movie.);




        return convertView;
    }




}
