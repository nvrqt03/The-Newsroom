package ajmitchell.android.thenewsroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import ajmitchell.android.thenewsroom.models.NewsModel;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";
    private NewsModel.Article article;
    private int articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.webView);
        Intent intent = getIntent();

        String url = intent.getStringExtra("articleUrl");
        webView.loadUrl(url);

//        article = intent.getParcelableExtra("article");
        articleId = intent.getIntExtra("articleId", 0);
        Log.d(TAG, "onCreate: " + articleId);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Testing functionality", Snackbar.LENGTH_SHORT)
//                        .show();
                Toast.makeText(WebViewActivity.this, "Article saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Todo: check to see if article is saved/favorite. If not, save to database and update fab src. Toast "article saved".
    // Todo: if saved, remove from favorites, update fab src

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
}