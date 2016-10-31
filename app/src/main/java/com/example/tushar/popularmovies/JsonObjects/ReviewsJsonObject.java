package com.example.tushar.popularmovies.JsonObjects;

import com.example.tushar.popularmovies.Models.Reviews;

import java.util.ArrayList;

/**
 * Created by Tushar on 27-10-2016.
 */
public class ReviewsJsonObject {

    private int id;

    private ArrayList<Reviews> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Reviews> getResults() {
        return results;
    }

    public void setResults(ArrayList<Reviews> results) {
        this.results = results;
    }
}
