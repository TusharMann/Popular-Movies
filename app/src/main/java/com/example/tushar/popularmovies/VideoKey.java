package com.example.tushar.popularmovies;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tushar on 24-10-2016.
 */
public class VideoKey {

    @SerializedName("key")
    String key;

    public String getKey() {
        return key;
    }

}
