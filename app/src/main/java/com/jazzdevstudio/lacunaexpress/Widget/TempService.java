package com.JazzDevStudio.LacunaExpress.Widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.JazzDevStudio.LacunaExpress.MISCClasses.SharedPrefs;
import com.JazzDevStudio.LacunaExpress.R;

import java.util.Random;

public class TempService extends Service{

	private static final String LOG = "de.vogella.android.widget.example";

	//Shared Preferences Stuff
	public static final String PREFS_NAME = "LacunaExpress";
	SharedPrefs sp = new SharedPrefs();
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i(LOG, "Called");
		// create some random data

		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());

		//Pulls the widget IDs into an array
		int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

		ComponentName thisWidget = new ComponentName(getApplicationContext(), MailWidgetManager.class);
		int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);
		Log.w(LOG, "From Intent" + String.valueOf(allWidgetIds.length));
		Log.w(LOG, "Direct" + String.valueOf(allWidgetIds2.length));

		for (int i = 0; i< allWidgetIds.length; i++){
			int widgetID = allWidgetIds[i];
			int widgetID2 = allWidgetIds2[i];
			Log.d("Widget ID 1: ", Integer.toString(widgetID));
			Log.d("Widget ID 2: ", Integer.toString(widgetID2));
		}

		for (int widgetId : allWidgetIds) {
			//Shared preferences
			settings = getSharedPreferences(PREFS_NAME, 0);
			editor = settings.edit();

			String message_count_string;
			int message_count_int;

			String str = Integer.toString(widgetId);
			String user_name = sp.getString(settings, str + "::" + "chosen_accout_string", "Silmarilos  (US1)");
			message_count_string = sp.getString(settings, str + "::" + "message_count_string", "1000000"); //String defined in global
			String tag_chosen = sp.getString(settings, str + "::" + "tag_chosen", "All");
			String color_background_choice = sp.getString(settings, str + "::" + "color_background_choice", "White");
			String font_color_choice = sp.getString(settings, str + "::" + "font_color_choice", "Black");

			//Int converted from message_count_string
			message_count_int = Integer.parseInt(message_count_string);

			//Check values in logs
			Log.d("Shared Preferences Pulled: user_name", user_name);
			Log.d("Shared Preferences Pulled: message_count_string", message_count_string);
			Log.d("Shared Preferences Pulled: tag_chosen", tag_chosen);
			Log.d("Shared Preferences Pulled: color_background_choice", color_background_choice);
			Log.d("Shared Preferences Pulled: font_color_choice", font_color_choice);

			// create some random data
			Log.d("Widget IDs are: ", Integer.toString(widgetId));
			int number = (new Random().nextInt(100));

			RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.widget_mail_layout);
			Log.w("WidgetExample", String.valueOf(number));
			// Set the text
			remoteViews.setTextViewText(R.id.widget_mail_message_count,
					"Random: " + String.valueOf(number));

			// Register an onClickListener
			Intent clickIntent = new Intent(this.getApplicationContext(),
					MailWidgetManager.class);

			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
					allWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.widget_mail_message_count, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
		stopSelf();

		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
