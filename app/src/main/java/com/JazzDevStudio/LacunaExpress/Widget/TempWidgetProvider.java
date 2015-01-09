package com.JazzDevStudio.LacunaExpress.Widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.JazzDevStudio.LacunaExpress.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by PatrickSSD2 on 1/8/2015.
 */
public class TempWidgetProvider extends AppWidgetProvider {

	private static final String LOG = "com.JazzDevStudio.LacunaExpress.Widget.TempWidgetProvider";

	public void onReceive(){

	}


	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	                     int[] appWidgetIds) {

		Log.w(LOG, "onUpdate method called");
		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				TempWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		int counter = 1;
		for (int widgetId : allWidgetIds) {
			Log.d("Widget ID in Provider: ", Integer.toString(widgetId));
			Log.d("Counter is at: ", Integer.toString(counter));
			counter++;
		}

		// Build the intent to call the service
		Intent intent = new Intent(context.getApplicationContext(),
				TempService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

		// Update the widgets via the service
		context.startService(intent);
	}

	private static HashMap<Integer, Uri> uris = new HashMap<Integer, Uri>();

	@Override
	public void onReceive(Context context,
	                      Intent intent)
	{
		String action = intent.getAction();
		Log.d("onReceive", "action: " + action);
		if(action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE) ||
				action.equals("com.JazzDevStudio.LacunaExpress.Widget.TempWidgetConfig"))
		{
			//Check if there is a single widget ID.
			int widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			//If there is no single ID, call the super implementation.
			if(widgetID == AppWidgetManager.INVALID_APPWIDGET_ID)
				super.onReceive(context, intent);
				//Otherwise call our onUpdate() passing a one element array, with the retrieved ID.
			else
				this.onUpdate(context, AppWidgetManager.getInstance(context), new int[]{widgetID});
		}
		else
			super.onReceive(context, intent);
	}

	/*
	@Override
	public void onUpdate(Context context,
	                     AppWidgetManager appWidgetManager,
	                     int[] appWidgetIds)
	{
		Log.d("onUpdate", "called, number of instances " + appWidgetIds.length);
		for (int widgetId : appWidgetIds)
		{
			updateAppWidget(context,
					appWidgetManager,
					widgetId);
		}
	}
	*/

	/**
	 * Each time an instance is removed, we cancel the associated AlarmManager.
	 */
	@Override
	public void onDeleted(Context context, int[] appWidgetIds)
	{
		super.onDeleted(context, appWidgetIds);
		for (int appWidgetId : appWidgetIds)
		{
			cancelAlarmManager(context, appWidgetId);
		}
	}

	protected void cancelAlarmManager(Context context, int widgetID)
	{
		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intentUpdate = new Intent(context, TempWidgetProvider.class);
		//AlarmManager are identified with Intent's Action and Uri.
		intentUpdate.setAction("com.JazzDevStudio.LacunaExpress.Widget.TempWidgetConfig");
		//Don't put the uri to cancel all the AlarmManager with action UPDATE_ONE.
		intentUpdate.setData(uris.get(widgetID));
		intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
		PendingIntent pendingIntentAlarm = PendingIntent.getBroadcast(context,
				0,
				intentUpdate,
				PendingIntent.FLAG_UPDATE_CURRENT);
		alarm.cancel(pendingIntentAlarm);
		Log.d("cancelAlarmManager", "Cancelled Alarm. Action = " +
				"com.JazzDevStudio.LacunaExpress.Widget.TempWidgetConfig" +
				" URI = " + uris.get(widgetID));
		uris.remove(widgetID);
	}

	public static void addUri(int id, Uri uri)
	{
		uris.put(new Integer(id), uri);
	}

	private void updateAppWidget(Context context,
	                             AppWidgetManager appWidgetManager,
	                             int appWidgetId)
	{
		//Inflate layout.
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_mail_layout);
		//Update UI.
		remoteViews.setTextViewText(R.id.widget_mail_message_count, "This is working:"+getTimeStamp());
		//Retrieve color.
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

		//Apply color.
		String color_background_choice = "white";
		remoteViews.setInt(R.id.widget_mail_layout, "setBackgroundColor", android.graphics.Color.parseColor(color_background_choice));

		//Create the intent.
		Intent labelIntent = new Intent(context, TempWidgetProvider.class);
		labelIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
		//Put the ID of our widget to identify it later.
		labelIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		PendingIntent labelPendingIntent = PendingIntent.getBroadcast(context,
				appWidgetId,
				labelIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.widget_mail_message_count, labelPendingIntent);
		Log.d("updateAppWidget", "Updated ID: " + appWidgetId);
		//Call the Manager to ensure the changes take effect.
		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
	}

	private String getTimeStamp()
	{
		String res="";
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		Date now = calendar.getTime();
		res += now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
		return res;
	}
}