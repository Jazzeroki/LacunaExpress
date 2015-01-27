package com.JazzDevStudio.LacunaExpress.Widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.Database.TEMPDatabaseAdapter;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.SelectMessageActivity2;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

//This class manages the service for the Mail widget updating
public class TEMPMailWidgetUpdateService extends Service implements serverFinishedListener {

	private static final String LOG = "com.JazzDevStudio.LacunaExpress.Widget.TempService";
	private static final String ACTION_WIDGET_CONTROL = "com.JazzDevStudio.LacunaExpress.Widget.WIDGET_CONTROL";
	public static final String EXTRA_FLAG_REQUEST_STOP = "requestStop";
	public static final String EXTRA_FLAG_UPDATE = "flagUpdate";

	List<String> server_data = new ArrayList<String>();

	Hashtable<Integer, UpdateThread> threadPool = new Hashtable<Integer, UpdateThread>();

	//Account info
	AccountInfo selectedAccount;
	//For storing all account files
	ArrayList<AccountInfo> accounts;
	//ArrayList of display strings for the spinner
	ArrayList<String> user_accounts = new ArrayList<String>();

	//Messages Info
	ArrayList<Response.Messages> messages_array = new ArrayList<Response.Messages>();
	Boolean messagesReceived = false;

	int awid;

	private String user_name, color_background_choice, font_color_choice, message_count_string,
			message_count_int, sync_frequency, sessionID, homePlanetID, message_count_returned_with_tag;
	//Message count int is ALL messages and message count string is specific tag messages. The message
	//Count returned with tag variable is set to one of the previous 2 message count vars depending on tag chosen
	private String tag_chosen = "All";


	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {

		int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

		boolean stopRequested = intent.getBooleanExtra(TEMPMailWidgetUpdateService.EXTRA_FLAG_REQUEST_STOP, false);

		if (threadPool.containsKey(appWidgetId)) {
			UpdateThread thread = threadPool.get(appWidgetId);
			if (thread.isAlive()) {

				if (stopRequested) {

					thread.requestStop();

					if (threadPool.isEmpty()) {
						// if there are no threads, we don't need to be running
						this.stopSelf();
					}
				} else {

					updateWidget(this, appWidgetId);

				}
			} else {
				Log.w(LOG, "Thread has died: " + appWidgetId);
			}
		} else {
			UpdateThread thread = new UpdateThread(appWidgetId, this);
			threadPool.put(appWidgetId, thread);
			thread.start();


			updateWidget(this, appWidgetId);
		}



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

		if(!reply.equals("error")) {
			Log.d("Deserializing Response", "Creating Response Object");
			messagesReceived = true;
			//Getting new messages, clearing list first.
			Response r = new Gson().fromJson(reply, Response.class);
			messages_array.clear();
			messages_array = r.result.messages;

			message_count_int = Integer.toString(r.result.status.empire.has_new_messages);
			message_count_string = r.result.message_count;

		} else {
			Log.d("Error with Reply", "Error in onResponseReceived()");
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}



	//Update the widget within the host. @Params: context, app widget identifier
	//This calls updateControlStateOfWidget to update the state
	private void updateWidget(Context context, int appWidgetId) {
		WidgetState state = WidgetState.getState(context, appWidgetId);

		RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_mail_layout);

		try {
			//Update ALL of the views
			if (tag_chosen.equalsIgnoreCase("All")) {
				remoteView.setTextViewText(R.id.widget_mail_message_count, message_count_int);
				message_count_returned_with_tag = message_count_int;
			} else {
				remoteView.setTextViewText(R.id.widget_mail_message_count, message_count_string);
				message_count_returned_with_tag = message_count_string;
			}

			//Check the number of messages and adjust the font size of the number of messages displayed. Prevents out of bounds on screen
			int total_num_messages = Integer.parseInt(message_count_returned_with_tag); //Conversion is needed, cannot use old one here as diff if statement is in effect
			Log.d("Num messages", message_count_returned_with_tag);
			if (total_num_messages < 10) {
				remoteView.setFloat(R.id.widget_mail_message_count, "setTextSize", 32);
			} else if (total_num_messages >= 10 && total_num_messages < 100) {
				remoteView.setFloat(R.id.widget_mail_message_count, "setTextSize", 28);
			} else if (total_num_messages >= 100 && total_num_messages < 999) {
				remoteView.setFloat(R.id.widget_mail_message_count, "setTextSize", 24);
			} else {
				remoteView.setFloat(R.id.widget_mail_message_count, "setTextSize", 20);
			}

			//Set the username
			remoteView.setTextViewText(R.id.widget_mail_username, user_name);

			//To change the appearance and add a new line
			String tag_chosen_v1 = "Tag Chosen:\n" + tag_chosen;
			remoteView.setTextViewText(R.id.widget_mail_tag_choice, tag_chosen_v1);

			Log.d("Background choice is: ", color_background_choice);
			Log.d("Font color is: ", font_color_choice);

			//Set the background color of the widget
			remoteView.setInt(R.id.widget_mail_layout, "setBackgroundColor", android.graphics.Color.parseColor(color_background_choice));

			//Set the font color of the widget text
			remoteView.setInt(R.id.widget_mail_username, "setTextColor", android.graphics.Color.parseColor(font_color_choice));
			remoteView.setInt(R.id.widget_mail_message_count, "setTextColor", android.graphics.Color.parseColor(font_color_choice));
			remoteView.setInt(R.id.widget_mail_tag_choice, "setTextColor", android.graphics.Color.parseColor(font_color_choice));

			remoteView.setFloat(R.id.widget_mail_tag_choice, "setTextSize", 10);


			// modify remoteView based on current state
			updateControlStateOfWidget(context, remoteView, state, appWidgetId);
		}catch (Exception e){
			Log.d("TempMailWidgetUpdateService", "Try failed");
		}
	}

	//Modify controls displayed and set the respective pending intents
	//@Params: context, remoteView to modify (IE textview), widgetstate to use, widget ID being referenced
	private void updateControlStateOfWidget(Context context, RemoteViews remoteView, WidgetState state, int appWidgetId) {

		remoteView.setOnClickPendingIntent(R.id.widget_mail_message_count, makeControlPendingIntent(context, appWidgetId));

		Intent configIntent = new Intent(context, TEMPMailWidgetConfig.class); //This may need to be changed if things error out
		configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

		// gotta make this unique for this appwidgetid
		configIntent.setData(Uri.withAppendedPath(Uri.parse(TEMPMailWidgetProvider.URI_SCHEME + "://widget/id/"), String.valueOf(appWidgetId)));

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, configIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		try {
			AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, remoteView);
		} catch (Exception e) {
			Log.e(LOG, "Failure updating widget");
		}
	}

	//Method to return a new pending intent. @Params: context, command of what to do, widget identifier for this one
	private PendingIntent makeControlPendingIntent(Context context, int appWidgetId) {
		Intent active = new Intent(context, SelectMessageActivity2.class); //May need to replace context with this.getApplicationContext()
		active.setAction(ACTION_WIDGET_CONTROL); //This may error out
		active.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		active.putExtra("chosen_account_string", user_name);
		active.putExtra("tag_chosen", tag_chosen);

		Uri data = Uri.withAppendedPath(Uri.parse(TEMPMailWidgetProvider.URI_SCHEME + "://widget/id/#" + "SelectMailActivity2"), String.valueOf(appWidgetId));
		active.setData(data);
		return (PendingIntent.getActivity(context, 0, active, 0)); //May need to replace last param (0) with: PendingIntent.FLAG_ONE_SHOT
	}

	//Look closer into what this is doing, may use it in replacement of the async task
	private class UpdateThread extends Thread {

		// Update images from feed each hour
		//private static final int URL_UPDATE_DELAY = 2 * 60 * 60 * 1000;
		private boolean stopRequested = false;
		private int appWidgetId1;
		Context context;

		//List<serverFinishedListener> listeners = new ArrayList<serverFinishedListener>();
		private String output = "";

		private String tag_chosen_async;
		private AppWidgetManager appWidgetManager;

		private String thread_message_count_string;
		private int thread_message_count_int;

		private String user_name, color_background_choice, font_color_choice;

		//Constructor for the custom thread.@Params, widget identifier
		public UpdateThread(int appWidgetId, Context context) {
			super("PingServerDataThread");
			this.appWidgetId1 = appWidgetId;
			this.context = context;
		}

		//
		public void run() {

			try {


				//Begin pulling data: This block populates user_accounts with account information
				ReadInAccounts();
				if (accounts.size() == 1) {
					selectedAccount = accounts.get(0);
					Log.d("Widget Update Service", "only 1 account setting as default" + selectedAccount.displayString);
					user_accounts.add(selectedAccount.displayString);
				} else {
					for (AccountInfo i : accounts) {
						Log.d("Widget Update Service", "Multiple accounts found, Setting Default account to selected account: " + i.displayString); //
						user_accounts.add(i.displayString);
						if (i.defaultAccount)
							selectedAccount = i;
					}
				}

				//Create a database object and set the values here
				TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(context);

				//For the row ID
				String widget_id = Integer.toString(appWidgetId1);

				//Extract the return data from the List and use it//

				//List to hold returned data
				List<String> db_data = new ArrayList<>();

				//Set the returned data = to the row's returned data
				db_data = db.getRow(widget_id);


				user_name = db_data.get(2);
				Log.d("MailWidgetUpdateService Database username = ", user_name);
				tag_chosen = db_data.get(19);
				Log.d("MailWidgetUpdateService Database tag chosen = ", tag_chosen);
				color_background_choice = db_data.get(20);
				Log.d("MailWidgetUpdateService Database color background choice = ", color_background_choice);
				font_color_choice = db_data.get(21);
				Log.d("MailWidgetUpdateService Database font color choice = ", font_color_choice);
				message_count_int = db_data.get(3);
				Log.d("MailWidgetUpdateService Database message count int = ", message_count_int);

				//Determine which tag chosen parameter was passed and return the mail respective to that call
				if (tag_chosen.equalsIgnoreCase("All")) {
					message_count_string = db_data.get(4);
				} else if (tag_chosen.equalsIgnoreCase("Correspondence")) {
					message_count_string = db_data.get(4);
				} else if (tag_chosen.equalsIgnoreCase("Tutorial")) {
					message_count_string = db_data.get(5);
				} else if (tag_chosen.equalsIgnoreCase("Medal")) {
					message_count_string = db_data.get(6);
				} else if (tag_chosen.equalsIgnoreCase("Intelligence")) {
					message_count_string = db_data.get(7);
				} else if (tag_chosen.equalsIgnoreCase("Alert")) {
					message_count_string = db_data.get(8);
				} else if (tag_chosen.equalsIgnoreCase("Attack")) {
					message_count_string = db_data.get(9);
				} else if (tag_chosen.equalsIgnoreCase("Colonization")) {
					message_count_string = db_data.get(10);
				} else if (tag_chosen.equalsIgnoreCase("Complaint")) {
					message_count_string = db_data.get(11);
				} else if (tag_chosen.equalsIgnoreCase("Excavator")) {
					message_count_string = db_data.get(12);
				} else if (tag_chosen.equalsIgnoreCase("Mission")) {
					message_count_string = db_data.get(13);
				} else if (tag_chosen.equalsIgnoreCase("Parliament")) {
					message_count_string = db_data.get(14);
				} else if (tag_chosen.equalsIgnoreCase("Probe")) {
					message_count_string = db_data.get(15);
				} else if (tag_chosen.equalsIgnoreCase("Spies")) {
					message_count_string = db_data.get(16);
				} else if (tag_chosen.equalsIgnoreCase("Trade")) {
					message_count_string = db_data.get(17);
				} else if (tag_chosen.equalsIgnoreCase("Fissure")) {
					message_count_string = db_data.get(18);
				}
				Log.d("MailWidgetUpdateService Database message count string = ", message_count_string);

				Log.d("Database", "Has been queried");

				db.close();
				//contentValues.put(helper.COLUMN_SESSION_ID,  newData.get(22)); //May need...

				//Set the account man to the username received from the database
				AccountMan.GetAccount(user_name);

				//Depending on tag chosen, different URI request sent in JSON to the Lacuna Servers
				if (tag_chosen.equalsIgnoreCase("All")) {
					String request = Inbox.ViewInbox(selectedAccount.sessionID);
					output = ServerRequest(selectedAccount.server, Inbox.url, request);
					convertData(output);
				} else {
					String request = Inbox.ViewInbox(selectedAccount.sessionID, tag_chosen);
					output = ServerRequest(selectedAccount.server, Inbox.url, request);
					convertData(output);
				}

			} catch (Exception e){
				Log.d("TEMPMailWidgetUpdateService", "Try failed");
			}

			long millis = 0;
			long millisOffset = 0;
			WidgetState state = WidgetState.getState(getApplicationContext(), appWidgetId1);

			try {
				while (!stopRequested) {
					synchronized (this) {
						// update the state
						state = WidgetState.getState(getApplicationContext(), appWidgetId1);
					}
				}
			}catch (Exception e){
				Log.d("TEMPMailWidgetUpdateService", "try failed");
			}
		}

		private void convertData(String reply) {

			if(!reply.equals("error")) {
				Log.d("Deserializing Response", "Creating Response Object");
				messagesReceived = true;
				//Getting new messages, clearing list first.
				Response r = new Gson().fromJson(reply, Response.class);
				messages_array.clear();
				messages_array = r.result.messages;

				thread_message_count_int = r.result.status.empire.has_new_messages;
				thread_message_count_string = r.result.message_count;

				//Put the respective data into the database
				putDataIntoDatabase(appWidgetId1);
			}
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
				Log.d("Thread Request: ", "Reply string " + output);
			} catch (java.net.MalformedURLException e) {
				Log.d("Server Error", "Malformed URL Exception");
				output = "error";
			} catch (java.io.IOException e) {
				Log.d("Server Error", "Malformed IO Exception");
				output = "error";
			}
			return output;
		}

		//Puts the data into the database
		private void putDataIntoDatabase(int apwid1){

			//Create a database object and set the values here
			TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(context);

			//For the row ID
			String widget_id = Integer.toString(apwid1);

			try {

				//List of Strings to hold the passed data
				List<String> passed_data = new ArrayList<>();

				passed_data.add(widget_id); //0
				passed_data.add(sync_frequency); //1
				passed_data.add(user_name); //2
				passed_data.add(message_count_int); //3
			/*
			I am writing all of these in as the specific tag chosen because as the App widget ID is unique, it
			will not matter as it is not checking the other columns. Once I add an update to allow for editing
			widgets however, this will need to be changed. Furthermore, I will need to change it to raw SQL
			update code to allow for specific passing of the tag chosen and using that to write.
			 */
				passed_data.add(message_count_string); //4
				passed_data.add(message_count_string); //5
				passed_data.add(message_count_string); //6
				passed_data.add(message_count_string); //7
				passed_data.add(message_count_string); //8
				passed_data.add(message_count_string); //9
				passed_data.add(message_count_string); //10
				passed_data.add(message_count_string); //11
				passed_data.add(message_count_string); //12
				passed_data.add(message_count_string); //13
				passed_data.add(message_count_string); //14
				passed_data.add(message_count_string); //15
				passed_data.add(message_count_string); //16
				passed_data.add(message_count_string); //17
				passed_data.add(message_count_string); //18
				passed_data.add(tag_chosen); //19
				passed_data.add(color_background_choice); //20
				passed_data.add(font_color_choice); //21
				passed_data.add(selectedAccount.sessionID); //22
				passed_data.add(selectedAccount.homePlanetID); //23
				//AS OF RIGHT NOW, this line above is passing in the homePlanetID instead of the empire ID.
				//This will be changed later on
				db.insertData(passed_data);

			} catch (Exception e){
				e.printStackTrace();
				Log.d("WidgetUpdateService", "Error in insertData() method");
			}

			try {
				db.close();
			} catch (Exception e){
				Log.d("WidgetUpdateService", "ERROR closing db");
			}
		}

		/*
		 Call to request this thread cleanly finish and stop
		 Waits until the thread has stopped -- or an Exception is triggered
		 */
		public void requestStop() {
			synchronized (this) {
				stopRequested = true;
				notify();
			}
			try {
				join();
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}
}




