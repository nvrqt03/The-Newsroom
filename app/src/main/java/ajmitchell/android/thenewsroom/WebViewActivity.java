package ajmitchell.android.thenewsroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ajmitchell.android.thenewsroom.dataPersistence.NewsDatabase;
import ajmitchell.android.thenewsroom.dataPersistence.NewsRepository;
import ajmitchell.android.thenewsroom.models.NewsModel;
import ajmitchell.android.thenewsroom.utils.AppExecutors;
import ajmitchell.android.thenewsroom.viewModels.ArticleViewModel;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";
    private NewsModel.Article mArticle;
    private int articleId;
    private String articleTitle;
    private ArticleViewModel viewModel;
    public Boolean isSaved;
    public FloatingActionButton fab;
    public ToggleButton button;
    private NewsRepository newsRepository;
    public NewsDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.webView);
        Intent intent = getIntent();

        String url = intent.getStringExtra("articleUrl");
        webView.loadUrl(url);

        mArticle = intent.getParcelableExtra("article");
        if (mArticle != null) {
            articleTitle = mArticle.getTitle();
            Log.d(TAG, "onCreate: " + articleTitle);
        }
        isFavorite(articleTitle);

        button = findViewById(R.id.fab);
        button.setChecked(false);
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isPressed())
                    if (isChecked) {
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
                    button.setChecked(false);
                } else if (title == article.getTitle() && !button.isChecked()) {
                    isSaved = true;
                    button.setChecked(true);
                } else {
                    isSaved = true;
                    button.setChecked(true);
                }
            }
        });
    }

    public void saveArticle() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!isSaved) {
                    if (mArticle != null) {
                        database.newsDao().insertArticle(mArticle);
                    }
                }
            }
        });
        Toast.makeText(WebViewActivity.this, "Article saved", Toast.LENGTH_SHORT).show();
    }

    public void removeArticle() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mArticle != null) {
                    database.newsDao().delete(mArticle.getTitle());
                }
            }
        });
        Toast.makeText(WebViewActivity.this, "Article removed from saved", Toast.LENGTH_SHORT).show();
    }
}