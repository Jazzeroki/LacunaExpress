package com.JazzDevStudio.LacunaExpress.Widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.JazzDevStudio.LacunaExpress.Database.TEMPDatabaseAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * This will configure the widget that is placed on the user's homescreen.
 */
public class MailWidgetProvider extends AppWidgetProvider {

	private PendingIntent service = null;

	private boolean repeating = true;

	private static final String LOG = "com.JazzDevStudio.LacunaExpress.Widget.TempWidgetProvider";

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	                     int[] appWidgetIds) {

		//The below code is for timing the updates. Will work with it later
		/*
		//Alarm Manager to manage the frequency of updates
		final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//Calendar object to get current time
		final Calendar TIME = Calendar.getInstance();
		TIME.set(Calendar.MINUTE, 0);
		TIME.set(Calendar.SECOND, 0);
		TIME.set(Calendar.MILLISECOND, 0);
		*/

		Log.w(LOG, "onUpdate method called");
		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				MailWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		int counter = 1;
		for (int widgetId : allWidgetIds) {
			Log.d("Widget ID in Provider: ", Integer.toString(widgetId));
			Log.d("Counter is at: ", Integer.toString(counter));
			counter++;
		}

		// Build the intent to call the service
		Intent intent = new Intent(context.getApplicationContext(),
				MailWidgetUpdateService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

		//The below code is for timing the updates. Will work with it later
		//if (service == null)
		//{
			//service = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		//}

		//if (repeating){
			//m.setRepeating(AlarmManager.RTC, TIME.getTime().getTime(), 1000 * 60, service);
		//}



		// Update the widgets via the service
		context.startService(intent);
	}

	//Whenever the widget is disabled, this will be called
	public void onDisabled(Context context)
	{
		//final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		//m.cancel(service);
	}

	private static HashMap<Integer, Uri> uris = new HashMap<Integer, Uri>();

	@Override
	public void onReceive(Context context, Intent intent) {



		String action = intent.getAction();
		Log.d("onReceive", "action: " + action);
		if(action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE) ||
				action.equals(AppWidgetManager.ACTION_APPWIDGET_OPTIONS_CHANGED)) {
				//action.equals("com.JazzDevStudio.LacunaExpress.Widget.MailWidgetConfig"))


			//Check if there is a single widget ID.
			int widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			//If there is no single ID, call the super implementation.
			if(widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
				super.onReceive(context, intent);
				Log.d("Provider: ", "Line 69");
				//updateRemoteViews(context, widgetID);//////////////////
				//Otherwise call our onUpdate() passing a one element array, with the retrieved ID.
			} else {
				this.onUpdate(context, AppWidgetManager.getInstance(context), new int[]{widgetID});
				Log.d("Provider: ", "Line 73");
			}
		} else {
			super.onReceive(context, intent);
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		for (int appWidgetId : appWidgetIds)
		{
			cancelAlarmManager(context, appWidgetId);
			Log.d("App Widget ID Being Deleted: ", Integer.toString(appWidgetId));

			//Delete the row in the database that held the data
			TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(context);

			try {
				db.deleteRow(Integer.toString(appWidgetId));
			} catch (Exception e){
				Log.d("Database", "Could not delete row = " + Integer.toString(appWidgetId));
			}

			try {
				db.close();
			} catch (Exception e){
				Log.d("Database", "Could not close database");
			}
			//REMOVE THE NOTIFICATIONS FROM INBOUND MAILS HERE
		}


	}

	protected void cancelAlarmManager(Context context, int widgetID) {
		AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intentUpdate = new Intent(context, MailWidgetProvider.class);
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
		uris.put(id, uri); //uris.put(new Integer(id), uri);
		Log.d("addURI added id: ", Integer.toString(id));
	}

	/*
	private void updateAppWidget(Context context,
	                             AppWidgetManager appWidgetManager,
	                             int appWidgetId) {
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
		Intent labelIntent = new Intent(context, MailWidgetProvider.class);
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
	*/

	private String getTimeStamp() {
		String res="";
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		Date now = calendar.getTime();
		res += now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
		return res;
	}
}