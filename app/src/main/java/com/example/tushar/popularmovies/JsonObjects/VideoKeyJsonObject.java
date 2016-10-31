package com.example.tushar.popularmovies.JsonObjects;

import com.example.tushar.popularmovies.Models.VideoKey;

import java.util.ArrayList;

/**
 * Created by Tushar on 24-10-2016.
 */
public class VideoKeyJsonObject {

    private int id;

    private ArrayList<VideoKey> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<VideoKey> getResults() {
        return results;
    }

    public void setResults(ArrayList<VideoKey> results) {
        this.results = results;
    }
}
