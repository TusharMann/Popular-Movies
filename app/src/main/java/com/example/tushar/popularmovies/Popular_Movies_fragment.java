package com.example.tushar.popularmovies;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Popular_Movies_fragment extends Fragment {
    ArrayList<Movie> movieList;
    Movie_Adapter adapter;
    GridView gridView;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_popular__movies_fragment, container, false);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        gridView=(GridView)view.findViewById(R.id.popular_movie_gridview);
        movieList=new ArrayList<Movie>();
        adapter=new Movie_Adapter(getActivity(),movieList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        Call<MovieJsonObject> jsonObject = ApiClient.getInterface().getPopularMovies();

        jsonObject.enqueue(new Callback<MovieJsonObject>() {
            @Override
            public void onResponse(Call<MovieJsonObject> call, Response<MovieJsonObject> response) {
                MovieJsonObject jsonObject1 = response.body();

                for (int i = 0; i < jsonObject1.getMovieList().size(); i++)
                    movieList.add(jsonObject1.getMovieList().get(i));

               Log.i("movie data", String.valueOf(movieList.size()));

                adapter.notifyDataSetChanged();
                progressDialog.hide();
            }
            @Override
            public void onFailure(Call<MovieJsonObject> call, Throwable t) {
                Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG);

            }
        });





            return view;
    }


}
