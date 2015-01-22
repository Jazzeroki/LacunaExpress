package com.JazzDevStudio.LacunaExpress.Widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.Database.TEMPDatabaseAdapter;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.MISCClasses.SharedPrefs;
import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.SelectMessageActivity2;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Useful information on this class setup @: http://programmerbruce.blogspot.com/2011/04/simple-complete-app-widget-part-1.html
 */
//This class manages the service for the Mail widget updating
public class MailWidgetUpdateService extends Service implements serverFinishedListener {
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

	int awid;

	String message_count_string, message_count_int;

	@Override
	public void onCreate() {
		super.onCreate();
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
				MailWidgetProvider.class);
		int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);
		Log.w(LOG, "From Intent" + String.valueOf(allWidgetIds.length));
		Log.w(LOG, "Direct" + String.valueOf(allWidgetIds2.length));

		int counter = 1;
		//All widget IDs are passed through this for loop. Run calculations / set text fields here
		for (int widgetId : allWidgetIds) {

			awid = widgetId;

			//Retrieve all of the data from the shared preferences held via app widget ID
			settings = getSharedPreferences(PREFS_NAME, 0);
			editor = settings.edit();

			String str = Integer.toString(widgetId);

			RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.widget_mail_layout);

			//Create a database object and set the values here
			TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(this);

			//For the row ID
			String widget_id = Integer.toString(awid);

			//List to hold returned data
			List<String> db_data = new ArrayList<>();

			//Set the returned data = to the row's returned data
			db_data = db.getRow(widget_id);

			//Extract the return data from the List and use it

			String user_name = sp.getString(settings, str + "::" + "chosen_accout_string", "Loading...");
			Log.d("Service username passed is: ", user_name);
			String tag_chosen = sp.getString(settings, str + "::" + "tag_chosen", "All");
			String color_background_choice = sp.getString(settings, str + "::" + "color_background_choice", "White");
			String font_color_choice = sp.getString(settings, str + "::" + "font_color_choice", "Black");
			//These 2 will be defined when a response is received from the server, still left in default values however.
			String message_count_string = sp.getString(settings, str + "::" + "message_count_string", "1000000"); //String defined in global
			String message_count_int = sp.getString(settings, str + "::" + "message_count_int", "1000000");
			
			//Still need to implement add the data in as well



			AccountMan.GetAccount(user_name);
			//Depending on tag chosen, different URI request sent in JSON
			if (tag_chosen.equalsIgnoreCase("All")){
				Log.d("SelectMessage.onItemSelected", "Second Spinner Tag All Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				Log.d("Select Message Activity, SelectedAccount", user_name);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();

				UpdateRemoteViewsViaAsync s1 = new UpdateRemoteViewsViaAsync(MailWidgetUpdateService.this,
						remoteViews, widgetId, tag_chosen, appWidgetManager, user_name);

				s1.addListener(this);
				s1.execute(sRequest);
			} else {
				Log.d("SelectMessage.onItemSelected", "Second Spinner word in spinner All Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID, tag_chosen);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				Log.d("Select Message Activity, SelectedAccount", user_name);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();

				UpdateRemoteViewsViaAsync s1 = new UpdateRemoteViewsViaAsync(MailWidgetUpdateService.this,
						remoteViews, widgetId, tag_chosen, appWidgetManager, user_name);

				s1.addListener(this);
				s1.execute(sRequest);
			}

			Log.d("Widget ID in Service: ", Integer.toString(widgetId));
			Log.d("Counter is at: ", Integer.toString(counter));
			counter++;



			// Register an onClickListener
			Intent clickIntent = new Intent(this.getApplicationContext(),
					SelectMessageActivity2.class);



			//Set all remote IDs with respective texts

			//Set the message count


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

			/* Finished setting remoteViews */

			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
					allWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, clickIntent, 0);
			//Set the onClickListener the TEXTVIEW. If the click the textview, it opens up the SelectMessageActivity2
			remoteViews.setOnClickPendingIntent(R.id.widget_mail_message_count, pendingIntent);


			//PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,
			//PendingIntent.FLAG_UPDATE_CURRENT);
			//remoteViews.setOnClickPendingIntent(R.id.widget_mail_message_count, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
		stopSelf();
		Log.d("Service", "Has been Stopped");

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

		//To place data in respectice awids
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();

		if(!reply.equals("error")) {
			Log.d("Deserializing Response", "Creating Response Object");
			messagesReceived = true;
			//Getting new messages, clearing list first.
			Response r = new Gson().fromJson(reply, Response.class);
			messages_array.clear();
			messages_array = r.result.messages;

			int message_count_int_received = r.result.status.empire.has_new_messages;
			String message_count_string_received = r.result.message_count;

		} else {
			Log.d("Error with Reply", "Error in onResponseReceived()");
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(LOG, "IBinder Called");
		return null;
	}

	//Async task to both run the server code and adjust the remoteviews when finished
	private class UpdateRemoteViewsViaAsync extends AsyncTask<ServerRequest, Void, String> {
		List<serverFinishedListener> listeners = new ArrayList<serverFinishedListener>();
		private String output = "";

		private RemoteViews rv1;
		private int appwid;
		private String tag_chosen_async;
		private AppWidgetManager appWidgetManager;
		private String user_name;

		private String async_message_count_string;
		private int async_message_count_int;

		public UpdateRemoteViewsViaAsync(Context c, RemoteViews rv, int appwid, String aTag,
		                                 AppWidgetManager aAppWidgetManager, String username){
			this.appwid = appwid;
			this.rv1 = rv;
			this.tag_chosen_async = aTag;
			this.appWidgetManager = aAppWidgetManager;
			this.user_name = username;
			Log.d("Output from my Async, username is ", user_name);
			Log.d("Output from my Async, Constructor:", "Here");
		}
		public void addListener(serverFinishedListener toAdd) {
			listeners.add(toAdd);
		}

		protected String doInBackground(ServerRequest... a) {

			Log.d("Output from my Async, doInBackground:", "Here");

			output = ServerRequest(a[0].server, a[0].methodURL, a[0].json);
			convertData(output);
			Log.d("Output from my Async", output);
			//ResponseReceived();
			return output;
		}

		private void ResponseReceived() {
			//Log.d("Firing Event", "Sending out response to listeners");
			Log.d("Output from my Async, onResponseReceived:", "Here");
			for (serverFinishedListener i : listeners) {
				i.onResponseReceived(output);
			}
			listeners.clear();
		}

		@Override
		protected void onPostExecute(String r) {
			//Log.d("OnPostExecute", "Firing On Post Execute");
			Log.d("Output from my Async, onPostExecute:", "Here");
			ResponseReceived();

			String messages_with_tag;

			if (tag_chosen_async.equalsIgnoreCase("All")){
				Log.d("Message count string is at:", Integer.toString(async_message_count_int));
				rv1.setTextViewText(R.id.widget_mail_message_count, Integer.toString(async_message_count_int));
				messages_with_tag = Integer.toString(async_message_count_int);
			} else {
				Log.d("Message count string is at:", async_message_count_string);
				rv1.setTextViewText(R.id.widget_mail_message_count, async_message_count_string);
				messages_with_tag = async_message_count_string;
			}

			//Set the remote views dependent upon new data received
			//Check the number of messages and adjust the font size of the number of messages displayed. Prevents out of bounds on screen
			int total_num_messages = Integer.parseInt(messages_with_tag); //Conversion is needed, cannot use old one here as diff if statement is in effect
			Log.d("Num messages", messages_with_tag);
			if (total_num_messages < 10){
				rv1.setFloat(R.id.widget_mail_message_count, "setTextSize", 32);
			} else if (total_num_messages >=10 && total_num_messages <100){
				rv1.setFloat(R.id.widget_mail_message_count, "setTextSize", 28);
			} else if (total_num_messages >= 100 && total_num_messages <999){
				rv1.setFloat(R.id.widget_mail_message_count, "setTextSize", 24);
			} else {
				rv1.setFloat(R.id.widget_mail_message_count, "setTextSize", 20);
			}

			//Set the username
			rv1.setTextViewText(R.id.widget_mail_username, user_name);

			//Finally, update the widget
			appWidgetManager.updateAppWidget(appwid, rv1);
		}

		public String ServerRequest(String gameServer, String methodURL, String JsonRequest) {
			Log.d("Output from my Async, ServerRequest:", "Here");
			try {
				Log.d("AsyncServer.ServerRequest URL", (gameServer + "/" + methodURL));
				Log.d("AsyncServer.ServerRequest", "Request string " + JsonRequest);
				URL url = new URL(gameServer + "/" + methodURL);
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(JsonRequest);
				out.close();
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				output = in.readLine();
				Log.d("AsyncServer.ServerRequest", "Reply string " + output);
			} catch (java.net.MalformedURLException e) {
				Log.d("Server Error", "Malformed URL Exception");
				output = "error";
			} catch (java.io.IOException e) {
				Log.d("Server Error", "Malformed IO Exception");
				output = "error";
			}
			return output;

		}

		public void convertData(String reply) {

			//To place data in respectice awids
			settings = getSharedPreferences(PREFS_NAME, 0);
			editor = settings.edit();

			if(!reply.equals("error")) {
				Log.d("Deserializing Response", "Creating Response Object");
				messagesReceived = true;
				//Getting new messages, clearing list first.
				Response r = new Gson().fromJson(reply, Response.class);
				messages_array.clear();
				messages_array = r.result.messages;

				async_message_count_int = r.result.status.empire.has_new_messages;
				async_message_count_string = r.result.message_count;
			}
		}
	}
}