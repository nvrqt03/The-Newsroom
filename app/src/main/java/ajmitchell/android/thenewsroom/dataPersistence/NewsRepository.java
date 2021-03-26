package ajmitchell.android.thenewsroom.dataPersistence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import ajmitchell.android.thenewsroom.models.NewsModel;

public class NewsRepository {
    private NewsDao newsDao;
    private LiveData<List<NewsModel.Article>> allArticles;

    public NewsRepository(Application application) {
        NewsDatabase database = NewsDatabase.getInstance(application);
        newsDao = database.newsDao();
        allArticles = newsDao.getAllArticles();
    }

    public void insert(NewsModel.Article article) {
        new InsertArticleAsyncTask(newsDao).execute(article);
    }

    public void delete(NewsModel.Article article) {
        new DeleteArticleAsyncTask(newsDao).execute(article);
    }

    public LiveData<List<NewsModel.Article>> getAllArticles() {
        return allArticles;
    }

    public LiveData<NewsModel.Article> getArticleById(int articleId) {
        return newsDao.getArticleById(articleId);
    }

    private static class InsertArticleAsyncTask extends AsyncTask<NewsModel.Article, Void, Void> {
        private NewsDao newsDao;
        private InsertArticleAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(NewsModel.Article... articles) {
            newsDao.insertArticle(articles[0]);
            return null;
        }
    }

    private static class DeleteArticleAsyncTask extends AsyncTask<NewsModel.Article, Void, Void> {
        private NewsDao newsDao;
        private DeleteArticleAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(NewsModel.Article... articles) {
            newsDao.delete(articles[0].getArticleId());
            return null;
        }
    }
}
