package com.example.tushar.popularmovies;

import android.app.Activity;
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


public class Highest_Rated_fragment extends Fragment {
    ArrayList<Movie> movieList;
    Movie_Adapter adapter;
    GridView gridView;
    ProgressDialog progressDialog;
    OnDataPass dataPasser;

    public interface OnDataPass {
        public void onDataPass(Movie m);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_highest__rated_fragment, container, false);

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

                dataPasser.onDataPass(movieList.get(i));

            }
        });

        Call<MovieJsonObject> jsonObject = ApiClient.getInterface().getTopRated();

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
                Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }
    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }


}
