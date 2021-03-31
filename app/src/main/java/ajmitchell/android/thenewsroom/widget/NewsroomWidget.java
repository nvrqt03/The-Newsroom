package ajmitchell.android.thenewsroom.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import ajmitchell.android.thenewsroom.ArticleDetailActivity;
import ajmitchell.android.thenewsroom.MainActivity;
import ajmitchell.android.thenewsroom.R;

import ajmitchell.android.thenewsroom.WebViewActivity;
import ajmitchell.android.thenewsroom.viewModels.ArticleViewModel;


/**
 * Implementation of App Widget functionality.
 */
public class NewsroomWidget extends AppWidgetProvider {

    public static ArrayList<String> articleList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent appIntent = new Intent(context, WebViewActivity.class);
//        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.newsroom_widget);
        views.setPendingIntentTemplate(R.id.widget_grid_view, pendingIntent);

        //connect our widget code to the remote views service - setRemoteAdapter
        setRemoteAdapter(context, views);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_grid_view,
                new Intent(context, WidgetService.class));
    }
}