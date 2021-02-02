package com.jsimoes.newsheadlines.ui.newsoverview;

import com.jsimoes.newsheadlines.BuildConfig;
import com.jsimoes.newsheadlines.models.ResultResponse;
import com.jsimoes.newsheadlines.network.APIInterface;
import com.jsimoes.newsheadlines.network.RESTApiClient;
import com.jsimoes.newsheadlines.network.Resource;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {
    private MutableLiveData<Resource<ResultResponse>> result;
    private APIInterface apiInterface;

    public NewsViewModel() {
        apiInterface = RESTApiClient.getClient().create(APIInterface.class);
        result = new MutableLiveData<>();
    }

    public NewsViewModel(APIInterface apiInterface) {
        this.apiInterface = apiInterface;
        this.result = new MutableLiveData<>();
    }

    /**
     * Make call to fetch server news for a specific news source
     */
    public void getNews() {
        result.setValue(Resource.loading(null));
        apiInterface.getNews(BuildConfig.NEWS_SOURCE).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {

                result.setValue(Resource.success(response.body() != null ? response.body() : new ResultResponse()));
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                result.setValue(Resource.error(new Throwable(), null));
            }
        });
    }

    public MutableLiveData<Resource<ResultResponse>> getResult() {
        return result;
    }
}