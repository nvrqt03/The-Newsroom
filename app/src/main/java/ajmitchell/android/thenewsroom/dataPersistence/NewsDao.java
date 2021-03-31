package ajmitchell.android.thenewsroom.dataPersistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ajmitchell.android.thenewsroom.NewsApi;
import ajmitchell.android.thenewsroom.models.NewsModel;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM article_table ORDER BY publishedAt DESC")
    LiveData<List<NewsModel.Article>> getAllArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(NewsModel.Article article);

//    @Query("DELETE FROM article_table WHERE articleId = :id")
//    void delete(int id);

    @Query("DELETE FROM article_table WHERE title = :articleTitle")
    void delete(String articleTitle);

//    @Query("SELECT articleId FROM article_table WHERE articleId = :id")
//    LiveData<NewsModel.Article> getArticleById(int id);

    @Query("SELECT * FROM article_table WHERE title = :articleTitle")
    LiveData<NewsModel.Article> getArticleByTitle(String articleTitle);
}
