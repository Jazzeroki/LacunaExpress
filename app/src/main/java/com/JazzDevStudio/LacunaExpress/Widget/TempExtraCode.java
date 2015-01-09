package com.JazzDevStudio.LacunaExpress.Widget;

/**
 * Created by PatrickSSD2 on 1/8/2015.
 */
public class TempExtraCode {

		/*
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

		//Issue begins here with passing in of widgetIDs. Need to look into them again

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
			String sync_freq = sp.getString(settings, str + "::" + "sync_frequency", "1");

			//Int converted from message_count_string
			message_count_int = Integer.parseInt(message_count_string);

			//Check values in logs
			Log.d("Shared Preferences Pulled: user_name", user_name);
			Log.d("Shared Preferences Pulled: message_count_string", message_count_string);
			Log.d("Shared Preferences Pulled: tag_chosen", tag_chosen);
			Log.d("Shared Preferences Pulled: color_background_choice", color_background_choice);
			Log.d("Shared Preferences Pulled: font_color_choice", font_color_choice);

			//Testing to see if I can get the timer to work
			scheduleNextUpdate(sync_freq);


			// create some random data
			Log.d("Widget IDs are: ", Integer.toString(widgetId));
			int number = (new Random().nextInt(100));

			RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.widget_mail_layout);
			Log.w("WidgetExample", String.valueOf(number));
			// Set the text

			String tag_chosen_v1 = "Tag Chosen:\n" + tag_chosen;
			remoteViews.setTextViewText(R.id.widget_mail_tag_choice, tag_chosen_v1);

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

	private void scheduleNextUpdate(String sync_interval) {
		Intent changeWidgetIntent = new Intent(this, this.getClass());
		// A content URI for this Intent may be unnecessary.
		changeWidgetIntent.setData(Uri.parse("content://" + PACKAGE_NAME + "/change_passcode"));
		PendingIntent pendingIntent1 = PendingIntent.getService(this, 0, changeWidgetIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		//Great AlarmManager Tutorial - http://www.programcreek.com/java-api-examples/index.php?api=android.app.AlarmManager
		int sync_interval_int = Integer.parseInt(sync_interval);
		// The update frequency should be user configurable.
		Log.d("TIME TO LOOK: Current:", Long.toString(System.currentTimeMillis()));
		final Calendar c=Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		c.add(Calendar.MINUTE,sync_interval_int); //Add X minutes where X is the sync interval
		Log.d("TIME TO LOOK: Update", Long.toString(c.getTimeInMillis()));

		//Manages the sync interval
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC, c.getTimeInMillis(), pendingIntent1); //Update the timer to match the new sync time
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	*/
}

