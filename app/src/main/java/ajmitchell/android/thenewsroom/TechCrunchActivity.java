package ajmitchell.android.thenewsroom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import ajmitchell.android.thenewsroom.Adapters.NewsAdapter;
import ajmitchell.android.thenewsroom.models.NewsModel;

public class TechCrunchActivity extends AppCompatActivity implements NewsAdapter.OnArticleClickListener {
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
        newsAdapter = new NewsAdapter(TechCrunchActivity.this, techCrunchList, this);
        recyclerView.setAdapter(newsAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            closeOnError();
        }

        techCrunch = bundle.getParcelable("techCrunchStories");
        techCrunchList = techCrunch.getArticles();
        if (techCrunchList != null) {
            newsAdapter = new NewsAdapter(TechCrunchActivity.this, techCrunchList, this);
            recyclerView.setAdapter(newsAdapter);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.dataNotAvail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onArticleClick(int position) {
        NewsModel.Article clickedArticle = techCrunchList.get(position);
        String articleUrl = clickedArticle.getUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
        startActivity(intent);
    }
}