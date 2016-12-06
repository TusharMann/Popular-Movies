 package com.example.tushar.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Tushar on 01-11-2016.
 */
public class Favourite_Adapter extends ArrayAdapter<com.example.tushar.popularmovies.Movie> {

    Context context;
    ArrayList<com.example.tushar.popularmovies.Movie> favoutitelist;
    String baseUrl = "http://image.tmdb.org/t/p/w342";

    public Favourite_Adapter(Context context, ArrayList<com.example.tushar.popularmovies.Movie> list) {
        super(context,0,list);
        this.context = context;
        this.favoutitelist = list;
    }

    public class ViewHolder {
        TextView title;
        ImageView poster;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.favourites_adapter_layout, null);

            ViewHolder vh = new ViewHolder();

            vh.title = (TextView) convertView.findViewById(R.id.movie_title);
            vh.poster = (ImageView) convertView.findViewById(R.id.movie_icon);


            convertView.setTag(vh);

        }

        ViewHolder vh = (ViewHolder) convertView.getTag();

        Movie movie=(Movie)getItem(position);
        String posterpath=movie.getPosterPath();
        vh.title.setText(movie.getTitle());
        Picasso.with(getContext())
                .load(baseUrl+posterpath+"?api_key=52a1dc564a183650a3b560723582b6f6")
                .into(vh.poster);




        return convertView;
    }




}
