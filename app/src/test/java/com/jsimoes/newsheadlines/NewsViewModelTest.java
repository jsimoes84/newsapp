package com.jsimoes.newsheadlines;

import com.jsimoes.newsheadlines.models.ResultResponse;
import com.jsimoes.newsheadlines.network.APIInterface;
import com.jsimoes.newsheadlines.network.Resource;
import com.jsimoes.newsheadlines.ui.newsoverview.NewsViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 31/01/2021.
 */

@RunWith(JUnit4.class)
public class NewsViewModelTest {
    private ResultResponse successResponse;
    private Resource<ResultResponse> successResource;
    private Resource<ResultResponse> errorResource = Resource.error(new Throwable(), null);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private APIInterface apiInterface;

    private NewsViewModel newsViewModel;

    @Mock
    private Observer<Resource<ResultResponse>> observer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        newsViewModel = new NewsViewModel(apiInterface);
        newsViewModel.getResult().observeForever(observer);

        successResponse = new ResultResponse();
        successResponse.setTotalResults(100);
        successResource = Resource.success(successResponse);
    }

    @Test
    public void testNull() {
        Mockito.when(apiInterface.getNews(BuildConfig.NEWS_SOURCE)).thenReturn(null);
        Assert.assertNotNull(newsViewModel.getResult());
        Assert.assertTrue(newsViewModel.getResult().hasObservers());
    }

    @Test
    public void fetchNewsWithSuccessResponse() {

        Call<ResultResponse> mockCall = Mockito.mock(Call.class);

        //When request
        Mockito.when(apiInterface.getNews(BuildConfig.NEWS_SOURCE)).thenReturn(mockCall);

        // Mock API response
        Mockito.doAnswer(invocation -> {
            Callback<ResultResponse> callback = invocation.getArgument(0);
            callback.onResponse(mockCall, Response.success(successResponse));

            newsViewModel.getResult().setValue(successResource);

            return null;
        }).when(mockCall).enqueue(ArgumentMatchers.any(Callback.class));

        newsViewModel.getNews();

        Mockito.verify(observer).onChanged(successResource);
    }

    @Test
    public void fetchNewsWithErrorResponse() {

        Call<ResultResponse> mockCall = Mockito.mock(Call.class);

        //When request
        Mockito.when(apiInterface.getNews(BuildConfig.NEWS_SOURCE)).thenReturn(mockCall);

        // Mock API response
        Mockito.doAnswer(invocation -> {
            Callback<ResultResponse> callback = invocation.getArgument(0);
            callback.onFailure(mockCall, new IOException());

            newsViewModel.getResult().setValue(errorResource);

            return null;
        }).when(mockCall).enqueue(ArgumentMatchers.any(Callback.class));

        newsViewModel.getNews();

        Mockito.verify(observer).onChanged(errorResource);
    }
}
