package com.example.tushar.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Popular_Movies_fragment.OnDataPass,Highest_Rated_fragment.OnDataPass,Favourite_Movies.OnDataPass {

    int counter;
    FrameLayout container1,container2;
    Movie film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        container1=(FrameLayout)findViewById(R.id.mainActivity_framelayout);
        container2=(FrameLayout)findViewById(R.id.detail_activity_framelayout);
        Boolean connected=isNetworkConnected();

        if(connected) {
            if (savedInstanceState != null) {
                counter = savedInstanceState.getInt("counter");
                if (counter == 1) {
                    Popular_Movies_fragment fragment = new Popular_Movies_fragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout, fragment).commit();
                    setTitle("Popular Movies");
                } else if (counter == 0) {
                    Highest_Rated_fragment fragment = new Highest_Rated_fragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout, fragment).commit();
                    setTitle("Highest Rated Movies");
                } else if (counter == 2) {
                    Favourite_Movies fragment = new Favourite_Movies();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout, fragment).commit();
                    setTitle("Favourite Movies");

                }

            } else {

                Popular_Movies_fragment fragment = new Popular_Movies_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout, fragment).commit();
                counter = 1;
            }
        }

        else{
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("counter",counter);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {

        if(counter!=1) {
            Toast.makeText(this, "Press once again to exit", Toast.LENGTH_LONG).show();
            counter=1;
        }

        else
            super.onBackPressed();


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.top_rated) {
            counter=0;
            Highest_Rated_fragment fragment=new Highest_Rated_fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout,fragment).commit();
            setTitle("Highest Rated Movies");
            return true;
        }

        else if(id == R.id.popular){
            counter=1;
            Popular_Movies_fragment fragment=new Popular_Movies_fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout,fragment).commit();
            setTitle("Popular Movies");

        }

        else if(id == R.id.favourite){
            counter=2;
            Favourite_Movies fragment=new Favourite_Movies();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout,fragment).commit();
            setTitle("Favourite Movies");

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataPass(Movie m) {
        film=(Movie)m;
        Log.i("Name",film.getTitle());

        if(container2==null){
            Intent intent = new Intent();
            intent.setClass(this, DetailActivity.class);
            intent.putExtra("movie object", film);
            startActivity(intent);

        }

        else{
            Bundle b=new Bundle();
            b.putSerializable("selectedMovieObjectForFragment",film);
            DetailFragment fragment=new DetailFragment();
            fragment.setArguments(b);

            getSupportFragmentManager().beginTransaction().replace(R.id.detail_activity_framelayout,fragment).commit();

        }


    }
}
