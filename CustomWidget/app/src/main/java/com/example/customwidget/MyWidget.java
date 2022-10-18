package com.example.customwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidget extends AppWidgetProvider {

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_design);

        //get widget value
        SharedPreferences preferences = context.getSharedPreferences("PREFS", 0);
        int value = preferences.getInt("value", 1);

        //set value to the widget text
        views.setTextViewText(R.id.tv_number, String.valueOf(value));

        //update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        //reschedule new widget refresh
        AlarmHandler alarmHandler = new AlarmHandler(context);
        alarmHandler.cancelAlarmManager();
        alarmHandler.setAlarmManager();

        Log.d("WIDGET", "Widget updated!");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId: appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        //stop updating the widget
        AlarmHandler alarmHandler = new AlarmHandler(context);
        alarmHandler.cancelAlarmManager();

        Log.d("WIDGET", "Widget removed!");
    }
}
