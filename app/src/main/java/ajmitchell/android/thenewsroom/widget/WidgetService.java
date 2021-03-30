package ajmitchell.android.thenewsroom.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import ajmitchell.android.thenewsroom.R;


public class WidgetService extends RemoteViewsService {
    List<String> remoteArticlesList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        // return remote view factory here
        return new NewsWidgdetDataFactory(this.getApplicationContext(), intent);
    }
}
