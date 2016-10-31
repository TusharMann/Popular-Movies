package com.example.tushar.popularmovies.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tushar.popularmovies.Fragments.Highest_Rated_fragment;
import com.example.tushar.popularmovies.Fragments.Popular_Movies_fragment;
import com.example.tushar.popularmovies.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Popular_Movies_fragment fragment=new Popular_Movies_fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout,fragment).commit();
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
            Highest_Rated_fragment fragment=new Highest_Rated_fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout,fragment).commit();
            setTitle("Highest Rated Movies");
            return true;
        }

        else if(id == R.id.popular){
            Popular_Movies_fragment fragment=new Popular_Movies_fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout,fragment).commit();
            setTitle("Popular Movies");

        }

        return super.onOptionsItemSelected(item);
    }
}
