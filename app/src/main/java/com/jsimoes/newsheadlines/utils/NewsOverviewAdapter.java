package com.jsimoes.newsheadlines.utils;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jsimoes.newsheadlines.R;
import com.jsimoes.newsheadlines.models.Article;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by admin on 26/01/2021.
 */
public class NewsOverviewAdapter extends RecyclerView.Adapter<NewsOverviewAdapter.ViewHolder> {
    private List<Article> articles;
    private NewsItemListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout itemWrapper;
        private final SimpleDraweeView itemThumbnail;
        private final TextView itemHeadline;

        public ViewHolder(View view) {
            super(view);
            itemWrapper = view.findViewById(R.id.itemWrapper);
            itemThumbnail = view.findViewById(R.id.itemThumbnail);
            itemHeadline = view.findViewById(R.id.itemHeadline);
        }
    }

    public NewsOverviewAdapter(List<Article> articles, NewsItemListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    /**
     * Update adapter with new articles list
     *
     * @param articles
     */
    public void updateDataSort(List<Article> articles) {
        if (articles != null) {
            this.articles = articles;
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Article article = getArticles().get(position);
        viewHolder.itemHeadline.setText(article.getTitle());

        if (article.getUrlToImage() != null && !article.getUrlToImage().isEmpty()) {
            viewHolder.itemThumbnail.setImageURI(Uri.parse(article.getUrlToImage()));
        }

        viewHolder.itemWrapper.setOnClickListener(v -> listener.onNewsItemTouched(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return articles.size();
    }

    public List<Article> getArticles() {
        return articles;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
