package ajmitchell.android.thenewsroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import ajmitchell.android.thenewsroom.dataPersistence.NewsDatabase;
import ajmitchell.android.thenewsroom.dataPersistence.NewsRepository;
import ajmitchell.android.thenewsroom.models.NewsModel;
import ajmitchell.android.thenewsroom.utils.AppExecutors;
import ajmitchell.android.thenewsroom.viewModels.ArticleViewModel;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";
    private NewsModel.Article article;
    private int articleId;
    private String articleTitle;
    private ArticleViewModel viewModel;
    public Boolean isSaved;
    public FloatingActionButton fab;
    private NewsRepository newsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.webView);
        Intent intent = getIntent();

        String url = intent.getStringExtra("articleUrl");
        webView.loadUrl(url);

        article = intent.getParcelableExtra("article");
        articleTitle = intent.getStringExtra("articleTitle");
        isFavorite(articleTitle);

        Log.d(TAG, "onCreate: " + articleTitle);

        fab = findViewById(R.id.fab);
        isSaved = false;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSaved) {
                    saveArticle();
                } else {
                    removeArticle();
                }
            }
        });
    }
    // Todo: check to see if article is saved/favorite. If not, save to database and update fab src. Toast "article saved".
    // Todo: if saved, remove from favorites, update fab src

    public void isFavorite(String title) {
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(ArticleViewModel.class);
        LiveData<NewsModel.Article> savedArticles = viewModel.getArticleByTitle(title);
        savedArticles.observe(this, new Observer<NewsModel.Article>() {
            @Override
            public void onChanged(NewsModel.Article article) {
                savedArticles.removeObserver(this);
                if (article == null) {
                    isSaved = false;
                    fab.setImageResource(R.drawable.ic_favorite_border_24);
                } else if (articleTitle == article.getTitle() && !isSaved) {
                    isSaved = true;
                    fab.setImageResource(R.drawable.ic_favorite_filled_24);
                } else {
                    isSaved = true;
                    fab.setImageResource(R.drawable.ic_favorite_filled_24);
                }
            }
        });
    }

    public void saveArticle() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!isSaved) {
                    newsRepository.insert(article);
                }
            }
        });
        Toast.makeText(WebViewActivity.this, "Article saved", Toast.LENGTH_SHORT).show();
    }

    public void removeArticle() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                newsRepository.delete(article);
            }
        });
        Toast.makeText(WebViewActivity.this, "Article removed from saved", Toast.LENGTH_SHORT).show();
    }
}