package com.example.tushar.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Tushar on 14-10-2016.
 */
public interface ApiClientInterface {

    @GET("movie/popular?api_key=52a1dc564a183650a3b560723582b6f6")
    Call<MovieJsonObject> getPopularMovies();

    @GET("movie/top_rated?api_key=52a1dc564a183650a3b560723582b6f6")
    Call<MovieJsonObject> getTopRated();

    @GET("movie/{id}/videos?api_key=59276f8d407daa49e874d6b17ffe603c")
    Call<VideoKeyJsonObject> getVideokey(@Path("id") String id);
}
