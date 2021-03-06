package ajmitchell.android.thenewsroom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ajmitchell.android.thenewsroom.Adapters.NewsAdapter;
import ajmitchell.android.thenewsroom.models.NewsModel;
import ajmitchell.android.thenewsroom.widget.NewsroomWidget;

import static ajmitchell.android.thenewsroom.MainActivity.PACKAGE_NAME;

public class ArticleDetailActivity extends AppCompatActivity implements NewsAdapter.OnArticleClickListener {

    private static final String TAG = "ArticleDetailActivity";
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private NewsModel mArticle;
    private List<NewsModel.Article> articleList;
//    ActionBar actionBar;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.news_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(ArticleDetailActivity.this, articleList, this);
        recyclerView.setAdapter(newsAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            closeOnError();
        }
        articleList = new ArrayList<>(); // test

        mArticle = bundle.getParcelable("categoryStories");
        if (mArticle != null) {
            articleList = mArticle.getArticles();
        }
        List<NewsModel.Article> temp = new ArrayList<>();
        temp = articleList;

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("articleTitles", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonArticles = gson.toJson(temp);
        editor.putString("articleTitles", jsonArticles);

        editor.apply();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(getApplicationContext(), NewsroomWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_gridview_item);

        if (articleList != null) {
            newsAdapter = new NewsAdapter(ArticleDetailActivity.this, articleList, this);
            recyclerView.setAdapter(newsAdapter);
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.dataNotAvail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onArticleClick(int position) {
        NewsModel.Article clickedArticle = articleList.get(position);
        String articleUrl = clickedArticle.getUrl();
        Intent intent = new Intent(ArticleDetailActivity.this, WebViewActivity.class);
        intent.putExtra("articleUrl", articleUrl);
        intent.putExtra("article", clickedArticle);
        startActivity(intent);
    }
}

