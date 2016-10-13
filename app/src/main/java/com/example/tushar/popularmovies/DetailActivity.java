package com.example.tushar.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView name,rating,overview,release;
    ImageView icon;
    String baseUrl = "http://image.tmdb.org/t/p/w342";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        name=(TextView)findViewById(R.id.movie_name);
        rating=(TextView)findViewById(R.id.movie_rating);
        overview=(TextView)findViewById(R.id.movie_overview);
        release=(TextView)findViewById(R.id.movie_release);
        icon=(ImageView)findViewById(R.id.movie_icon);

        Intent i=getIntent();
        Movie movie=(Movie)i.getSerializableExtra("movie object");
        String posterpath=movie.getPosterPath();

        name.setText("Movie Title\n"+movie.getTitle());
        rating.setText("Movie Rating \n"+movie.getRating());
        overview.setText(movie.getDescription());
        release.setText("Release Date\n"+movie.getReleaseDate());

        Picasso.with(this)
                .load(baseUrl+posterpath+"?api_key=52a1dc564a183650a3b560723582b6f6")
                .into(icon);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
