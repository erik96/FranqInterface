package com.phenom.erik.franqinterface;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import com.phenom.erik.franqinterface.Util.Constants;

import java.util.Random;

/**
 * Created by Erik on 2/19/2015.
 */
public class WidgetProvider extends AppWidgetProvider implements Constants {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        ComponentName thisWidget = new ComponentName(context,
                WidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        // Build the intent to call the service
        Intent intent = new Intent(context.getApplicationContext(),
                UpdateWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

        // Update the widgets via the service
        context.startService(intent);
    }

    /*private void onUpdateWidget(Context context,AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        String[] res = context.getResources().getStringArray(R.array.quotes);

        final int index = new Random().nextInt(res.length);
        views.setTextViewText(R.id.textViewName,res[index]);
        views.setTextColor(R.id.textViewName, Color.parseColor(COLOR_GREEN));

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_bg, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    } */
}
