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

public class TopNewsActivity extends AppCompatActivity implements NewsAdapter.OnArticleClickListener {

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
        newsAdapter = new NewsAdapter(TopNewsActivity.this, topNewsList, this);
        recyclerView.setAdapter(newsAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            closeOnError();
        }

        topNews = bundle.getParcelable("allTopStories");
        topNewsList = topNews.getArticles();
        if (topNewsList != null) {
            newsAdapter = new NewsAdapter(TopNewsActivity.this, topNewsList, this);
            recyclerView.setAdapter(newsAdapter);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.dataNotAvail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onArticleClick(int position) {
        NewsModel.Article clickedArticle = topNewsList.get(position);
        String articleUrl = clickedArticle.getUrl();
        Intent intent = new Intent(TopNewsActivity.this, WebViewActivity.class);
        intent.putExtra("articleUrl", articleUrl);
        intent.putExtra("article", clickedArticle);
        startActivity(intent);
    }
}
//    NewsModel.Article clickedArticle = articleList.get(position);
//    int articleId = articleList.get(position).getArticleId();
//    String articleUrl = clickedArticle.getUrl();
//    //        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
//    Intent intent = new Intent(ArticleDetailActivity.this, WebViewActivity.class);
//        intent.putExtra("articleUrl", articleUrl);
//                intent.putExtra("article", clickedArticle);
//                startActivity(intent);