package com.pidois.ester.api;

import com.pidois.ester.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<News> getNews(

        @Query("country") String country,
        @Query("q") String q,
        @Query("apiKey") String apiKey

    );

    @GET("everything")
    Call<News> getNewsSearch(

        @Query("q") String keyword,
        @Query("language") String language,
        @Query("apiKey") String apiKey

    );

}
