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

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.MISCClasses.SharedPrefs;
import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TempService extends Service implements serverFinishedListener {
	private static final String LOG = "com.JazzDevStudio.LacunaExpress.Widget.TempService";

	//Shared Preferences Stuff
	public static final String PREFS_NAME = "LacunaExpress";
	SharedPrefs sp = new SharedPrefs();
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	//Account info
	AccountInfo selectedAccount;
	//For storing all account files
	ArrayList<AccountInfo> accounts;
	//ArrayList of display strings for the spinner
	ArrayList<String> user_accounts = new ArrayList<String>();

	//Messages Info
	ArrayList<Response.Messages> messages_array = new ArrayList<Response.Messages>();
	Boolean messagesReceived = false;
	private String tag_chosen = "All";

	String message_count_string_test;
	int message_count_int_test;

	//Object
	//MailCountObjects mo = new MailCountObjects();

	@Override
	public void onCreate() {
		super.onCreate();

		message_count_string_test = "-2";
		message_count_int_test = -2;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i(LOG, "Called");

		//Begin pulling data:
		//This block populates user_accounts for values to display in the select account spinner
		ReadInAccounts();
		if(accounts.size() == 1){
			selectedAccount = accounts.get(0);
			Log.d("SelectMessage.Initialize", "only 1 account setting as default" + selectedAccount.displayString);
			user_accounts.add(selectedAccount.displayString);
		} else{
			for(AccountInfo i: accounts){
				Log.d("SelectMessage.Initialize", "Multiple accounts found, Setting Default account to selected account: "+i.displayString); //
				user_accounts.add(i.displayString);
				if(i.defaultAccount)
					selectedAccount = i;
			}
		}

		Log.d(LOG, "OnStart Called");
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());

		int[] allWidgetIds = intent
				.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

		ComponentName thisWidget = new ComponentName(getApplicationContext(),
				TempWidgetProvider.class);
		int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);
		Log.w(LOG, "From Intent" + String.valueOf(allWidgetIds.length));
		Log.w(LOG, "Direct" + String.valueOf(allWidgetIds2.length));

		int counter = 1;
		//All widget IDs are passed through this for loop. Run calculations / set text fields here
		for (int widgetId : allWidgetIds) {


			//Retrieve all of the data from the shared preferences held via app widget ID
			settings = getSharedPreferences(PREFS_NAME, 0);
			editor = settings.edit();

			String str = Integer.toString(widgetId);



			String user_name = sp.getString(settings, str + "::" + "chosen_accout_string", "Loading...");
			String tag_chosen = sp.getString(settings, str + "::" + "tag_chosen", "All");
			String color_background_choice = sp.getString(settings, str + "::" + "color_background_choice", "White");
			String font_color_choice = sp.getString(settings, str + "::" + "font_color_choice", "Black");
			//These 2 will be defined when a response is received from the server, still left in default values however.
			//message_count_string = "1000000";//sp.getString(settings, str + "::" + "message_count_string", "1000000"); //String defined in global
			//message_count_int = 1000000;//(int) sp.getInt(settings, str + "::" + "message_count_int", 1000000);

			AccountMan.GetAccount(user_name);
			//Depending on tag chosen, different URI request sent in JSON
			if (tag_chosen.equalsIgnoreCase("All")){
				Log.d("SelectMessage.onItemSelected", "Second Spinner Tag All Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				Log.d("Select Message Activity, SelectedAccount", user_name);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();
				s.addListener(this);
				s.execute(sRequest);
			} else {
				Log.d("SelectMessage.onItemSelected", "Second Spinner word in spinner All Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID, tag_chosen);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				Log.d("Select Message Activity, SelectedAccount", user_name);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();
				s.addListener(this);
				s.execute(sRequest);
			}







			Log.d("Widget ID in Service: ", Integer.toString(widgetId));
			Log.d("Counter is at: ", Integer.toString(counter));
			counter++;

			RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.widget_mail_layout);

			// Register an onClickListener
			Intent clickIntent = new Intent(this.getApplicationContext(),
					TempWidgetProvider.class);



			//Set all remote IDs with respective texts
			//Set the username
			remoteViews.setTextViewText(R.id.widget_mail_username, user_name);
			//Set the message count

			if (tag_chosen.equalsIgnoreCase("All")){
				String temp_message_count = Integer.toString(message_count_int_test);
				Log.d("Message count string is at:", temp_message_count);
				remoteViews.setTextViewText(R.id.widget_mail_message_count, temp_message_count);
				Log.d("Firing 1", "Firing 1");
			} else {
				Log.d("Message count string is at:", message_count_string_test);
				remoteViews.setTextViewText(R.id.widget_mail_message_count, message_count_string_test);
				Log.d("Firing 2", "Firing 2");
			}

			//Set the Tag choice
			String tag_chosen_v1 = "Tag Chosen:\n" + tag_chosen;
			remoteViews.setTextViewText(R.id.widget_mail_tag_choice, tag_chosen_v1);

			Log.d("Background choice is: ", color_background_choice);
			Log.d("Font color is: ", font_color_choice);

			//Set the background color of the widget
			remoteViews.setInt(R.id.widget_mail_layout, "setBackgroundColor", android.graphics.Color.parseColor(color_background_choice));

			//Set the font color of the widget text
			remoteViews.setInt(R.id.widget_mail_username, "setTextColor", android.graphics.Color.parseColor(font_color_choice));
			remoteViews.setInt(R.id.widget_mail_message_count, "setTextColor", android.graphics.Color.parseColor(font_color_choice));
			remoteViews.setInt(R.id.widget_mail_tag_choice, "setTextColor", android.graphics.Color.parseColor(font_color_choice));

			remoteViews.setFloat(R.id.widget_mail_tag_choice, "setTextSize", 10);

			//Check the number of messages and adjust the font size of the number of messages displayed. Prevents out of bounds on screen
			int total_num_messages = Integer.parseInt(message_count_string_test);
			Log.d("Num messages", message_count_string_test);
			if (total_num_messages < 10){
				remoteViews.setFloat(R.id.widget_mail_message_count, "setTextSize", 32);
			} else if (total_num_messages >=10 && total_num_messages <100){
				remoteViews.setFloat(R.id.widget_mail_message_count, "setTextSize", 28);
			} else if (total_num_messages >= 100 && total_num_messages <999){
				remoteViews.setFloat(R.id.widget_mail_message_count, "setTextSize", 24);
			} else {
				remoteViews.setFloat(R.id.widget_mail_message_count, "setTextSize", 20);
			}

			/* Finished setting remoteViews */

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

	//Reads in the accounts from the existing objects
	private void ReadInAccounts() {
		Log.d("SelectAccountActivity.ReadInAccounts", "checking for file" + AccountMan.CheckForFile());
		//accounts.clear(); //Clear any leftover accounts
		accounts = AccountMan.GetAccounts();
		Log.d("SelectAccountActivity.ReadInAccounts", String.valueOf(accounts.size()));
	}

	//When a response is received from the server
	public void onResponseReceived(String reply) {
		if(!reply.equals("error")) {
			Log.d("Deserializing Response", "Creating Response Object");
			messagesReceived = true;
			//Getting new messages, clearing list first.
			Response r = new Gson().fromJson(reply, Response.class);
			messages_array.clear();
			messages_array = r.result.messages;

			int message_count_int_received = r.result.status.empire.has_new_messages;
			String message_count_string_received = r.result.message_count;

			Log.d("Message Count local string = ", message_count_string_received);
			Log.d("Message Count local int = ", Integer.toString(message_count_int_received));

			message_count_string_test = message_count_string_received;
			message_count_int_test = message_count_int_received;

			Log.d("Message Count global string = ", message_count_string_test);
			Log.d("Message Count global int = ", Integer.toString(message_count_int_test));

		} else {
			Log.d("Error with Reply", "Error in onResponseReceived()");
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(LOG, "IBinder Called");
		return null;
	}
}