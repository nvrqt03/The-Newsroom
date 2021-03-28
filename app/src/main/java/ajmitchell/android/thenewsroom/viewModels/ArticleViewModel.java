package ajmitchell.android.thenewsroom.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ajmitchell.android.thenewsroom.dataPersistence.NewsRepository;
import ajmitchell.android.thenewsroom.models.NewsModel;

public class ArticleViewModel extends AndroidViewModel {

    private NewsRepository repository;
    private LiveData<NewsModel.Article> article;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        repository = new NewsRepository(getApplication());
    }

//    public LiveData<NewsModel.Article> getArticleById(int id) {
//        return repository.getArticleById(id);
//    }

    public LiveData<List<NewsModel.Article>> getAllArticles() {
        return repository.getAllArticles();
    }
    public LiveData<NewsModel.Article> getArticleByTitle(String title) {
        return repository.getArticleByTitle(title);
    }
}
