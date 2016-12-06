package com.example.tushar.popularmovies;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class Favourite_Movies extends Fragment {

    View v;
    Database sqlite;
    SQLiteDatabase db;
    ArrayList<com.example.tushar.popularmovies.Movie> list;
    GridView listView;
    Favourite_Adapter adapter;

    OnDataPass dataPasser;

    public interface OnDataPass {
        public void onDataPass(Movie m);
    }


    public Favourite_Movies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v=inflater.inflate(R.layout.fragment_favourite__movies, container, false);

        sqlite=new Database(getContext(),1);
        db=sqlite.getWritableDatabase();
        list=new ArrayList<com.example.tushar.popularmovies.Movie>();
        listView=(GridView) v.findViewById(R.id.favourite_list) ;
        adapter=new Favourite_Adapter(getContext(),list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dataPasser.onDataPass(list.get(i));
            }
        });

        String[] columns={Database.id,Database.title,Database.release,Database.description,Database.rating,Database.poster};
        Cursor cursor=db.query(Database.Tname,columns,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            com.example.tushar.popularmovies.Movie movie=new com.example.tushar.popularmovies.Movie();

            int index1=cursor.getColumnIndex(Database.id);
            int index2=cursor.getColumnIndex(Database.title);
            int index3=cursor.getColumnIndex(Database.release);
            int index4=cursor.getColumnIndex(Database.description);
            int index5=cursor.getColumnIndex(Database.rating);
            int index6=cursor.getColumnIndex(Database.poster);

            movie.setId(cursor.getString(index1));
            movie.setTitle(cursor.getString(index2));
            movie.setReleaseDate(cursor.getString(index3));
            movie.setDescription(cursor.getString(index4));
            movie.setRating(cursor.getString(index5));
            movie.setPosterPath(cursor.getString(index6));

            list.add(movie);
        }
        adapter.notifyDataSetChanged();



        return v;
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }

}
