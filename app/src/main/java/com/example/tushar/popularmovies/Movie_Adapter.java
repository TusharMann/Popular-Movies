package com.example.tushar.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Tushar on 14-10-2016.
 */
public class Movie_Adapter extends ArrayAdapter<Movie> {

    String baseUrl = "http://image.tmdb.org/t/p/w342";

    Context context;
    ArrayList<Movie> movies;

    public Movie_Adapter(Context context, ArrayList<Movie> movies) {
        super(context,0,movies);
        this.context = context;
        this.movies = movies;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView textView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if(convertView==null){
           convertView=View.inflate(context,R.layout.movie_adapterlayout,null);

           ViewHolder v=new ViewHolder();
           v.imageView=(ImageView)convertView.findViewById(R.id.movie_icon);
           v.textView=(TextView)convertView.findViewById(R.id.movie_name);

           convertView.setTag(v);
       }

        ViewHolder vh=(ViewHolder)convertView.getTag();
        Movie movie=(Movie)getItem(position);
        String posterpath=movie.getPosterPath();

        String name=movie.getTitle();
        Log.i("movie data",name);

        vh.textView.setText(name);
        Picasso.with(context)
                .load(baseUrl+posterpath+"?api_key=52a1dc564a183650a3b560723582b6f6")
                .into(vh.imageView);

        return convertView;
    }
}
