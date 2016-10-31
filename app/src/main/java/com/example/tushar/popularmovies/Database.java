package com.example.tushar.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tushar on 01-11-2016.
 */
public class Database extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "Favourite Movies";
    public final static String Tname = "Favourite";
    public final static String id = "ID";
    public final static String title = "Title";
    public final static String release = "Release";
    public final static String description="Description";
    public final static String rating="Rating";
    public final static String poster="Poster";


    public Database(Context context, int version){
        super(context,DATABASE_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query="CREATE TABLE "+Tname+"("+id +" VARCHAR(50),"+title+" VARCHAR(50),"+release +" VARCHAR(50),"
                                        +description +" VARCHAR(100),"+rating+" VARCHAR(50),"+poster+" VARCHAR(50));";

        db.execSQL(query);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
