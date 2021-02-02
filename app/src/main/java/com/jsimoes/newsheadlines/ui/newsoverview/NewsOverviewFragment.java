package com.jsimoes.newsheadlines.ui.newsoverview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jsimoes.newsheadlines.R;
import com.jsimoes.newsheadlines.databinding.NewsOverviewFragmentBinding;
import com.jsimoes.newsheadlines.models.Article;
import com.jsimoes.newsheadlines.utils.NavigationFlows;
import com.jsimoes.newsheadlines.utils.NewsOverviewAdapter;
import com.jsimoes.newsheadlines.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NewsOverviewFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private NewsViewModel newsViewModel;
    private NewsOverviewAdapter newsOverviewAdapter;
    private NewsOverviewFragmentBinding binding;
    private List<Article> articleList;
    private NavigationFlows navigationListener;
    private boolean sortFlag = true;
    private boolean isRefreshing = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleList = new ArrayList<>();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = NewsOverviewFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sortNewsAction) {
            changePublishDateSorting();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.navigationListener = (NavigationFlows) getActivity();
        binding.swiperefresh.setOnRefreshListener(this);
        initAdapter();

        if (newsViewModel == null) {
            newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
            newsViewModel.getResult().observe(getViewLifecycleOwner(), resource -> {
                switch (resource.status) {
                    case LOADING:
                        toggleLoader(true);
                        break;

                    case SUCCESS:
                        if (resource.data != null && resource.data.getArticles() != null) {
                            articleList = resource.data.getArticles();
                            sortAndUpdate();
                            toggleLoader(false);
                        }

                        if (isRefreshing()) {
                            binding.swiperefresh.setRefreshing(false);
                            setRefreshing(false);
                        }

                        break;

                    case ERROR:
                        Utils.showToast(getContext(), getString(R.string.error_fetching_articles));
                        toggleLoader(false);

                        if (isRefreshing()) {
                            binding.swiperefresh.setRefreshing(false);
                            setRefreshing(false);
                        }

                        break;

                    default:
                        break;
                }
            });
        }

        newsViewModel.getNews();
    }

    @Override
    public void onRefresh() {
        setRefreshing(true);
        newsViewModel.getNews();
    }

    /**
     * Sort and update article list according to sortFlag value and update news overview adapter
     */
    private void sortAndUpdate() {
        if (sortFlag) {
            Collections.sort(articleList, (o1, o2) -> o1.getPublishedAt().compareTo(o2.getPublishedAt()));
        } else {
            Collections.sort(articleList, (o1, o2) -> o2.getPublishedAt().compareTo(o1.getPublishedAt()));
        }
        newsOverviewAdapter.updateDataSort(articleList);
    }

    /**
     * Change articles publish date sorting
     */
    private void changePublishDateSorting() {
        sortFlag = !sortFlag;
        sortAndUpdate();
    }

    /**
     * Initialize articles adapter
     */
    private void initAdapter() {
        try {
            newsOverviewAdapter = new NewsOverviewAdapter(articleList, position -> navigationListener.onNewsDetailRequested(articleList.get(position)));
            newsOverviewAdapter.setHasStableIds(true);
            binding.newsList.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.newsList.setAdapter(newsOverviewAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Toggle loader visibility
     *
     * @param show
     */
    private void toggleLoader(boolean show) {
        binding.loaderWrapper.newsLoader.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }
}