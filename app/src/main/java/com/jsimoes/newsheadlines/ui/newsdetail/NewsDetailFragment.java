package com.jsimoes.newsheadlines.ui.newsdetail;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsimoes.newsheadlines.databinding.NewsDetailFragmentBinding;
import com.jsimoes.newsheadlines.models.Article;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

public class NewsDetailFragment extends Fragment {
    private NewsDetailFragmentBinding binding;
    private Article article;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.article = NewsDetailFragmentArgs.fromBundle(getArguments()).getNewsData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = NewsDetailFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateUI();
    }

    /**
     * Update ui with article data
     */
    private void updateUI() {
        if (article.getUrlToImage() != null) {
            binding.itemImage.setImageURI(Uri.parse(article.getUrlToImage()));
        }

        if (article.getTitle() != null) {

            binding.itemTitle.setText(HtmlCompat.fromHtml(article.getTitle(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        }

        if (article.getDescription() != null) {
            binding.itemDescription.setText(HtmlCompat.fromHtml(article.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        }

        if (article.getContent() != null) {
            binding.itemContent.setText(HtmlCompat.fromHtml(article.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
    }
}