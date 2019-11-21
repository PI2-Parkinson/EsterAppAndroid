package com.pidois.ester.api;

import com.pidois.ester.Models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<News> getNews(

        @Query("country") String country,
        //@Query("sources") String sources,
        @Query("q") String q,
        @Query("excludeDomains") String excludeDomains,
        @Query("language") String language,
        @Query("apiKey") String apiKey

    );


    @GET("everything")
    Call<News> getNewsSearch(

        @Query("q") String keyword,
        @Query("excludeDomains") String excludeDomains,
        //@Query("sources") String sources,
        //@Query("qInTitle") String qInTitle,
        @Query("language") String language,
        @Query("sortBy") String sortBy,
        @Query("apiKey") String apiKey

    );

}
