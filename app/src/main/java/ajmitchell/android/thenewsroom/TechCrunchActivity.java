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

public class TechCrunchActivity extends AppCompatActivity {
    private static final String TAG = "TechCrunchActivity";
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private NewsModel techCrunch;
    private List<NewsModel.Article> techCrunchList;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_crunch2);

        actionBar = getSupportActionBar();

        recyclerView = findViewById(R.id.techCrunch_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(TechCrunchActivity.this, techCrunchList);
        recyclerView.setAdapter(newsAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            closeOnError();
        }

        techCrunch = bundle.getParcelable("techCrunchStories");
        techCrunchList = techCrunch.getArticles();
        if (techCrunchList != null) {
            newsAdapter = new NewsAdapter(TechCrunchActivity.this, techCrunchList);
            recyclerView.setAdapter(newsAdapter);
        }
//
//        Bundle bundle2 = getIntent().getExtras();
//        if (bundle2 == null) {
//            closeOnError();
//        }
//        allNews = bundle2.getParcelable("allTopStories");
//        allNewsList = allNews.getArticles();
//        if (allNewsList != null) {
//            newsAdapter = new NewsAdapter(ArticleDetailActivity.this, allNewsList);
//            recyclerView.setAdapter(newsAdapter);
//        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.dataNotAvail, Toast.LENGTH_SHORT).show();
    }

}