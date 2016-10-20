package com.example.tushar.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView name,rating,overview,release;
    ImageView icon;
    String baseUrl = "http://image.tmdb.org/t/p/w342";
    private android.support.v7.widget.ShareActionProvider mShareActionProvider;

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
        setTitle(movie.getTitle());

        Picasso.with(this)
                .load(baseUrl+posterpath+"?api_key=52a1dc564a183650a3b560723582b6f6")
                .into(icon);
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
            Toast.makeText(this,"Share Action Provider is null",Toast.LENGTH_LONG).show();
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
