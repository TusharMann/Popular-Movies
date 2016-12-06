package com.example.tushar.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    Database sqlite;
    SQLiteDatabase db;

    TextView name;
    TextView rating,overview,release;
    Button favourite,trailer1,trailer2,review;
    ImageView icon;
    ArrayList<VideoKey> keylist;
    ArrayList<Reviews> reviews;
    String baseUrl = "http://image.tmdb.org/t/p/w342";
    private android.support.v7.widget.ShareActionProvider mShareActionProvider;
    Boolean markasfavourite=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        sqlite=new Database(this,1);
        db=sqlite.getWritableDatabase();


        name=(TextView)findViewById(R.id.movie_name);
        rating=(TextView)findViewById(R.id.movie_rating);
        overview=(TextView)findViewById(R.id.movie_overview);
        release=(TextView)findViewById(R.id.movie_release);
        icon=(ImageView)findViewById(R.id.movie_icon);
        favourite=(Button)findViewById(R.id.favourite_button);
        trailer1=(Button)findViewById(R.id.trailer1);
        trailer2=(Button)findViewById(R.id.trailer2);
        review=(Button)findViewById(R.id.review);

        Intent i=getIntent();
        final Movie movie=(Movie)i.getSerializableExtra("movie object");
        String posterpath=movie.getPosterPath();

        name.setText(movie.getTitle());
        rating.setText("Movie Rating \n"+movie.getRating()+"/10");
        overview.setText(movie.getDescription());
        release.setText("Release Date\n"+movie.getReleaseDate());
        favourite.setText("Mark As \n Favourite");
        setTitle("Movie Details");

        checkForFavouriteMovie();

        String id=(String)movie.getId();
        keylist=new ArrayList<VideoKey>();
        reviews=new ArrayList<Reviews>();

        Picasso.with(this)
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
                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();

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
                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();

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
                LayoutInflater li=LayoutInflater.from(getApplicationContext());
                View reviewdialog=li.inflate(R.layout.reviewalertdialog,null);

                AlertDialog builder=new AlertDialog.Builder(DetailActivity.this).create();
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
                    Toast.makeText(getApplicationContext(),"Movie removed from Favourite List ",Toast.LENGTH_LONG).show();

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

                    Toast.makeText(getApplicationContext(), "Movie Added in Favourtite List", Toast.LENGTH_LONG).show();
                }

            }
        });


    }


    private void checkForFavouriteMovie() {

        Intent i=getIntent();
        Movie movie=(Movie)i.getSerializableExtra("movie object");



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


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);

        MenuItem item=menu.findItem(R.id.share);
        mShareActionProvider = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if(mShareActionProvider!=null){
           mShareActionProvider.setShareIntent(createMovieShareIntent());
       }

        else{
           // Toast.makeText(this,"Share Action Provider is null",Toast.LENGTH_LONG).show();
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            startActivity(createMovieShareIntent());
            return true;
        }

        else if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private Intent createMovieShareIntent(){
        String moviename=name.getText().toString();
        Log.i("movien",moviename);

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setType("type/plain");
        intent.putExtra(Intent.EXTRA_TEXT,moviename);

        return intent;
    }



}
