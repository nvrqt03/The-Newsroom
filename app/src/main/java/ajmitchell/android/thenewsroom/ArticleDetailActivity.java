package ajmitchell.android.thenewsroom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import ajmitchell.android.thenewsroom.Adapters.NewsAdapter;
import ajmitchell.android.thenewsroom.dataPersistence.NewsDatabase;
import ajmitchell.android.thenewsroom.models.NewsModel;
import ajmitchell.android.thenewsroom.utils.AppExecutors;
import ajmitchell.android.thenewsroom.viewModels.ArticleViewModel;

public class ArticleDetailActivity extends AppCompatActivity implements NewsAdapter.OnArticleClickListener {

    private static final String TAG = "ArticleDetailActivity";
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private NewsModel mArticle;
    private List<NewsModel.Article> articleList;
    ActionBar actionBar;
    public ArticleViewModel articleViewModel;
    public boolean isFavorite;
    private NewsModel.Article individualArticle;
    private int individualArticleId;
    private NewsDatabase newsDatabase;

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
        int articleId = articleList.get(position).getArticleId();
        String articleUrl = clickedArticle.getUrl();
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
        Intent intent = new Intent(ArticleDetailActivity.this, WebViewActivity.class);
        intent.putExtra("articleUrl", articleUrl);
        intent.putExtra("articleId", articleId);
//        intent.putExtra("article", clickedArticle);
        startActivity(intent);
    }
}
//    public void isFavorite(int id) {
//        for (int i = 0; i < articleList.size(); i++) {
//            if (id == articleList.get(i).getArticleId()) {
//                individualArticleId = id;
//            }
//        }
//        articleViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
//                .getInstance(this.getApplication()))
//                .get(ArticleViewModel.class);
//        LiveData<NewsModel.Article> favorites = articleViewModel.getArticleById(id);
//        favorites.observe(this, new Observer<NewsModel.Article>() {
//            @Override
//            public void onChanged(NewsModel.Article article) {
//                favorites.removeObserver(this);
//                if (article == null) {
//                    isFavorite = false;
//                    favoriteButton.setChecked(false);
//                } else if (individualArticleId == article.getArticleId() && !favoriteButton.isChecked()) {
//                    isFavorite = true;
//                    favoriteButton.setChecked(true);
//                } else {
//                    isFavorite = true;
//                    favoriteButton.setChecked(true);
//                }
//            }
//        });
//    }
