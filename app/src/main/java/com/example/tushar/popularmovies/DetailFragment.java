package com.example.tushar.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {

    Database sqlite;
    SQLiteDatabase db;

    TextView name;
    TextView rating,overview,release;
    Button favourite,trailer1,trailer2,review;
    ImageView icon;
    ArrayList<VideoKey> keylist;
    ArrayList<Reviews> reviews;
    String baseUrl = "http://image.tmdb.org/t/p/w342";
    Boolean markasfavourite=false;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_detail, container, false);

        final Movie movie=(Movie)getArguments().getSerializable("selectedMovieObjectForFragment") ;
        sqlite=new Database(getActivity(),1);
        db=sqlite.getWritableDatabase();


        name=(TextView)v.findViewById(R.id.movie_name);
        rating=(TextView)v.findViewById(R.id.movie_rating);
        overview=(TextView)v.findViewById(R.id.movie_overview);
        release=(TextView)v.findViewById(R.id.movie_release);
        icon=(ImageView)v.findViewById(R.id.movie_icon);
        favourite=(Button)v.findViewById(R.id.favourite_button);
        trailer1=(Button)v.findViewById(R.id.trailer1);
        trailer2=(Button)v.findViewById(R.id.trailer2);
        review=(Button)v.findViewById(R.id.review);

        String posterpath=movie.getPosterPath();

        name.setText(movie.getTitle());
        rating.setText("Movie Rating \n"+movie.getRating()+"/10");
        overview.setText(movie.getDescription());
        release.setText("Release Date\n"+movie.getReleaseDate());
        favourite.setText("Mark As \n Favourite");

        checkForFavouriteMovie();

        String id=(String)movie.getId();
        keylist=new ArrayList<VideoKey>();
        reviews=new ArrayList<Reviews>();

        Picasso.with(getActivity())
                .load(baseUrl+posterpath+"?api_key=52a1dc564a183650a3b560723582b6f6")
                .into(icon);

        Call<VideoKeyJsonObject> jsonObject = ApiClient.getInterface().getVideokey(id);

        jsonObject.enqueue(new Callback<VideoKeyJsonObject>() {
            @Override
            public void onResponse(Call<VideoKeyJsonObject> call, Response<VideoKeyJsonObject> response) {
                VideoKeyJsonObject jsonObject1 = response.body();

                for (int i = 0; i < jsonObject1.getResults().size(); i++)
                    keylist.add(jsonObject1.getResults().get(i));

                Log.i("movie data", String.valueOf(keylist.size()));
            }
            @Override
            public void onFailure(Call<VideoKeyJsonObject> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();

            }
        });

        Call<ReviewsJsonObject> jsonObject2 = ApiClient.getInterface().getReviews(id);

        jsonObject2.enqueue(new Callback<ReviewsJsonObject>() {
            @Override
            public void onResponse(Call<ReviewsJsonObject> call, Response<ReviewsJsonObject> response) {
                ReviewsJsonObject jsonObject3 = response.body();

                for (int i = 0; i < jsonObject3.getResults().size(); i++)
                    reviews.add(jsonObject3.getResults().get(i));

                Log.i("movie reviews", String.valueOf(reviews.size()));
            }
            @Override
            public void onFailure(Call<ReviewsJsonObject> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();

            }
        });


        trailer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoKey keyobject=(VideoKey)keylist.get(0);
                String key=keyobject.getKey();
                String url = "http://www.youtube.com/watch?v="+key;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        trailer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoKey keyobject=(VideoKey)keylist.get(1);
                String key=keyobject.getKey();
                String url = "http://www.youtube.com/watch?v="+key;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li=LayoutInflater.from(getActivity().getApplicationContext());
                View reviewdialog=li.inflate(R.layout.reviewalertdialog,null);

                AlertDialog builder=new AlertDialog.Builder(getActivity()).create();
                builder.setView(reviewdialog);

                TextView reviewtextbox;
                reviewtextbox=(TextView) reviewdialog.findViewById(R.id.review_textbox);

                Reviews review=(Reviews)reviews.get(0);
                String abc=review.getContent();
                Log.i("movie review", abc);
                reviewtextbox.setText(abc);


                builder.setTitle("Reviews");
                builder.show();



            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(markasfavourite){
                    markasfavourite=false;
                    favourite.setText("Mark \n As Favourite");
                    favourite.setBackgroundResource(R.color.moviename);
                    Toast.makeText(getActivity(),"Movie removed from Favourite List ",Toast.LENGTH_LONG).show();

//                    String del="DELETE FROM "+Database.Tname+"WHERE ID="+movie.getId();
//                    db.execSQL(del);

                    db.delete(Database.Tname, Database.id+"="+movie.getId(), null);

                }

                else {
                    markasfavourite=true;
                    favourite.setText("Marked \n As Favourite");
                    favourite.setBackgroundResource(R.color.marked);

                    ContentValues cv = new ContentValues();
                    cv.put(Database.id, movie.getId());
                    cv.put(Database.title, movie.getTitle());
                    Log.i("Title", movie.getTitle());
                    cv.put(Database.rating, movie.getRating());
                    cv.put(Database.description, movie.getDescription());
                    cv.put(Database.poster, movie.getPosterPath());
                    cv.put(Database.release, movie.getReleaseDate());

                    db.insert(Database.Tname, null, cv);

                    Toast.makeText(getActivity(), "Movie Added in Favourtite List", Toast.LENGTH_LONG).show();
                }

            }
        });


        return v;
    }

    private void checkForFavouriteMovie() {

        Movie movie=(Movie)getArguments().getSerializable("selectedMovieObjectForFragment") ;


        String Query = "Select * from " + Database.Tname + " where " + Database.id + " = " + movie.getId();
        Cursor cursor = db.rawQuery(Query, null);

        if(cursor.getCount() <= 0){
            //means no row with this id exist, just let button as normal
            markasfavourite=false;
        }
        else
        {
            //means the row is already in the database
            favourite.setBackgroundResource(R.color.marked);
            favourite.setText("Marked \n As Favourite");
            markasfavourite=true;
        }

        cursor.close();

    }


}
