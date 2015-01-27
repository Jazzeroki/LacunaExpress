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
import android.widget.RemoteViews;

import com.JazzDevStudio.LacunaExpress.Database.TEMPDatabaseAdapter;
import com.JazzDevStudio.LacunaExpress.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * This will configure the widget that is placed on the user's homescreen.
 */
public class MailWidgetProvider extends AppWidgetProvider {

	private static final String LOG = "com.JazzDevStudio.LacunaExpress.Widget.TempWidgetProvider";

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	                     int[] appWidgetIds) {

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
		//intent.putExtra("one", 123);
		//intent.putExtra("one", 123);
		// Update the widgets via the service
		context.startService(intent);
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
				updateRemoteViews(context, widgetID);
				//Otherwise call our onUpdate() passing a one element array, with the retrieved ID.
			} else {
				this.onUpdate(context, AppWidgetManager.getInstance(context), new int[]{widgetID});
				Log.d("Provider: ", "Line 73");
			}
		} else {
			super.onReceive(context, intent);
		}
	}

	private void updateRemoteViews(Context context, int widgetID) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_mail_layout);

		//Create a database object and set the values here
		TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(context);

		//For the row ID
		String widget_id = Integer.toString(widgetID);

		//Extract the return data from the List and use it//

		//List to hold returned data
		List<String> db_data = new ArrayList<>();

		//Set the returned data = to the row's returned data
		db_data = db.getRow(widget_id);

		String user_name7, color_background_choice7, font_color_choice7, message_count_int7,
		message_count_string7, tag_chosen7;

		user_name7 = "Loading...";
		tag_chosen7 = "All";
		color_background_choice7 = "blue";
		font_color_choice7 = "white";
		message_count_int7 = "-1";
		message_count_string7 = "-1";

		try {
			user_name7 = db_data.get(2);
			Log.d("MailWidgetUpdateService Database username = ", user_name7);
			tag_chosen7 = db_data.get(19);
			Log.d("MailWidgetUpdateService Database tag chosen = ", tag_chosen7);
			color_background_choice7 = db_data.get(20);
			Log.d("MailWidgetUpdateService Database color background choice = ", color_background_choice7);
			font_color_choice7 = db_data.get(21);
			Log.d("MailWidgetUpdateService Database font color choice = ", font_color_choice7);
			message_count_int7 = db_data.get(3);
			Log.d("MailWidgetUpdateService Database message count int = ", message_count_int7);

			//Determine which tag chosen parameter was passed and return the mail respective to that call
			if (tag_chosen7.equalsIgnoreCase("All")) {
				message_count_string7 = db_data.get(4);
			} else if (tag_chosen7.equalsIgnoreCase("Correspondence")) {
				message_count_string7 = db_data.get(4);
			} else if (tag_chosen7.equalsIgnoreCase("Tutorial")) {
				message_count_string7 = db_data.get(5);
			} else if (tag_chosen7.equalsIgnoreCase("Medal")) {
				message_count_string7 = db_data.get(6);
			} else if (tag_chosen7.equalsIgnoreCase("Intelligence")) {
				message_count_string7 = db_data.get(7);
			} else if (tag_chosen7.equalsIgnoreCase("Alert")) {
				message_count_string7 = db_data.get(8);
			} else if (tag_chosen7.equalsIgnoreCase("Attack")) {
				message_count_string7 = db_data.get(9);
			} else if (tag_chosen7.equalsIgnoreCase("Colonization")) {
				message_count_string7 = db_data.get(10);
			} else if (tag_chosen7.equalsIgnoreCase("Complaint")) {
				message_count_string7 = db_data.get(11);
			} else if (tag_chosen7.equalsIgnoreCase("Excavator")) {
				message_count_string7 = db_data.get(12);
			} else if (tag_chosen7.equalsIgnoreCase("Mission")) {
				message_count_string7 = db_data.get(13);
			} else if (tag_chosen7.equalsIgnoreCase("Parliament")) {
				message_count_string7 = db_data.get(14);
			} else if (tag_chosen7.equalsIgnoreCase("Probe")) {
				message_count_string7 = db_data.get(15);
			} else if (tag_chosen7.equalsIgnoreCase("Spies")) {
				message_count_string7 = db_data.get(16);
			} else if (tag_chosen7.equalsIgnoreCase("Trade")) {
				message_count_string7 = db_data.get(17);
			} else if (tag_chosen7.equalsIgnoreCase("Fissure")) {
				message_count_string7 = db_data.get(18);
			}
			Log.d("Database", "Has been queried");
		} catch (Exception e){
			Log.d("Database", "Has NOT been queried");
		}

		//Set the remote views
		//Set the background color of the widget
		remoteViews.setInt(R.id.widget_mail_layout, "setBackgroundColor", android.graphics.Color.parseColor(color_background_choice7));

		//Set the font color of the widget text
		remoteViews.setInt(R.id.widget_mail_username, "setTextColor", android.graphics.Color.parseColor(font_color_choice7));
		remoteViews.setInt(R.id.widget_mail_message_count, "setTextColor", android.graphics.Color.parseColor(font_color_choice7));
		remoteViews.setInt(R.id.widget_mail_tag_choice, "setTextColor", android.graphics.Color.parseColor(font_color_choice7));

		remoteViews.setFloat(R.id.widget_mail_tag_choice, "setTextSize", 10);

		int messages_with_tag7;

		if (tag_chosen7.equalsIgnoreCase("All")){
			Log.d("Message count string is at:", message_count_int7);
			remoteViews.setTextViewText(R.id.widget_mail_message_count, message_count_int7);
			messages_with_tag7 = Integer.parseInt(message_count_int7);
		} else {
			Log.d("Message count string is at:", message_count_string7);
			remoteViews.setTextViewText(R.id.widget_mail_message_count, message_count_string7);
			messages_with_tag7 = Integer.parseInt(message_count_string7);
		}

		//Set the remote views dependent upon new data received
		//Check the number of messages and adjust the font size of the number of messages displayed. Prevents out of bounds on screen
		Log.d("Num messages", Integer.toString(messages_with_tag7));
		if (messages_with_tag7 < 10){
			remoteViews.setFloat(R.id.widget_mail_message_count, "setTextSize", 32);
		} else if (messages_with_tag7 >=10 && messages_with_tag7 <100){
			remoteViews.setFloat(R.id.widget_mail_message_count, "setTextSize", 28);
		} else if (messages_with_tag7 >= 100 && messages_with_tag7 <999){
			remoteViews.setFloat(R.id.widget_mail_message_count, "setTextSize", 24);
		} else {
			remoteViews.setFloat(R.id.widget_mail_message_count, "setTextSize", 20);
		}

		//Set the username
		remoteViews.setTextViewText(R.id.widget_mail_username, user_name7);

		//Close the database
		try{
			db.close();
			Log.d("Database", "Has been closed");
		} catch (Exception e){
			Log.d("Database", "Could not be closed!");
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		for (int appWidgetId : appWidgetIds)
		{
			cancelAlarmManager(context, appWidgetId);
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