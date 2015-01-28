package com.JazzDevStudio.LacunaExpress.MISCClasses;

public class TESTCode {

	//WidgetState
	/*
	package com.JazzDevStudio.LacunaExpress.Widget;

import android.content.Context;
import android.util.Log;

import com.JazzDevStudio.LacunaExpress.Database.TEMPDatabaseAdapter;

import java.util.ArrayList;
import java.util.List;

//State management and utilities
public class WidgetState {

	//ADD aLL OF THE PuBLIC FIELDS HERE. IE mail_count, color background, etc
	//May need to change methods back to private if duplicate issues

	private static final String ACTION_WIDGET_CONTROL = "com.JazzDevStudio.LacunaExpress.Widget.WIDGET_CONTROL";
	String user_name, color_background_choice, font_color_choice, message_count_string,
			message_count_int, sessionID, homePlanetID;
	String sync_frequency = "15"; //Default
	String tag_chosen = "All";

	//Retrieves a new WidgetState object from the database
	//@Params: context, app widget identifier
	public static WidgetState getState(Context context, int appWidgetId) {
		WidgetState state = new WidgetState();

		//Pull data from the database here to add it to the 'state'
		//Create a database object and set the values here
		TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(context);

		//For the row ID
		String widget_id = Integer.toString(appWidgetId);

		try {
			//Extract the return data from the List and use it//

			//List to hold returned data
			List<String> db_data = new ArrayList<>();

			//Set the returned data = to the row's returned data
			db_data = db.getRow(widget_id);

			state.user_name = db_data.get(2);
			Log.d("MailWidgetUpdateService Database username = ", state.user_name);
			state.tag_chosen = db_data.get(19);
			Log.d("MailWidgetUpdateService Database tag chosen = ", state.tag_chosen);
			state.color_background_choice = db_data.get(20);
			Log.d("MailWidgetUpdateService Database color background choice = ", state.color_background_choice);
			state.font_color_choice = db_data.get(21);
			Log.d("MailWidgetUpdateService Database font color choice = ", state.font_color_choice);
			state.message_count_int = db_data.get(3);
			Log.d("MailWidgetUpdateService Database message count int = ", state.message_count_int);

			//Determine which tag chosen parameter was passed and return the mail respective to that call
			if (state.tag_chosen.equalsIgnoreCase("All")) {
				state.message_count_string = db_data.get(4);
			} else if (state.tag_chosen.equalsIgnoreCase("Correspondence")) {
				state.message_count_string = db_data.get(4);
			} else if (state.tag_chosen.equalsIgnoreCase("Tutorial")) {
				state.message_count_string = db_data.get(5);
			} else if (state.tag_chosen.equalsIgnoreCase("Medal")) {
				state.message_count_string = db_data.get(6);
			} else if (state.tag_chosen.equalsIgnoreCase("Intelligence")) {
				state.message_count_string = db_data.get(7);
			} else if (state.tag_chosen.equalsIgnoreCase("Alert")) {
				state.message_count_string = db_data.get(8);
			} else if (state.tag_chosen.equalsIgnoreCase("Attack")) {
				state.message_count_string = db_data.get(9);
			} else if (state.tag_chosen.equalsIgnoreCase("Colonization")) {
				state.message_count_string = db_data.get(10);
			} else if (state.tag_chosen.equalsIgnoreCase("Complaint")) {
				state.message_count_string = db_data.get(11);
			} else if (state.tag_chosen.equalsIgnoreCase("Excavator")) {
				state.message_count_string = db_data.get(12);
			} else if (state.tag_chosen.equalsIgnoreCase("Mission")) {
				state.message_count_string = db_data.get(13);
			} else if (state.tag_chosen.equalsIgnoreCase("Parliament")) {
				state.message_count_string = db_data.get(14);
			} else if (state.tag_chosen.equalsIgnoreCase("Probe")) {
				state.message_count_string = db_data.get(15);
			} else if (state.tag_chosen.equalsIgnoreCase("Spies")) {
				state.message_count_string = db_data.get(16);
			} else if (state.tag_chosen.equalsIgnoreCase("Trade")) {
				state.message_count_string = db_data.get(17);
			} else if (state.tag_chosen.equalsIgnoreCase("Fissure")) {
				state.message_count_string = db_data.get(18);
			}
			Log.d("MailWidgetUpdateService Database message count string = ", state.message_count_string);

			Log.d("Database", "Has been queried");

			state.sessionID = db_data.get(22);
			state.homePlanetID = db_data.get(23);

		} catch (IndexOutOfBoundsException e) {
		}

		//Close the database
		try {
			db.close();
		} catch (Exception e){
			Log.d("WidgetState", "ERROR closing db");
		}

		return state;
	}

	//Store the updated state via writing to the database. @Params: context, app widget identifier, the widgetstate that is being stored
	public static void storeState(Context context, int appWidgetId, WidgetState state) {
		//Create a database object and set the values here
		TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(context);

		//For the row ID
		String widget_id = Integer.toString(appWidgetId);

		try {

			//List of Strings to hold the passed data
			List<String> passed_data = new ArrayList<>();

			passed_data.add(widget_id); //0
			passed_data.add(state.sync_frequency); //1
			passed_data.add(state.user_name); //2
			passed_data.add(state.message_count_int); //3
			/*
			I am writing all of these in as the specific tag chosen because as the App widget ID is unique, it
			will not matter as it is not checking the other columns. Once I add an update to allow for editing
			widgets however, this will need to be changed. Furthermore, I will need to change it to raw SQL
			update code to allow for specific passing of the tag chosen and using that to write.

	passed_data.add(state.message_count_string); //4
	passed_data.add(state.message_count_string); //5
	passed_data.add(state.message_count_string); //6
	passed_data.add(state.message_count_string); //7
	passed_data.add(state.message_count_string); //8
	passed_data.add(state.message_count_string); //9
	passed_data.add(state.message_count_string); //10
	passed_data.add(state.message_count_string); //11
	passed_data.add(state.message_count_string); //12
	passed_data.add(state.message_count_string); //13
	passed_data.add(state.message_count_string); //14
	passed_data.add(state.message_count_string); //15
	passed_data.add(state.message_count_string); //16
	passed_data.add(state.message_count_string); //17
	passed_data.add(state.message_count_string); //18
	passed_data.add(state.tag_chosen); //19
	passed_data.add(state.color_background_choice); //20
	passed_data.add(state.font_color_choice); //21
	passed_data.add(state.sessionID); //22
	passed_data.add(state.homePlanetID); //23
	//AS OF RIGHT NOW, this line above is passing in the homePlanetID instead of the empire ID.
	//This will be changed later on
	db.insertData(passed_data);

} catch (Exception e){
		e.printStackTrace();
		Log.d("WidgetState", "Error in insertData() method");
		}

		try {
		db.close();
		} catch (Exception e){
		Log.d("WidgetState", "ERROR closing db");
		}
		}

//Deletes a set of state information from the database. @Params: context, app widget identifier
public static void deleteStateForId(Context context, int appWidgetId) {
		//Create a database object and set the values here
		TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(context);

		try {
		db.deleteRow(Integer.toString(appWidgetId));
		} catch (Exception e){
		Log.d("Widget State", "Issue with deleting row in database");
		}

		try {
		db.close();
		} catch (Exception e){
		Log.d("WidgetState", "ERROR closing db");
		}
		}
		}


		*/


	//TEMPMailWidgetConfig
	/*
	package com.JazzDevStudio.LacunaExpress.Widget;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.AddAccount;
import com.JazzDevStudio.LacunaExpress.Database.TEMPDatabaseAdapter;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.MISCClasses.L;
import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TEMPMailWidgetConfig extends Activity implements serverFinishedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

	String color_background_choice, font_color_choice;

	String chosen_account_string;

	//Total number of messages
	String message_count_string;
	int message_count_int;

	//To be written into the database... eventually
	String session_id;
	String empire_id;

	//Do they want notifications?
	boolean notifications_option;

	Switch widget_mail_config_notification_switch;
	Button create;
	Spinner widget_mail_config_spinner_account, widget_mail_config_spinner_tag,
			widget_mail_config_spinner_color, widget_mail_config_spinner_font;

	RadioGroup widget_mail_config_radiogroup;

	AppWidgetManager awm;
	Context c;
	int awID = AppWidgetManager.INVALID_APPWIDGET_ID;
	int sync_frequency;

	//For checking internet connection
	private boolean do_we_have_network_connection;

	//Shared Preferences
	SharedPreferences prefs;

	//Account info
	AccountInfo selectedAccount;
	//For storing all account files
	ArrayList<AccountInfo> accounts;
	//ArrayList of display strings for the spinner
	ArrayList<String> user_accounts = new ArrayList<String>();

	//For color choices in string format
	ArrayList<String> color_names_strings = new ArrayList<String>();

	//Utilizing the color.xml file
	ArrayList<String> color_names = new ArrayList<String>();

	//Messages Info
	ArrayList<Response.Messages> messages_array = new ArrayList<Response.Messages>();
	Boolean messagesReceived = false;
	private String tag_chosen = "All";
	static final String[] messageTags = {"All", "Correspondence", "Tutorial", "Medal", "Intelligence", "Alert", "Attack", "Colonization", "Complaint", "Excavator", "Mission", "Parliament", "Probe", "Spies", "Trade", "Fissure"};

	private static final String LOG = "com.JazzDevStudio.LacunaExpress.Widget.TempWidgetConfig";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_mail_config);
		Initialize();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setTheBackground();

		if (do_we_have_network_connection){
			//Working network connection!
			//Go ahead as normal
		} else {
			//No network connection!
			//This is the dialog listener
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
						//If they hit ok, the app will close until they have network connection
						case DialogInterface.BUTTON_NEGATIVE:
							try {
								dialog.dismiss();
								finish();
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
					}
				}
			};
			String confirm = "You currently do not have a network connection. Please connect to the internet before creating the widget";
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(confirm).setNegativeButton("Close", dialogClickListener).show(); //.setPositiveButton("Yes", dialogClickListener)
		}

		//Internet has now been confirmed, move forward//


		//Bug <-- DELETE THIS DIALOG INTERFACE ONCE IT IS ALL RUNNING
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {

					case DialogInterface.BUTTON_NEGATIVE:
						try {
							dialog.dismiss();
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
				}
			}
		};
		String heads_up = "There is currently a bug that will allow widgets to work ONLY if you have one account on the device. " +
				"If you have more than one account, please note this will not work. " +
				"I will have a fix out as soon as I can. Thank you for your patience";
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(heads_up).setNegativeButton("Ok", dialogClickListener).show();

		//Check if any accounts exist
		boolean does_file_exist = false;
		try {
			does_file_exist = AccountMan.CheckForFile();
			if (!does_file_exist){
				//If no account exists, open the activity to add an account
				L.makeToast(this, "No Accounts detected, Let's add an account");
				Intent openActivity = new Intent(this, AddAccount.class);
				startActivity(openActivity);
			}
		} catch (Exception e){
			e.printStackTrace();
			//In case there is a corrupted file
			L.makeToast(this, "No Accounts detected, Let's add an account");
			Intent openActivity = new Intent(this, AddAccount.class);
			startActivity(openActivity);
		}

		//Accounts have been confirmed, move forward//

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

		//Configure all the spinners//

		//Arraylist has been filled with accounts, add them to the accounts spinner
		ArrayAdapter adapter_accounts = new ArrayAdapter(this, android.R.layout.simple_spinner_item, user_accounts);
		adapter_accounts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		widget_mail_config_spinner_account.setAdapter(adapter_accounts);

		//Message Tags spinner
		ArrayAdapter adapter_message_tag = new ArrayAdapter(this, android.R.layout.simple_spinner_item, messageTags);
		adapter_message_tag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		widget_mail_config_spinner_tag.setAdapter(adapter_message_tag);

		//String array of all the color choices
		Field[] fields = R.color.class.getFields();
		String color_name_temp;

		try {
			for (int i = 0; i < fields.length; i++) {
				color_name_temp = getResources().getString(fields[i].getInt(null));
				color_names.add(color_name_temp);
			}
		} catch (Exception e){
			e.printStackTrace();
			Log.d("MailWidgetConfig", "Color Choices errored out. Check Field[] fields");
		}

		//String array of all the color choices
		String[] color_names_temp = getResources().getStringArray(R.array.color_choices_names);
		try {
			for (int i = 0; i < color_names_temp.length; i++) {
				color_name_temp = color_names_temp[i];
				color_names_strings.add(color_name_temp);
			}
		} catch (Exception e){
			e.printStackTrace();
			Log.d("MailWidgetConfig", "Color Choices errored out. Check Field[] fields");
		}

		//Set an adapter to allow for different colors to be presented
		widget_mail_config_spinner_color.setAdapter(new MyAdapter(this, R.layout.custom_spinner, color_names_strings));

		//Set an adapter to allow for different colors to be presented
		widget_mail_config_spinner_font.setAdapter(new MyAdapter(this, R.layout.custom_spinner, color_names_strings));

		//Set the default values in each spinner
		widget_mail_config_spinner_account.setSelection(0);
		widget_mail_config_spinner_tag.setSelection(0);
		widget_mail_config_spinner_color.setSelection(0); //White Background
		widget_mail_config_spinner_font.setSelection(1); //Black Font

		//Spinner have been populated//

		//An intent is opening this class, therefore, must make one
		Intent i = getIntent();
		//Create a bundle since info is being passed around (Which app launched this activity)
		Bundle extras = i.getExtras();
		//As long as the extras had something, setup the app widget id
		if (extras != null){
			//Get an ID and pass it in. IE, a way to checking which widget activated this class
			awID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

			Intent cancelResultValue = new Intent();
			cancelResultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, awID);
			setResult(RESULT_CANCELED, cancelResultValue);

			Log.d("Widget ID in Widget Config", Integer.toString(awID));
			//This returns 1 App widget ID
		} else {
			//In case something gets a-broken!
			finish();
		}
		awm = AppWidgetManager.getInstance(c);


	}

	private void Initialize() {
		create = (Button) findViewById(R.id.widget_mail_config_create);
		create.setOnClickListener(this);

		c = TEMPMailWidgetConfig.this;

		//Set it to false by default
		notifications_option = false;

		//Strings
		color_background_choice = "white";
		font_color_choice = "black";

		//Spinners
		widget_mail_config_spinner_account = (Spinner) findViewById(R.id.widget_mail_config_spinner_account);
		widget_mail_config_spinner_tag = (Spinner) findViewById(R.id.widget_mail_config_spinner_tag);
		widget_mail_config_spinner_color = (Spinner) findViewById(R.id.widget_mail_config_spinner_color);
		widget_mail_config_spinner_font = (Spinner) findViewById(R.id.widget_mail_config_spinner_font);

		//Set the spinners to the onItemSelectedListener
		widget_mail_config_spinner_account.setOnItemSelectedListener(this);
		widget_mail_config_spinner_tag.setOnItemSelectedListener(this);
		widget_mail_config_spinner_color.setOnItemSelectedListener(this);
		widget_mail_config_spinner_font.setOnItemSelectedListener(this);

		//Radio Group
		widget_mail_config_radiogroup = (RadioGroup) findViewById(R.id.widget_mail_config_radiogroup);
		widget_mail_config_radiogroup.setOnCheckedChangeListener(this);

		//Default to no network connection
		do_we_have_network_connection = false;
		//Check if there is internet connection
		do_we_have_network_connection = haveNetworkConnection();

		//Default frequency check if the user chooses nothing
		sync_frequency = 15;

		//Switch to decide if the users want notifications in their notification bar
		widget_mail_config_notification_switch = (Switch) findViewById(R.id.widget_mail_config_notification_switch);
		widget_mail_config_notification_switch.setChecked(false);
		widget_mail_config_notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					notifications_option = true;
				}else{
					notifications_option = false;
				}
			}
		});
	}



	//Create the widget here
	public void onClick(View v) {
		//Write all the data into the database
		putDataInDatabase();

		if (awID != AppWidgetManager.INVALID_APPWIDGET_ID) {

			// tell the app widget manager that we're now configured
			Intent resultValue = new Intent();
			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, awID);
			setResult(RESULT_OK, resultValue);

			Intent widgetUpdate = new Intent();
			widgetUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			widgetUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[] { awID });

			// make this pending intent unique
			widgetUpdate.setData(Uri.withAppendedPath(Uri.parse(TEMPMailWidgetProvider.URI_SCHEME + "://widget/id/"), String.valueOf(awID)));
			PendingIntent newPending = PendingIntent.getBroadcast(getApplicationContext(), 0, widgetUpdate, PendingIntent.FLAG_UPDATE_CURRENT);

			// schedule the new widget for updating
			AlarmManager alarms = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			alarms.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), sync_frequency * 60 * 1000, newPending);
		}

		finish();

	}

	//Puts the data into the database
	private void putDataInDatabase() {
		//Create a database object and set the values here
		TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(this);

		//For the row ID
		String widget_id = Integer.toString(awID);

		try {

			//List of Strings to hold the passed data
			List<String> passed_data = new ArrayList<>();

			passed_data.add(widget_id); //0
			passed_data.add(Integer.toString(sync_frequency)); //1
			passed_data.add(chosen_account_string); //2
			passed_data.add(Integer.toString(message_count_int)); //3

			I am writing all of these in as the specific tag chosen because as the App widget ID is unique, it
			will not matter as it is not checking the other columns. Once I add an update to allow for editing
			widgets however, this will need to be changed. Furthermore, I will need to change it to raw SQL
			update code to allow for specific passing of the tag chosen and using that to write.

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
		Log.d("WidgetConfig", "Error in insertData() method");
		}

		try {
		db.close();
		} catch (Exception e){
		Log.d("Widget Config", "ERROR closing db");
		}
		}

//When an item is selected with the spinner
public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		//First spinner, widget_mail_config_spinner_account
		if (parent == widget_mail_config_spinner_account){
		//Get the position within the spinner
		int position0 = widget_mail_config_spinner_account.getSelectedItemPosition();
		chosen_account_string = user_accounts.get(position0);
		Log.d("SelectMessage.onItemSelected assigning selected account", "word in spinner "+ chosen_account_string);

		if (tag_chosen.equalsIgnoreCase("All")){
		//Check the account via the spinner chosen
		selectedAccount = AccountMan.GetAccount(chosen_account_string);
		Log.d("SelectMessage.onItemSelected", "Tag All Calling View Inbox");
		String request = Inbox.ViewInbox(selectedAccount.sessionID);
		Log.d("Select Message Activity, SelectedAccount", selectedAccount.userName);
		Log.d("SelectMessage.OnSelectedItem Request to server", request);
		ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
		AsyncServer s = new AsyncServer();
		s.addListener(this);
		s.execute(sRequest);
		} else {
		//Check the account via the spinner chosen
		selectedAccount = AccountMan.GetAccount(chosen_account_string);
		Log.d("SelectMessage.onItemSelected", "Tag Word in spinner Calling View Inbox");
		String request = Inbox.ViewInbox(selectedAccount.sessionID, tag_chosen);
		Log.d("SelectMessage.OnSelectedItem Request to server", request);
		Log.d("Select Message Activity, SelectedAccount", selectedAccount.userName);
		ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
		AsyncServer s = new AsyncServer();
		s.addListener(this);
		s.execute(sRequest);
		}
		}

		//Second spinner, message_tag
		if (parent == widget_mail_config_spinner_tag){
		int position0 = widget_mail_config_spinner_tag.getSelectedItemPosition();
		String word_in_spinner = messageTags[position0];
		tag_chosen = word_in_spinner; //Sets the tag for mail to the one chosen via the spinner
		Log.d("SelectMessage.onItemSelected assigning Tag", "word in spinner "+ word_in_spinner);

		if (tag_chosen.equalsIgnoreCase("All")){
		Log.d("SelectMessage.onItemSelected", "Second Spinner Tag All Calling View Inbox");
		String request = Inbox.ViewInbox(selectedAccount.sessionID);
		Log.d("SelectMessage.OnSelectedItem Request to server", request);
		Log.d("Select Message Activity, SelectedAccount", chosen_account_string);
		ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
		AsyncServer s = new AsyncServer();
		s.addListener(this);
		s.execute(sRequest);
		} else {
		Log.d("SelectMessage.onItemSelected", "Second Spinner word in spinner All Calling View Inbox");
		String request = Inbox.ViewInbox(selectedAccount.sessionID, tag_chosen);
		Log.d("SelectMessage.OnSelectedItem Request to server", request);
		Log.d("Select Message Activity, SelectedAccount", chosen_account_string);
		ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
		AsyncServer s = new AsyncServer();
		s.addListener(this);
		s.execute(sRequest);
		}
		}

		//Third spinner, Background Color of widget
		if (parent == widget_mail_config_spinner_color){
		int position1 = widget_mail_config_spinner_color.getSelectedItemPosition();
		String word_in_spinner = color_names_strings.get(position1);
		color_background_choice = word_in_spinner;
		}

		//Fourth spinner, font color of widget
		if (parent == widget_mail_config_spinner_font){
		int position1 = widget_mail_config_spinner_font.getSelectedItemPosition();
		String word_in_spinner = color_names_strings.get(position1);
		font_color_choice = word_in_spinner;
		}
		}

//This handles the radio buttons
public void onCheckedChanged(RadioGroup rg, int checkedId) {
		//Depending on which one is selected. Default is 15 minutes
		switch(checkedId){

		//5 Minutes Refresh
		case R.id.widget_mail_config_button5:
		sync_frequency = 5;
		break;

		//10 Minutes Refresh
		case R.id.widget_mail_config_button10:
		sync_frequency = 10;
		break;

		//15 Minutes Refresh
		case R.id.widget_mail_config_button15:
		sync_frequency = 15;
		break;

		//30 Minutes Refresh
		case R.id.widget_mail_config_button30:
		sync_frequency = 30;
		break;

		//60 Minutes Refresh
		case R.id.widget_mail_config_button60:
		sync_frequency = 60;
		break;
		}
		}

//Unused atm
public void onNothingSelected(AdapterView<?> parent) {
		}

//Checks if there is network connection. Returns boolean
private boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
		if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		if (ni.isConnected())
		haveConnectedWifi = true;
		if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		if (ni.isConnected())
		haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
		}

//Reads in the accounts from the existing objects
private void ReadInAccounts() {
		Log.d("SelectAccountActivity.ReadInAccounts", "checking for file" + AccountMan.CheckForFile());
		accounts = AccountMan.GetAccounts();
		Log.d("SelectAccountActivity.ReadInAccounts", String.valueOf(accounts.size()));
		}

//Set the background image as per shared preferences
private void setTheBackground() {
		String user_choice = prefs.getString("pref_background_choice","blue_glass");
		Log.d("User Background Choice", user_choice);
		LinearLayout layout = (LinearLayout) findViewById(R.id.widget_mail_config_layout);
		if (user_choice.equalsIgnoreCase("blue_glass")){
		layout.setBackground(getResources().getDrawable(R.drawable.blue_glass));
		} else if (user_choice.equalsIgnoreCase("blue_oil_painting")){
		layout.setBackground(getResources().getDrawable(R.drawable.blue_oil_painting));
		} else if (user_choice.equalsIgnoreCase("stained_glass_blue")){
		layout.setBackground(getResources().getDrawable(R.drawable.stained_glass_blue));
		} else if (user_choice.equalsIgnoreCase("light_blue_boxes")){
		layout.setBackground(getResources().getDrawable(R.drawable.light_blue_boxes));
		} else if (user_choice.equalsIgnoreCase("light_silver_background")){
		layout.setBackground(getResources().getDrawable(R.drawable.light_silver_background));
		} else if (user_choice.equalsIgnoreCase("simple_grey")){
		layout.setBackground(getResources().getDrawable(R.drawable.simple_grey));
		} else if (user_choice.equalsIgnoreCase("simple_apricot")){
		layout.setBackground(getResources().getDrawable(R.drawable.simple_apricot));
		} else if (user_choice.equalsIgnoreCase("simple_teal")){
		layout.setBackground(getResources().getDrawable(R.drawable.simple_teal));
		} else if (user_choice.equalsIgnoreCase("xmas")){
		layout.setBackground(getResources().getDrawable(R.drawable.xmas));
		} else if (user_choice.equalsIgnoreCase("lacuna_logo")){
		layout.setBackground(getResources().getDrawable(R.drawable.lacuna_logo));
		} else {
		}
		}

//Spinner Adapter
public class MyAdapter extends ArrayAdapter<String> {
	//(Really useful code, link is here: http://mrbool.com/how-to-customize-spinner-in-android/28286)
	public MyAdapter(Context ctx, int txtViewResourceId, ArrayList<String> aList) {
		super(ctx, txtViewResourceId, aList);
	}

	@Override
	public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
		return getCustomView(position, cnvtView, prnt);
	}

	@Override
	public View getView(int pos, View cnvtView, ViewGroup prnt) {
		return getCustomView(pos, cnvtView, prnt);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = getLayoutInflater();
		View mySpinner = inflater.inflate(R.layout.custom_spinner, parent, false);

		TextView main_text = (TextView) mySpinner.findViewById(R.id.spinner_text_view);
		String word_in_spinner_temp = color_names_strings.get(position);
		main_text.setText(word_in_spinner_temp);
		//Set the font color here to match the word
		main_text.setTextColor(Color.parseColor(word_in_spinner_temp));
		return mySpinner;
	}
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

			message_count_int = r.result.status.empire.has_new_messages;

			message_count_string = r.result.message_count;

		} else {
			Log.d("Error with Reply", "Error in onResponseReceived()");
		}
	}

	//Activate the service

	//Deactivate the service
}

	 */
	//TEMPMailWidgetProvider
	/*
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
  This will configure the widget that is placed on the user's homescreen.

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
	 */


	//TEMPMailWidgetUpdateService
	/*
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

			I am writing all of these in as the specific tag chosen because as the App widget ID is unique, it
			will not matter as it is not checking the other columns. Once I add an update to allow for editing
			widgets however, this will need to be changed. Furthermore, I will need to change it to raw SQL
			update code to allow for specific passing of the tag chosen and using that to write.

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


		 //Call to request this thread cleanly finish and stop
		 //Waits until the thread has stopped -- or an Exception is triggered

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

		*/


	//Update remote views with ping to database
	/*
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
	 */

	//Testdb
	/*

public class Testdb extends AndroidTestCase{

	public void testCreatedb() throws Throwable{
		mContext.deleteDatabase(DatabaseHelper.DATABASE_NAME);
		SQLiteDatabase db = new DatabaseHelper(this.mContext).getWritableDatabase();
		assertEquals(true, db.isOpen());
		db.close();
	}

	ContentValues getWidgetContentValues(){

		ContentValues values = new ContentValues();
		values.put(DatabaseContract.WidgetEntry.COLUMN_COLOR_BACKGROUND_CHOICE, "blue");
		values.put(DatabaseContract.WidgetEntry.COLUMN_WIDGET_ID, "123");
		values.put(DatabaseContract.WidgetEntry.COLUMN_COLOR_FONT_CHOICE, "blue");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ALERT, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ALL, "1000");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ATTACK, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_COLONIZATION, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_COMPLAINT, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_CORRESPONDENCE, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_EXCAVATOR, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_FISSURE, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_INTELLIGENCE, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_MEDAL, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_MISSION, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_PARLIAMENT, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_PROBE, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_SPIES, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_TUTORIAL, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_SYNC_FREQUENCY, "21");
		values.put(DatabaseContract.WidgetEntry.COLUMN_TAG_CHOSEN, "Attack");
		values.put(DatabaseContract.WidgetEntry.COLUMN_USERNAME, "Runescholar");
		values.put(DatabaseContract.WidgetEntry.COLUMN_SESSION_ID, "Session_ID_123_abc");

		return values;
	}

	//Validate the cursor
	static public void validateCursor(ContentValues expectedValues, Cursor valueCursor){
		//
		Set <Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

		for (Map.Entry<String, Object> entry : valueSet){
			String columnName = entry.getKey();
			int idx = valueCursor.getColumnIndex(columnName);
			assertFalse(-1 == idx);
			String expectedValue = entry.getValue().toString();
			assertEquals(expectedValue, valueCursor.getString(idx));
		}
	}

	//Reads in the database
	public void testInsertReadDb(){

		DatabaseHelper dbHelper = new DatabaseHelper(mContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		//Create map of values, where column names are keys
		ContentValues values = getWidgetContentValues();

		long locationRowId;
		locationRowId = db.insert(DatabaseContract.WidgetEntry.TABLE_NAME, null, values);

		//Verify a row came back
		assertTrue(locationRowId != -1);
		Log.d("LOGGING", "New Row ID: "+locationRowId);

		//Spe
		//Cursor to query results
		Cursor cursor = db.query(
				DatabaseContract.WidgetEntry.TABLE_NAME,
				null,
				null,
				null,
				null,
				null,
				null);

		if (cursor.moveToFirst()){
			validateCursor(values, cursor);
		} else {
			fail("Nothing returned! : (");
		}
	}
}

	 */


	//Full test suite
	/*
	/**
	public class FullTestSuite {

		public static Test suite(){
			return new TestSuiteBuilder(FullTestSuite.class).includeAllPackagesUnderHere().build();
		}

		public FullTestSuite() {
			super();
		}
	}
	 */

	//All of this code was from the SelectMessageActivity <-- Old version
	/*
package com.JazzDevStudio.LacunaExpress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;

import AccountMan.AccountInfo;
import AccountMan.AccountMan;
import JavaLEWrapper.Inbox;
import LEWrapperResponse.Response;
import LEWrapperResponse.Response.Messages;
import MISCClasses.MailFormat;
import Server.AsyncServer;
import Server.ServerRequest;
import Server.serverFinishedListener;


public class SelectMessageActivityNOTUSEDRIGHTNOW extends Activity implements serverFinishedListener, OnClickListener, OnItemClickListener {
	//for storing the current selected account
	AccountInfo selectedAccount;

    //Contains a list of all Mail objects
	ArrayList <Messages> messages_array = new ArrayList<Messages>();
	MailFormat mf;

    //Boolean for tracking when messages have been received
    Boolean messagesReceived = false;

    //Selected Message, this is to store the id of the selected message
    String selectedMessage = "";

	//For storing all account files
	ArrayList<AccountInfo> accounts;

	//ArrayList of display strings for the spinner
	ArrayList<String> user_accounts = new ArrayList<String>();
	ArrayList<String> message_tag_mail = new ArrayList<String>();

	private Button compose;
	private Spinner account_list, message_tag;

	ListView list_messages;
	static final String[] messageTags = {"Correspondence", "Tutorial", "Medal", "Intelligence", "Alert", "Attack", "Colonization", "Complaint", "Excavator", "Mission", "Parliament", "Probe", "Spies", "Trade", "Fissure"};

    //String sessionID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_temp);

        //Initialize Variables
        Initialize();

		//This block populates user_accounts for values to display in the select account spinner
	    ReadInAccounts();
	    if(accounts.size() == 1){
		    selectedAccount = accounts.get(0);
		    //Log.d("Select Account", selectedAccount);
		    user_accounts.add(selectedAccount.displayString);
	    } else{
		    for(AccountInfo i: accounts){
			    Log.d("Select Account", i.displayString); //
			    user_accounts.add(i.displayString);
		    }
	    }

        //Configure the spinners:
		ArrayAdapter adapter_accounts = new ArrayAdapter(this, android.R.layout.simple_spinner_item, user_accounts);
		adapter_accounts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		account_list.setAdapter(adapter_accounts);

		//ArrayAdapter adapter_message_tag = new ArrayAdapter(this, android.R.layout.simple_spinner_item, message_tag_mail);
        ArrayAdapter adapter_message_tag = new ArrayAdapter(this, android.R.layout.simple_spinner_item, messageTags);
		adapter_message_tag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		message_tag.setAdapter(adapter_message_tag);

        //checking for intent Extra
        Intent i = getIntent();
        if(i.hasExtra("displayString")){
        	Log.d("SelectMessageActivity.onCreate", i.getStringExtra("displayString"));
        	String a = i.getStringExtra("displayString");
        	selectedAccount = AccountMan.GetAccount(a); //this is erroring out
        	//Inbox inbox = new Inbox();
        	String request = Inbox.ViewInbox(selectedAccount.sessionID, Inbox.MessageTags.Correspondence.toString());

        	Log.d("SelecteMessage.Oncreate Request to server", request);
            ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
            AsyncServer s = new AsyncServer();
            s.addListener(this);
            s.execute(sRequest);
        } else{
        	Log.d("AddAccount.onCreate", "Intent is type addAccount");
        }
    }

    //Initialize Variables
    private void Initialize() {
		compose = (Button) findViewById(R.id.activity_mail_temp_compose);

		account_list = (Spinner) findViewById(R.id.activity_mail_temp_account_spinner);
		message_tag = (Spinner) findViewById(R.id.activity_mail_temp_message_tag_spinner);

		list_messages = (ListView) findViewById(R.id.activity_mail_temp_list);

		compose.setOnClickListener(this);
		list_messages.setOnItemClickListener(this);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	//This handles the onClick events like the compose button
	public void onClick(View v) {
		switch (v.getId()){

		//Compose
		case R.id.activity_mail_temp_compose:
			try {
				//Starting to add intent code to launch compose
				Intent openActivity = new Intent(this, ComposeMessageActivity.class);
                		startActivity(openActivity);

			} catch (Exception e){
				e.printStackTrace();
			}
			break;
		}
	}

    private void ReadInAccounts() {
		Log.d("SelectAccountActivity.ReadInAccounts", "checking for file" + AccountMan.CheckForFile());
		accounts = AccountMan.GetAccounts();
		//Comment out this line and uncomment the line above after purging the excess accounts
		//accounts = AccountMan.PurgeDuplicateAccounts(AccountMan.GetAccounts());

		Log.d("SelectAccountActivity.ReadInAccounts", String.valueOf(accounts.size()));
	}

	@Override
	public void onResponseReceived(String reply) {
    	Log.d("SelectMessage.onResponse Recieved", reply);

        if(!reply.equals("error")) {
        	Log.d("Deserializing Response", "Creating Response Object");
            messagesReceived = true;
        	//Getting new messages, clearing list first.
            Response r = new Gson().fromJson(reply, Response.class);
	        messages_array.clear();
	        messages_array = r.result.messages;

	        //NEW TESTING
	        ArrayList<String> for_message_list = dataForListView(messages_array);
	        ArrayAdapter adapter_messages = new ArrayAdapter(this, android.R.layout.simple_list_item_1, for_message_list);
	        list_messages.setAdapter(adapter_messages);
        }
	}

	//This handles the listView clicking
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String value = list_messages.getItemAtPosition(position).toString();
		Intent openActivity = new Intent (this, ComposeMessageActivity.class);
		openActivity.putExtra("message_info", value);
		openActivity.putExtra("selected_message", selectedMessage);
		openActivity.putExtra("username", selectedAccount.userName);
		openActivity.putExtra("server", selectedAccount.server);
		openActivity.putExtra("defaultAccount", selectedAccount.defaultAccount);
		openActivity.putExtra("sessionID", selectedAccount.sessionID);
		startActivity(openActivity);
		finish();
	}

	public ArrayList<String> dataForListView(ArrayList<Response.Messages> message_array_list){
		ArrayList<String> all_returned_data = new ArrayList<String>();

		String str1, str2, str3, str4, longString;
		str1 = "";
		str2 = "";
		str3 = "";
		str4 = "";
		longString = "";

		ArrayList<String> body_preview_data = new ArrayList<String>();
		ArrayList<String> subject_data = new ArrayList<String>();
		ArrayList<String> from_data = new ArrayList<String>();
		ArrayList<String> date_data = new ArrayList<String>();

		try{
			body_preview_data = mf.fBody_Preview(message_array_list);
			subject_data = mf.fSubject(message_array_list);
			from_data = mf.fFrom(message_array_list);
			date_data = mf.fDate(message_array_list);
		} catch (NullPointerException e){
			e.printStackTrace();
		}

		//For loop to combine all data into readable format for message preview list
		for (int i = 0; i<message_array_list.size(); i++){
			str1 = date_data.get(i);
			str2 = from_data.get(i);
			str3 = subject_data.get(i);
			str4 = body_preview_data.get(i)+ "...";

			if (str1 == "")
				str1 = "(No Date)";
			if (str2 == "")
				str2 = "(No From)";
			if (str3 == "")
				str3 = "(No Subject)";
			if (str4 == "")
				str4 = "(No Preview Available)";

			//Adjust the date string to remove the +0000. First 20 characters (to eliminate time zone declaration)
			String adjust_date = str1.substring(0, Math.min(str1.length(), 20));

			longString = adjust_date + "\n" + str2 + "\n" + str3 + "\n" + str4;
			Log.d("MESSAGE EXAMPLE", "\n");
			Log.d("DATE EXAMPLE", adjust_date);
			Log.d("FROM EXAMPLE", str2);
			Log.d("SUBJECT EXAMPLE", str3);
			Log.d("PREVIEW EXAMPLE", str4);
			Log.d("LongString Example", longString);

			all_returned_data.add(longString);
		}

		return all_returned_data;
	}
}


	 */

	/*

	 */
}
