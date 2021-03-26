package ajmitchell.android.thenewsroom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import ajmitchell.android.thenewsroom.Adapters.NewsAdapter;
import ajmitchell.android.thenewsroom.models.NewsModel;

public class TopNewsActivity extends AppCompatActivity {

    private static final String TAG = "TopNewsActivity";
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private NewsModel topNews;
    private List<NewsModel.Article> topNewsList;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_news);


        actionBar = getSupportActionBar();

        recyclerView = findViewById(R.id.topNews_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(TopNewsActivity.this, topNewsList);
        recyclerView.setAdapter(newsAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            closeOnError();
        }

        topNews = bundle.getParcelable("allTopStories");
        topNewsList = topNews.getArticles();
        if (topNewsList != null) {
            newsAdapter = new NewsAdapter(TopNewsActivity.this, topNewsList);
            recyclerView.setAdapter(newsAdapter);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.dataNotAvail, Toast.LENGTH_SHORT).show();
    }
}
