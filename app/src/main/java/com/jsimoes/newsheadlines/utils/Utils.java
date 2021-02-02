package com.jsimoes.newsheadlines.utils;

import android.content.Context;
import android.widget.Toast;

import com.jsimoes.newsheadlines.BuildConfig;
import com.jsimoes.newsheadlines.R;

/**
 * Created by admin on 28/01/2021.
 */
public class Utils {

    public static int getNewsSourceTitle() {
        int titleResourceId = R.string.news_source_default_title;
        switch (BuildConfig.NEWS_SOURCE) {
            case Configuration.SOURCE_AP:
                titleResourceId = R.string.news_source_ap_title;
                break;
            case Configuration.SOURCE_BBC:
                titleResourceId = R.string.news_source_bbc_title;
                break;

            case Configuration.SOURCE_CNN:
                titleResourceId = R.string.news_source_cnn_title;
                break;

            default:
                break;
        }

        return titleResourceId;
    }
    
    public static void showToast(Context context, String message) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
