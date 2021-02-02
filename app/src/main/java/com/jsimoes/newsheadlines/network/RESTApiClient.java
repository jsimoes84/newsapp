package com.jsimoes.newsheadlines.network;

import com.jsimoes.newsheadlines.BuildConfig;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 26/01/2021.
 */
public class RESTApiClient {
    private static final String BASE_URL = BuildConfig.NEWS_BASE_ENDPOINT;
    private static final String AUTH_HEADER = "Authorization";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit == null) {

            Interceptor requestInterceptor = chain -> {
                Request newRequest = chain.request().newBuilder()
                        .addHeader(AUTH_HEADER, BuildConfig.API_KEY)
                        .build();
                return chain.proceed(newRequest);
            };

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(requestInterceptor).build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        }

        return retrofit;
    }
}
