package ajmitchell.android.thenewsroom.dataPersistence;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ajmitchell.android.thenewsroom.models.NewsModel;
import ajmitchell.android.thenewsroom.utils.NewsTypeConverter;

@Database(entities = {NewsModel.Article.class}, version = 1, exportSchema = false)
//@TypeConverters(NewsTypeConverter.class)
public abstract class NewsDatabase extends RoomDatabase {
    public static final String LOG_TAG = NewsDatabase.class.getSimpleName();
    public static final Object LOCK = new Object();
    public static final String DATABASE_NAME = "newsApp";
    private static NewsDatabase sInstance;

    public static NewsDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        NewsDatabase.class, NewsDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }
    public abstract NewsDao newsDao();
}
