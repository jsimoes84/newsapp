package com.jsimoes.newsheadlines.network;

import com.jsimoes.newsheadlines.models.ResultResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 26/01/2021.
 */
public interface APIInterface {
    @GET("/v2/top-headlines/")
    Call<ResultResponse> getNews(@Query("sources") String sources);
}
