package ajmitchell.android.thenewsroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import ajmitchell.android.thenewsroom.Adapters.NewsAdapter;
import ajmitchell.android.thenewsroom.models.NewsModel;

public class SavedArticleActivity extends AppCompatActivity implements NewsAdapter.OnArticleClickListener{

    private RecyclerView recyclerView;
    private NewsAdapter savedAdapter;
    private NewsModel article;
    private List<NewsModel.Article> savedArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_article);


        recyclerView = findViewById(R.id.savedNews_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        savedAdapter = new NewsAdapter(SavedArticleActivity.this, savedArticles, this);
        recyclerView.setAdapter(savedAdapter);


        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            closeOnError();
        }

        //article = bundle.getParcelable("savedArticles");
        savedArticles = bundle.getParcelable("savedArticles");
//        savedArticles = article.getArticles();

        if (savedArticles != null) {
            savedAdapter = new NewsAdapter(SavedArticleActivity.this, savedArticles, this);
            recyclerView.setAdapter(savedAdapter);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.dataNotAvail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onArticleClick(int position) {
        // Todo: send intent to open webview
    }
}