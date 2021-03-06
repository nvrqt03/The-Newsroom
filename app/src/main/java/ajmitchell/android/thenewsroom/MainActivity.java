package ajmitchell.android.thenewsroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ajmitchell.android.thenewsroom.dataPersistence.NewsDatabase;
import ajmitchell.android.thenewsroom.dataPersistence.NewsRepository;
import ajmitchell.android.thenewsroom.models.NewsModel;
import ajmitchell.android.thenewsroom.utils.Constants;
import ajmitchell.android.thenewsroom.viewModels.ArticleViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private List<NewsModel.Article> articles;
    private List<NewsModel.Article> techcrunch;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private Button topStories;
    private Button techCrunch;
    private NewsRepository newsRepository;
    private ArticleViewModel viewModel;
    private NewsDatabase db;
    public static String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(request);
        MobileAds.initialize(this);
        FirebaseAnalytics.getInstance(getApplicationContext());

        PACKAGE_NAME = getApplicationContext().getPackageName();
        Log.d(TAG, "onCreate: " + PACKAGE_NAME);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


//        actionBar = getSupportActionBar();
        topStories = findViewById(R.id.us_news_button);
        techCrunch = findViewById(R.id.techcrunch_news_button);
        db = NewsDatabase.getInstance(getApplicationContext());
        topStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTopStories();
            }
        });

        techCrunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTechCrunchHeadlines();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.business:
                getCategory(Constants.BUSINESS_CAT);
                toolbar.setTitle(Constants.BUSINESS_TITLE);
                return true;
            case R.id.entertainment:
                getCategory(Constants.ENTERTAINMENT_CAT);
                toolbar.setTitle(Constants.ENTERTAINMENT_TITLE);
                return true;
            case R.id.health:
                getCategory(Constants.HEALTH_CAT);
                toolbar.setTitle(Constants.HEALTH_TITLE);
                return true;
            case R.id.science:
                getCategory(Constants.SCIENCE_CATEGORY);
                toolbar.setTitle(Constants.SCIENCE_TITLE);
                return true;
            case R.id.sports:
                getCategory(Constants.SPORTS_CATEGORY);
                toolbar.setTitle(Constants.SPORTS_TITLE);
                return true;
            case R.id.tech:
                getCategory(Constants.TECH_CATEGORY);
                toolbar.setTitle(Constants.TECH_TITLE);
                return true;
            case R.id.saved:
                getSavedArticles();
                toolbar.setTitle(Constants.SAVED_ARTICLES);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getSavedArticles() {
        Intent intent = new Intent(MainActivity.this, SavedArticleActivity.class);
        startActivity(intent);

    }
    public void getTopStories() {
        NewsApi newsApi = NewsRetrofit.getNewsApi();
        Call<NewsModel> call = newsApi.getTopHeadlines(Constants.API_KEY);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsDetails = response.body();
                Bundle bundle = new Bundle();
                bundle.putParcelable("categoryStories", newsDetails);
                Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }

    public void getCategory(String category) {
        NewsApi newsApi = NewsRetrofit.getNewsApi();
        Call<NewsModel> call = newsApi.getTopCategory(category, Constants.API_KEY);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel categoryDetails = response.body();
                articles = categoryDetails.getArticles();
                Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("categoryStories", categoryDetails);
                intent.putExtras(bundle);
                startActivity(intent);

                Log.d(TAG, "onResponse: " + articles.toString());
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }

    public void getTechCrunchHeadlines() {
        NewsApi newsApi = NewsRetrofit.getNewsApi();
        Call<NewsModel> call = newsApi.getTechcrunchHeadlines(Constants.SOURCE_TECHCRUNCH, Constants.API_KEY);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsDetails = response.body();
                techcrunch = newsDetails.getArticles();
                Log.d(TAG, "onResponse: " + techcrunch.toString());
                Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("categoryStories", newsDetails);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }
}