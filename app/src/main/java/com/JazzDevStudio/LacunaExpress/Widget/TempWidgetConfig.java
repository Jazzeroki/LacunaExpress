package com.JazzDevStudio.LacunaExpress.Widget;

/**
 * Created by PatrickSSD2 on 1/8/2015.
 */

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
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.AddAccount;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.MISCClasses.L;
import com.JazzDevStudio.LacunaExpress.MISCClasses.SharedPrefs;
import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TempWidgetConfig extends Activity implements serverFinishedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

	//Shared Preferences Stuff
	public static final String PREFS_NAME = "LacunaExpress";
	SharedPrefs sp = new SharedPrefs();
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	private static final int RESULT_SETTINGS = 1;
	String color_background_choice, font_color_choice;

	String chosen_accout_string;

	//Total number of messages
	String message_count_string;
	int message_count_int;

	//Do they want notifications?
	boolean notifications_option;

	Switch widget_mail_config_notification_switch;
	Button create;
	Spinner widget_mail_config_spinner_account, widget_mail_config_spinner_tag,
			widget_mail_config_spinner_color, widget_mail_config_spinner_font;

	RadioGroup widget_mail_config_radiogroup;

	AppWidgetManager awm;
	Context c;
	int awID, sync_frequency;

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

		/*
		//Background Color Choice spinner
		ArrayAdapter adapter_background_color_choice = new ArrayAdapter(this, android.R.layout.simple_spinner_item, color_names_strings);
		adapter_message_tag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		widget_mail_config_spinner_color.setAdapter(adapter_background_color_choice);
		*/
		widget_mail_config_spinner_color.setAdapter(new MyAdapter(this, R.layout.custom_spinner, color_names_strings));

		/*
		//Font color choice spinner
		ArrayAdapter adapter_font_color_choice = new ArrayAdapter(this, android.R.layout.simple_spinner_item, color_names_strings);
		adapter_message_tag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		widget_mail_config_spinner_font.setAdapter(adapter_font_color_choice);
		*/
		widget_mail_config_spinner_font.setAdapter(new MyAdapter(this, R.layout.custom_spinner, color_names_strings));

		//Set the default values in each spinner
		widget_mail_config_spinner_account.setSelection(0);
		widget_mail_config_spinner_tag.setSelection(0);
		widget_mail_config_spinner_color.setSelection(0); //White Background
		widget_mail_config_spinner_font.setSelection(1); //Black Font

		//Spinner have been populated//

		//
		try {

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void Initialize() {
		create = (Button) findViewById(R.id.widget_mail_config_create);
		create.setOnClickListener(this);

		c = TempWidgetConfig.this;

		//Shared preferences
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();

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

		//An intent is opening this class, therefore, must make one
		Intent i = getIntent();
		//Create a bundle since info is being passed around (Which app launched this activity)
		Bundle extras = i.getExtras();
		//As long as the extras had something, setup the app widget id
		if (extras != null){
			//Get an ID and pass it in. IE, a way to checking which widget activated this class
			awID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			//This returns 1 App widget ID
		} else {
			//In case something gets a-broken!
			finish();
		}
		awm = AppWidgetManager.getInstance(c);
	}



	//Create the widget here
	public void onClick(View v) {



		//Setup a remoteview referring to the context (Param1) and relating to the widget (Param2)
		RemoteViews v1 = new RemoteViews(c.getPackageName(), R.layout.widget_mail_layout);

		/*
		//Set the username
		v1.setTextViewText(R.id.widget_mail_username, chosen_accout_string);
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

		Log.d("Background choice is: ", color_background_choice);
		Log.d("Font color is: ", font_color_choice);

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
		*/

		//Shared Preferences
		String str = Integer.toString(awID);
		sp.putString(editor, str + "::" + "sync_frequency", Integer.toString(sync_frequency)); //Sync Frequency in minutes
		sp.putString(editor, str + "::" + "chosen_accout_string", chosen_accout_string); //Username
		sp.putString(editor, str + "::" + "message_count_string", message_count_string); //Message count
		sp.putString(editor, str + "::" + "message_count_int", Integer.toString(message_count_int));
		sp.putString(editor, str + "::" + "tag_chosen", tag_chosen); //Tag Chosen
		sp.putString(editor, str + "::" + "color_background_choice", color_background_choice); //Background Color
		sp.putString(editor, str + "::" + "font_color_choice", font_color_choice); //Font color
		editor.commit();

		//Set and launch the Alarm Manager
		Uri.Builder build = new Uri.Builder();
		build.appendPath(""+awID);
		Uri uri = build.build();
		Intent intentUpdate = new Intent(c, TempWidgetProvider.class);
		intentUpdate.setAction(LOG);//Set an action anyway to filter it in onReceive()
		intentUpdate.setData(uri);//One Alarm per instance.
		//We will need the exact instance to identify the intent.
		TempWidgetProvider.addUri(awID, uri);
		intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, awID);
		PendingIntent pendingIntentAlarm = PendingIntent.getBroadcast(TempWidgetConfig.this,
				0,
				intentUpdate,
				PendingIntent.FLAG_UPDATE_CURRENT);
		//Setup a long to work with the sync interval chosen
		long sync_interval = (long) sync_frequency;
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis()+(2*1000),
				(60*sync_interval*1000),
				pendingIntentAlarm);
		Log.d("Ok Button", "Created Alarm. Action = " + "TempWidgetConfig" +
				" URI = " + build.build().toString() +
				" Seconds = " + 60*sync_interval);

		//Return the original widget ID, found in onCreate().
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, awID);
		setResult(RESULT_OK, resultValue);
		Toast.makeText(this, "Your widget will update shortly", Toast.LENGTH_LONG).show();
		finish();

		//
        //

		/*
		THIS ALL WORKS, LEAVE IT BACK IN IF THE ALARM MANAGER FAILS

		//IMPORTANT! The following code opens the class when clicked
		Intent intent = new Intent(c, SelectMessageActivity2.class);
		//A pending intent to launch upon clicking
		PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, intent, 0);
		//Set the onClickListener the TEXTVIEW. If the click the textview, it opens up the SelectMessageActivity2
		v1.setOnClickPendingIntent(R.id.widget_mail_message_count, pendingIntent);
		//Update the widget with the remote view
		awm.updateAppWidget(awID, v1);
		//Lastly, need to set a result
		Intent result = new Intent();
		//Updating the ID that is being called
		result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, awID);

		//Confirm the result works then set it
		setResult(RESULT_OK, result);

		//We want this to finish when the button is clicked
		finish();

		*/
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