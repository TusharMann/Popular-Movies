package com.example.tushar.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tushar on 27-10-2016.
 */
public class Reviews {

    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
