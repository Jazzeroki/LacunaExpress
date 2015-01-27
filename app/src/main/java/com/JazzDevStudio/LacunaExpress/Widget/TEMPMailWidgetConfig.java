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

	String chosen_accout_string;

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
			passed_data.add(chosen_accout_string); //2
			passed_data.add(Integer.toString(message_count_int)); //3
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
			chosen_accout_string = user_accounts.get(position0);
			Log.d("SelectMessage.onItemSelected assigning selected account", "word in spinner "+ chosen_accout_string);

			if (tag_chosen.equalsIgnoreCase("All")){
				//Check the account via the spinner chosen
				selectedAccount = AccountMan.GetAccount(chosen_accout_string);
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
				selectedAccount = AccountMan.GetAccount(chosen_accout_string);
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
				Log.d("Select Message Activity, SelectedAccount", chosen_accout_string);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();
				s.addListener(this);
				s.execute(sRequest);
			} else {
				Log.d("SelectMessage.onItemSelected", "Second Spinner word in spinner All Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID, tag_chosen);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				Log.d("Select Message Activity, SelectedAccount", chosen_accout_string);
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