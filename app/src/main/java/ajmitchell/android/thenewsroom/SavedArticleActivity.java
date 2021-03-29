package ajmitchell.android.thenewsroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ajmitchell.android.thenewsroom.Adapters.NewsAdapter;
import ajmitchell.android.thenewsroom.dataPersistence.NewsDatabase;
import ajmitchell.android.thenewsroom.models.NewsModel;
import ajmitchell.android.thenewsroom.viewModels.ArticleViewModel;

public class SavedArticleActivity extends AppCompatActivity implements NewsAdapter.OnArticleClickListener{

    private RecyclerView recyclerView;
    private NewsAdapter savedAdapter;
    private NewsModel article;
    private List<NewsModel.Article> savedArticles = new ArrayList<>();
    private NewsDatabase database;
    private ArticleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_article);

        database = NewsDatabase.getInstance(SavedArticleActivity.this);

        recyclerView = findViewById(R.id.savedNews_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        savedAdapter = new NewsAdapter(SavedArticleActivity.this, savedArticles, this);
        recyclerView.setAdapter(savedAdapter);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(ArticleViewModel.class);
        viewModel.getAllArticles().observe(this, new Observer<List<NewsModel.Article>>() {
            @Override
            public void onChanged(List<NewsModel.Article> articles) {
                savedArticles.addAll(articles);
                if (savedArticles.size() <= 0) {
                    Toast.makeText(SavedArticleActivity.this, "No Saved Articles", Toast.LENGTH_SHORT).show();
                }
                savedAdapter.notifyDataSetChanged();

            }
        });

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.dataNotAvail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onArticleClick(int position) {
        // Todo: send intent to open webview
        NewsModel.Article clickedArticle = savedArticles.get(position);
        String articleUrl = clickedArticle.getUrl();
        Intent intent = new Intent(SavedArticleActivity.this, WebViewActivity.class);
        intent.putExtra("articleUrl", articleUrl);
        intent.putExtra("article", clickedArticle);
        startActivity(intent);
    }
}