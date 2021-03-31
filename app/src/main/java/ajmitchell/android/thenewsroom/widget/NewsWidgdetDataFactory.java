package ajmitchell.android.thenewsroom.widget;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ajmitchell.android.thenewsroom.R;
import ajmitchell.android.thenewsroom.dataPersistence.NewsRepository;
import ajmitchell.android.thenewsroom.models.NewsModel;

import static ajmitchell.android.thenewsroom.MainActivity.PACKAGE_NAME;

public class NewsWidgdetDataFactory implements RemoteViewsService.RemoteViewsFactory {

    ArrayList<String> collection = new ArrayList<>();
    Context context;
    Intent intent;
    private String TAG = "NewsWidgetDataFactory";

    private void initData() {
        collection.clear();

        SharedPreferences sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("articleTitles", null);
        ArrayList<String> titles = new ArrayList<>();
        titles.addAll(set);
        Log.d(TAG, "initData: " + titles.toString());

        if (titles != null) {
            collection = titles;
        }
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
        return collection.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.widget_gridview_item);
        remoteView.setTextViewText(R.id.widget_gridview_item, collection.get(i));

        Intent fillIntent = new Intent();
        fillIntent.putExtra(PACKAGE_NAME, i);
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
