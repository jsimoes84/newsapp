package com.jsimoes.newsheadlines;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jsimoes.newsheadlines.models.Article;
import com.jsimoes.newsheadlines.utils.NavigationFlows;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements NavigationFlows {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigationController();
    }

    /**
     * Initialize navigation controller
     */
    private void initNavigationController() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.news_overview)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
                || super.onSupportNavigateUp();
    }

    @Override
    public void onNewsDetailRequested(Article article) {
        MobileNavigationDirections.OpenNewsDetail action = MobileNavigationDirections.openNewsDetail(article);
        navigateDestination(action);
    }

    /**
     * Navigate to destination and catch any disallowed navigation flow
     *
     * @param navDirections
     */
    private void navigateDestination(NavDirections navDirections) {
        try {
            navController.navigate(navDirections);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}