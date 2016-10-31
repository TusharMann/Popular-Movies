package com.example.tushar.popularmovies;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tushar.popularmovies.Favourite_Adapter;
import com.example.tushar.popularmovies.Database;
import com.example.tushar.popularmovies.R;

import java.util.ArrayList;

public class Favourite_Movies extends Fragment {

    View v;
    Database sqlite;
    SQLiteDatabase db;
    ArrayList<Movie> list;
    ListView listView;
    Favourite_Adapter adapter;


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
        list=new ArrayList<Movie>();
        listView=(ListView)v.findViewById(R.id.favourite_list) ;
        adapter=new Favourite_Adapter(getContext(),list);
        listView.setAdapter(adapter);

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

            movie.setId(cursor.getString(index1);
            movie.setTitle(cursor.getString(index2));
            movie.setTitle(cursor.getString(index2));
            movie.setTitle(cursor.getString(index2));
            movie.setTitle(cursor.getString(index2));
            movie.setTitle(cursor.getString(index2));

            list.add(c);
        }
        adapter.notifyDataSetChanged();



        return v;
    }


}
