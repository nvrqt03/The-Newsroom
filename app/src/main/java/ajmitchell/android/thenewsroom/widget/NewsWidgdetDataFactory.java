package ajmitchell.android.thenewsroom.widget;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ajmitchell.android.thenewsroom.R;
import ajmitchell.android.thenewsroom.dataPersistence.NewsRepository;
import ajmitchell.android.thenewsroom.models.NewsModel;

import static ajmitchell.android.thenewsroom.MainActivity.PACKAGE_NAME;

public class NewsWidgdetDataFactory implements RemoteViewsService.RemoteViewsFactory {

    List<NewsModel.Article> articles = new ArrayList<>();
    Context context;
    Intent intent;
    private String TAG = "NewsWidgetDataFactory";

    private void initData() {
        articles.clear();

        SharedPreferences preferences = context.getSharedPreferences("articleTitles", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("articleTitles", "");
        Type type = new TypeToken<List<NewsModel.Article>>(){}.getType();
        articles = gson.fromJson(json, type);

    }

    public NewsWidgdetDataFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
//        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (articles == null) {
            return 0;
        } else {
            return articles.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.widget_gridview_item);
        remoteView.setTextViewText(R.id.widget_gridview_item, articles.get(i).getTitle());

        String url = articles.get(i).getUrl();
        Intent fillIntent = new Intent();
        fillIntent.putExtra(PACKAGE_NAME, i);
        fillIntent.putExtra("articleUrl", url);
        fillIntent.putExtra("article", articles.get(i));
        remoteView.setOnClickFillInIntent(R.id.widget_gridview_item, fillIntent);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}




//        Set<String> set = sharedPreferences.getStringSet("articleTitles", null);
//        ArrayList<String> titles = new ArrayList<>();
//        titles.addAll(set);
//        Log.d(TAG, "initData: " + titles.toString());

//        if (titles != null) {
//            collection = titles;
//        }