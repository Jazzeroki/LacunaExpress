package com.JazzDevStudio.LacunaExpress.Widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

/**
 * This will configure the widget that is placed on the user's homescreen.
 */
public class TEMPMailWidgetProvider extends AppWidgetProvider {

	private static final String LOG = "com.JazzDevStudio.LacunaExpress.Widget.TempMailWidgetProvider";
	public static final String URI_SCHEME = "Mail Widget:";
	private static final String ACTION_WIDGET_CONTROL = "com.JazzDevStudio.LacunaExpress.Widget.WIDGET_CONTROL";


	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	                     int[] appWidgetIds) {

		for (int appWidgetId : appWidgetIds) {

			try {
				WidgetState state = WidgetState.getState(context, appWidgetId);

				updateWidgetViaService(context, appWidgetId, true, false);


			} catch (Exception e) {
				// don't want one widget failure to interrupt the rest
				Log.e(LOG, "Failed updating: " + appWidgetId, e);
			}

		}
	}

	//When the widget is deleted, this gets called
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);

		//Check which ones are being deleted
		for (int appWidgetId : appWidgetIds) {

			// stop alarm
			setAlarm(context, appWidgetId, -1);

			// remove our stored state and delete it from the database
			WidgetState.deleteStateForId(context, appWidgetId);

			// update service
			updateWidgetViaService(context, appWidgetId, false, true);
		}

		super.onDeleted(context, appWidgetIds);
	}

	public void onReceive(Context context, Intent intent) {

		final String action = intent.getAction();
		Log.d(LOG, "OnReceive:Action: " + action);
		if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
			final int appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				this.onDeleted(context, new int[]{appWidgetId});
			}
		} else if (ACTION_WIDGET_CONTROL.equals(action)) {
			// pass this on to the action handler where we'll figure out what to do and update the widget
			final int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				this.onHandleAction(context, appWidgetId, intent.getData());
			}

		} else if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {

			if (!URI_SCHEME.equals(intent.getScheme())) {
				//If the scheme doesn't match, that means it wasn't from the alarm which means that
				//either it's the first time in (even before the configuration is done) or after a reboot/ update

				final int[] appWidgetIds = intent.getExtras().getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);

				for (int appWidgetId : appWidgetIds) {

					//Get current state
					WidgetState state = WidgetState.getState(context, appWidgetId);

					//Only start the alarm if it's not paused
					if (state.sync_frequency != Integer.toString(-1) ) {
						Log.i(LOG, "Starting recurring alarm for id: " + appWidgetId);

						setAlarm(context, appWidgetId, Integer.parseInt(state.sync_frequency));
					}
				}
			}
			super.onReceive(context, intent);
		} else {
			super.onReceive(context, intent);
		}
	}

	//Handles action intents. @Params: context, widget id for action, uri code path to use (Unique identifier)
	private void onHandleAction(Context context, int appWidgetId, Uri data) {

		//Get the state of the widget via the context and the widget ID
		WidgetState state = WidgetState.getState(context, appWidgetId);
		//Update the widget via the service
		updateWidgetViaService(context, appWidgetId, true, false);
		//Set the alarm for when the this will run again as per the user's sync frequency
		setAlarm(context, appWidgetId, Integer.parseInt(state.sync_frequency));
		//Save the new state of things
		WidgetState.storeState(context, appWidgetId, state);
	}

	//Helper method to start a service or sent an intent to a currently running service. Updates the app widget.
	//@Params: context, widget identifier, true to update, true STOPS the widget service thread
	private void updateWidgetViaService(Context context, int appWidgetId, boolean update_widget, boolean requestStop) {
		Intent intent = new Intent(context, TEMPMailWidgetUpdateService.class);

		intent.putExtra(TEMPMailWidgetUpdateService.EXTRA_FLAG_REQUEST_STOP, requestStop);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		intent.putExtra(TEMPMailWidgetUpdateService.EXTRA_FLAG_UPDATE, update_widget);

		context.startService(intent);
	}

	//Set the alarm for the service / update to run again.
	//@Params: context, widget identifier, number of minutes between alarms
	private void setAlarm(Context context, int appWidgetId, int sync_update_rate) {
		Intent widgetUpdate = new Intent();
		widgetUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		widgetUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});

		// make this pending intent unique by adding a scheme to it
		widgetUpdate.setData(Uri.withAppendedPath(Uri.parse(TEMPMailWidgetProvider.URI_SCHEME + "://widget/id/"), String.valueOf(appWidgetId)));
		PendingIntent newPending = PendingIntent.getBroadcast(context, 0, widgetUpdate, PendingIntent.FLAG_UPDATE_CURRENT);

		// schedule the updating
		AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		if (sync_update_rate >= 0) {
			alarms.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), sync_update_rate * 60 * 1000, newPending);
		} else {
			alarms.cancel(newPending);
		}
	}

}