package ajmitchell.android.thenewsroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

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
    private Button topStories;
    private Button techCrunch;
    private NewsRepository newsRepository;
    private ArticleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        topStories = findViewById(R.id.us_news_button);
        techCrunch = findViewById(R.id.techcrunch_news_button);

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
                actionBar.setTitle(Constants.BUSINESS_TITLE);
                return true;
            case R.id.entertainment:
                getCategory(Constants.ENTERTAINMENT_CAT);
                actionBar.setTitle(Constants.ENTERTAINMENT_TITLE);
                return true;
            case R.id.health:
                getCategory(Constants.HEALTH_CAT);
                actionBar.setTitle(Constants.HEALTH_TITLE);
                return true;
            case R.id.science:
                getCategory(Constants.SCIENCE_CATEGORY);
                actionBar.setTitle(Constants.SCIENCE_TITLE);
                return true;
            case R.id.sports:
                getCategory(Constants.SPORTS_CATEGORY);
                actionBar.setTitle(Constants.SPORTS_TITLE);
                return true;
            case R.id.tech:
                getCategory(Constants.TECH_CATEGORY);
                actionBar.setTitle(Constants.TECH_TITLE);
                return true;
            case R.id.saved:
                getSavedArticles();
                actionBar.setTitle(Constants.SAVED_ARTICLES);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getSavedArticles() {
        final LiveData<List<NewsModel.Article>> savedArticles = newsRepository.getAllArticles();
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(ArticleViewModel.class);
        savedArticles.observe(this, new Observer<List<NewsModel.Article>>() {
            @Override
            public void onChanged(List<NewsModel.Article> articles) {
                viewModel.getAllArticles().removeObserver(this);
                // Todo: where are we going to send the list of articles? Article detail activity or create a new activity?
            }
        });

    }
    public void getTopStories() {
        NewsApi newsApi = NewsRetrofit.getNewsApi();
        Call<NewsModel> call = newsApi.getTopHeadlines(Constants.API_KEY);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsDetails = response.body();
                Bundle bundle = new Bundle();
                bundle.putParcelable("allTopStories", newsDetails);
                Intent intent = new Intent(MainActivity.this, TopNewsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

                //Log.d(TAG, "onResponse: " + articles.toString());
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
                Intent intent = new Intent(MainActivity.this, TechCrunchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("techCrunchStories", newsDetails);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }
}