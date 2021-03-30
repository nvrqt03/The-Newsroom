package ajmitchell.android.thenewsroom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        actionBar = getSupportActionBar();

        recyclerView = findViewById(R.id.news_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(ArticleDetailActivity.this, articleList, this);
        recyclerView.setAdapter(newsAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            closeOnError();
        }

        mArticle = bundle.getParcelable("categoryStories");
        articleList = mArticle.getArticles();
        List<NewsModel.Article> temp = articleList;

        ArrayList<String> newsTitlesForWidget = new ArrayList<>();

        for (int i = 0; i < temp.size(); i++) {
            newsTitlesForWidget.add(temp.get(i).getTitle());

        }

//        int i = temp.size();
//        newsTitlesForWidget.add(temp.get(i).getTitle());

        Set<String> set = new HashSet<>();
        set.addAll(newsTitlesForWidget);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("articleTitles", set);
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

