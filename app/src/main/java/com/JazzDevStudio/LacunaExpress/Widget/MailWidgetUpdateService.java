package com.JazzDevStudio.LacunaExpress.Widget;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.MISCClasses.SharedPrefs;
import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.SelectMessageActivity2;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;
import static com.JazzDevStudio.LacunaExpress.MISCClasses.L.longInfo;

/**
 * Useful information on this class setup @: http://programmerbruce.blogspot.com/2011/04/simple-complete-app-widget-part-1.html
 */
//This class manages the service for the Mail widget updating
public class MailWidgetUpdateService extends IntentService implements serverFinishedListener {

	//Shared Preferences Stuff
	public static final String PREFS_NAME = "LacunaExpress";
	SharedPrefs sp = new SharedPrefs();
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	//Package Name
	private static final String PACKAGE_NAME = MailWidgetUpdateService.class.getPackage().getName();
	//For Log statements
	private static final String TAG = "MailWidgetUpdateService:";

	//App widget manager
	AppWidgetManager awm;

	//For pinging the server
	String message_count_string;
	int message_count_int;

	//Account / Message info
	AccountInfo selectedAccount;
	//For storing all account files
	ArrayList<AccountInfo> accounts;
	//ArrayList of display strings for the spinner
	ArrayList<String> user_accounts = new ArrayList<String>();
	//Messages Info
	ArrayList<Response.Messages> messages_array = new ArrayList<Response.Messages>();
	Boolean messagesReceived = false;

	//Constructor
	public MailWidgetUpdateService(String name){
		super(name);
	}

	//Creates a service named MailWidgetUpdateService
	public MailWidgetUpdateService(){
		this("MailWidgetUpdateService");
	}

	protected void onHandleIntent(Intent intent) {
		awm = AppWidgetManager.getInstance(this);
		int incomingAppWidgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);

		//Shared preferences
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();

		//GET all values from shared preferences
		String str = Integer.toString(incomingAppWidgetId);
		String sync_freq = sp.getString(settings, str + "::" + "sync_frequency", "15");
		Log.d("Shared Preferences Pulled: Sync Freq", sync_freq);

		if (incomingAppWidgetId != INVALID_APPWIDGET_ID) {
			//Update one widget if == 1s
			updateOneAppWidget(awm, incomingAppWidgetId);
		} else {
			//Update all widgets if >1
			updateAllAppWidgets(awm);
		}

		//Schedules the next update
		scheduleNextUpdate(sync_freq);
	}

	/**
	 * Schedules the next App Widget update to occur  as per
	 * the user defined update / sync interval.  Any
	 * previously scheduled App Widget update is effectively
	 * canceled and replaced by the newly scheduled update.
	 *
	 * The scheduled update does not wake the device up.  If
	 * the update is scheduled to start while the device is
	 * asleep, it will not run until the next time the device
	 * is awake.
	 */
	private void scheduleNextUpdate(String sync_interval) {
		Intent changeWidgetIntent = new Intent(this, this.getClass());
		// A content URI for this Intent may be unnecessary.
		changeWidgetIntent.setData(Uri.parse("content://" + PACKAGE_NAME + "/change_passcode"));
		PendingIntent pendingIntent1 = PendingIntent.getService(this, 0, changeWidgetIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		//Great AlarmManager Tutorial - http://www.programcreek.com/java-api-examples/index.php?api=android.app.AlarmManager
		int sync_interval_int = Integer.parseInt(sync_interval);
		// The update frequency should be user configurable.
		final Calendar c=Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		c.add(Calendar.MINUTE,sync_interval_int); //Add X minutes where X is the sync interval

		//Manages the sync interval
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC, c.getTimeInMillis(), pendingIntent1); //Update the timer to match the new sync time
	}

	/**
	 * For each widget on the user's home screen, updates its display
	 * with all the information and registers click handling for its buttons.
	 */
	private void updateAllAppWidgets(AppWidgetManager appWidgetManager) {
		ComponentName appWidgetProvider = new ComponentName(this, MailWidgetManager.class);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(appWidgetProvider);
		int N = appWidgetIds.length;
		for (int i = 0; i < N; i++)
		{
			int appWidgetId = appWidgetIds[i];
			updateOneAppWidget(appWidgetManager, appWidgetId);
		}
	}

	/**
	 * Updates the individual widgets passed in via the for loop
	 * and registers click handling for its buttons.
	 */
	private void updateOneAppWidget(AppWidgetManager appWidgetManager, int app_widget_ID) {

		//Shared preferences
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();

		String str = Integer.toString(app_widget_ID);
		String user_name = sp.getString(settings, str + "::" + "chosen_accout_string", "Silmarilos (US1)");
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

		//Setup and send server request code
		//This block populates user_accounts for values to display in the select account spinner
		ReadInAccounts();
		if(accounts.size() == 1){
			selectedAccount = accounts.get(0);
			Log.d("SelectMessage.Initialize", "only 1 account setting as default"+selectedAccount.displayString);
			user_accounts.add(selectedAccount.displayString);
		} else{
			for(AccountInfo i: accounts){
				Log.d("SelectMessage.Initialize", "Multiple accounts found, Setting Default account to selected account: "+i.displayString); //
				user_accounts.add(i.displayString);
				if(i.defaultAccount)
					selectedAccount = i;
			}
		}
		//This will read the username like Silmarilos (US1) or TMT (PT)

		if (tag_chosen.equalsIgnoreCase("All")){
			//Check the account via the spinner chosen
			selectedAccount = AccountMan.GetAccount(user_name);
			Log.d("SelectMessage.onItemSelected", "Tag All Calling View Inbox");
			//
			String request = Inbox.ViewInbox(selectedAccount.sessionID);
			Log.d("Select Message Activity, SelectedAccount", selectedAccount.userName);
			Log.d("SelectMessage.OnSelectedItem Request to server", request);
			ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
			AsyncServer s = new AsyncServer();
			s.addListener(this);
			s.execute(sRequest);
		} else {
			//Check the account via the spinner chosen
			selectedAccount = AccountMan.GetAccount(user_name);
			Log.d("SelectMessage.onItemSelected", "Tag Word in spinner Calling View Inbox");
			String request = Inbox.ViewInbox(selectedAccount.sessionID, tag_chosen);
			Log.d("SelectMessage.OnSelectedItem Request to server", request);
			Log.d("Select Message Activity, SelectedAccount", selectedAccount.userName);
			ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
			AsyncServer s = new AsyncServer();
			s.addListener(this);
			s.execute(sRequest);
		}

		//Setup a remoteview referring to the context (Param1) and relating to the widget (Param2)
		RemoteViews v1 = new RemoteViews(PACKAGE_NAME, R.layout.widget_mail_layout);

		//Set the username
		v1.setTextViewText(R.id.widget_mail_username, selectedAccount.userName);
		//Set the message count

		if (tag_chosen.equalsIgnoreCase("All")){
			message_count_string = Integer.toString(message_count_int);
			v1.setTextViewText(R.id.widget_mail_message_count, message_count_string);
		} else {
			v1.setTextViewText(R.id.widget_mail_message_count, message_count_string);
		}

		//Set the Tag choice
		String tag_chosen_v1 = "Tag Chosen:\n" + tag_chosen;
		v1.setTextViewText(R.id.widget_mail_tag_choice, tag_chosen_v1);

		//Set the background color of the widget
		v1.setInt(R.id.widget_mail_layout, "setBackgroundColor", android.graphics.Color.parseColor(color_background_choice));

		//Set the font color of the widget text
		v1.setInt(R.id.widget_mail_username, "setTextColor", android.graphics.Color.parseColor(font_color_choice));
		v1.setInt(R.id.widget_mail_message_count, "setTextColor", android.graphics.Color.parseColor(font_color_choice));
		v1.setInt(R.id.widget_mail_tag_choice, "setTextColor", android.graphics.Color.parseColor(font_color_choice));

		v1.setFloat(R.id.widget_mail_tag_choice, "setTextSize", 10);

		//Check the number of messages and adjust the font size of the number of messages displayed. Prevents out of bounds on screen
		int total_num_messages = Integer.parseInt(message_count_string);
		Log.d("Num messages", message_count_string);

		if (total_num_messages < 10){
			v1.setFloat(R.id.widget_mail_message_count, "setTextSize", 32);
		} else if (total_num_messages >=10 && total_num_messages <100){
			v1.setFloat(R.id.widget_mail_message_count, "setTextSize", 28);
		} else if (total_num_messages >= 100 && total_num_messages <999){
			v1.setFloat(R.id.widget_mail_message_count, "setTextSize", 24);
		} else {
			v1.setFloat(R.id.widget_mail_message_count, "setTextSize", 20);
		}

		//IMPORTANT! The following code opens the class when clicked
		Intent intent = new Intent(this, SelectMessageActivity2.class);
		//A pending intent to launch upon clicking
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0); //THIS may need to be changed from 'this'
		//Set the onClickListener the TEXTVIEW. If the click the textview, it opens up the SelectMessageActivity2
		v1.setOnClickPendingIntent(R.id.widget_mail_message_count, pendingIntent);
		//Update the widget with the remote view
		appWidgetManager.updateAppWidget(app_widget_ID, v1);

	}

	//Reads in the accounts from the existing objects
	private void ReadInAccounts() {
		Log.d("SelectAccountActivity.ReadInAccounts", "checking for file" + AccountMan.CheckForFile());
		accounts = AccountMan.GetAccounts();
		Log.d("SelectAccountActivity.ReadInAccounts", String.valueOf(accounts.size()));
	}

	//Parse the code
	public void onResponseReceived(String reply) {
		longInfo(reply); //Print out the data

		if(!reply.equals("error")) {
			Log.d("Deserializing Response", "Creating Response Object");
			messagesReceived = true;
			//Getting new messages, clearing list first.
			Response r = new Gson().fromJson(reply, Response.class);
			messages_array.clear();
			messages_array = r.result.messages;

			Log.d("295", "Hit");
			message_count_int = r.result.status.empire.has_new_messages;
			message_count_string = r.result.message_count;

		} else {
			Log.d("Error with Reply", "Error in onResponseReceived()");
		}
	}


	//NOT USED ATM

			/*
			//For displaying really long Strings (IE JSON Requests)
			public static void longInfo(String str) {
				if(str.length() > 4000) {
					Log.i("Lengthy String", str.substring(0, 4000));
					longInfo(str.substring(4000));
				} else
					Log.i("Lengthy String", str);
			}
			 */


			/*
			/**
			 * Configures "Save Code" button clicks to pass the current
			 * passcode of the parent app widget to the save passcode
			 * Activity.
			 */
			/*
			private void setSavePasscodeIntent(RemoteViews views, int appWidgetId, String newRandomPasscode) {
				Intent intent = new Intent(this, SaveRandomPasscodeActivity.class);
				intent.setData(Uri.parse("content://" +
						PACKAGE_NAME + "/save_passcode/widget_id/" +
						appWidgetId));
				intent.putExtra("PASSCODE", newRandomPasscode);
				PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				views.setOnClickPendingIntent(R.id.save_passcode_button, pendingIntent2);
			}
			*/

			/**
			 * Configures "New Code" button clicks to generate and set a
			 * new passcode on the parent app widget.
			 */
			/*
			private void setChangePasscodeIntent(RemoteViews views, int appWidgetId) {
				Intent intent = new Intent(this, this.getClass());
				intent.setData(Uri.parse("content://" +
						PACKAGE_NAME + "/change_passcode/widget_id/" +
						appWidgetId));
				intent.putExtra(EXTRA_APPWIDGET_ID,
						appWidgetId);
				PendingIntent pendingIntent3 = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				views.setOnClickPendingIntent(R.id.new_passcode_button, pendingIntent3);
			}
			*/
}
