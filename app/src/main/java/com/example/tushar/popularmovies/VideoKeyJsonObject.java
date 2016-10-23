package com.example.tushar.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Tushar on 24-10-2016.
 */
public class VideoKeyJsonObject {

    @SerializedName("id")
    int id;

    @SerializedName("result")
    ArrayList<VideoKey> result;

    public ArrayList<VideoKey> getResult() {
        return result;
    }
}
