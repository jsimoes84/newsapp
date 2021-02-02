package com.jsimoes.newsheadlines.network;

import com.jsimoes.newsheadlines.models.ResultResponse;

import java.io.IOException;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by admin on 31/01/2021.
 */
public class APIInterfaceMock implements APIInterface{

    @Override
    public Call<ResultResponse> getNews(String sources) {
        return new Call<ResultResponse>() {
            @Override
            public Response<ResultResponse> execute() throws IOException {
                Timber.e("here 123");
                return null;
            }

            @Override
            public void enqueue(Callback<ResultResponse> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<ResultResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }

            @Override
            public Timeout timeout() {
                return null;
            }
        };
    }
}
