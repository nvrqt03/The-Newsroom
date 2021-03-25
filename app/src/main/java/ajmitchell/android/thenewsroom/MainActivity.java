package ajmitchell.android.thenewsroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import ajmitchell.android.thenewsroom.Adapters.NewsAdapter;
import ajmitchell.android.thenewsroom.models.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsModel.Article> articles;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // will implement this later
//        actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable((Color.parseColor("#000000"))));

        recyclerView = findViewById(R.id.news_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getTopStories();
    }

    public void getTopStories() {
        NewsApi newsApi = NewsRetrofit.getNewsApi();
        Call<NewsModel> call = newsApi.getTopHeadlines(Constants.API_KEY);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsDetails = response.body();
                articles = newsDetails.getArticles();
                newsAdapter = new NewsAdapter(MainActivity.this, articles);
                recyclerView.setAdapter(newsAdapter);
                Log.d(TAG, "onResponse: " + articles.toString());
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }


    // Use as template to create menu options for toolbar
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.popular_item:
//                getMovies(Constants.POPULAR);
//                actionBar.setTitle(Constants.coming_soon);
//                return true;
//            case R.id.highest_rated:
//                getMovies(Constants.HIGHEST_RATED);
//                actionBar.setTitle(Constants.highest_rated);
//                return true;
//            case R.id.coming_soon:
//                getMovies(Constants.COMING_SOON);
//                actionBar.setTitle(Constants.coming_soon);
//                return true;
//            case R.id.favorites:
//                getFavorites();
//                actionBar.setTitle(Constants.FAVORITES);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}