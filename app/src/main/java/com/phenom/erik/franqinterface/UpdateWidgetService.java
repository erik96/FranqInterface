package com.phenom.erik.franqinterface;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.phenom.erik.franqinterface.Util.Constants;

import java.util.Random;

/**
 * Created by Erik on 3/1/2015.
 */
public class UpdateWidgetService extends Service implements Constants {

    private Context context;

    @Override
    public void onStart(Intent intent, int startId) {

        context = this.getApplicationContext();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        int[] allWidgetIds = intent
                .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);


        for (int widgetId : allWidgetIds) {
            String[] r = context.getResources().getStringArray(R.array.quotes);

            RemoteViews views = new RemoteViews(this
                    .getApplicationContext().getPackageName(),
                    R.layout.widget_layout);

            final int index = new Random().nextInt(r.length);

            views.setTextViewText(R.id.textViewName,r[index]);

            views.setTextColor(R.id.textViewName, Color.parseColor(COLOR_WHITE));

            Intent clickIntent = new Intent(this.getApplicationContext(),
                    WidgetProvider.class);

            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                    allWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.textViewName, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, views);
        }
        stopSelf();

        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
