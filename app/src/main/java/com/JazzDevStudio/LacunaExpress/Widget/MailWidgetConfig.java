package com.JazzDevStudio.LacunaExpress.Widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RemoteViews;
import android.widget.Spinner;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.AddAccount;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.MISCClasses.L;
import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Splash;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Widget --
 -If Mail is <100 font size = x. if mail is >=100 but <1000, font size = x. If mail is >=1000, font size = x.
 -Widget to show mail count of chosen account
 -drop down menu spinner to choose mail account to add. Have it read from accounts. If no accounts or if file does not exist, take to add account activity.
 -options to choose font color
 -options to choose background color
 -options to choose refresh interval
 -If widget is clicked, open the activity to view mail passing in the respective account information.
 -Need to have the account info held in the widget itself before passing it into the class.
 .
 */
public class MailWidgetConfig extends Activity implements View.OnClickListener, OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

	private static final int RESULT_SETTINGS = 1;
	String color_background_choice, font_color_choice;

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

	//Utilizing the color.xml file
	ArrayList<String> color_names = new ArrayList<String>();

	//Messages Info
	ArrayList<Response.Messages> messages_array = new ArrayList<Response.Messages>();
	Boolean messagesReceived = false;
	private String tag_chosen = "Correspondence";
	static final String[] messageTags = {"All", "Correspondence", "Tutorial", "Medal", "Intelligence", "Alert", "Attack", "Colonization", "Complaint", "Excavator", "Mission", "Parliament", "Probe", "Spies", "Trade", "Fissure"};


	//
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

		//Background Color Choice spinner
		ArrayAdapter adapter_background_color_choice = new ArrayAdapter(this, android.R.layout.simple_spinner_item, color_names);
		adapter_message_tag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		widget_mail_config_spinner_color.setAdapter(adapter_background_color_choice);

		//Font color choice spinner
		ArrayAdapter adapter_font_color_choice = new ArrayAdapter(this, android.R.layout.simple_spinner_item, color_names);
		adapter_message_tag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		widget_mail_config_spinner_font.setAdapter(adapter_font_color_choice);

		//Set the default values in each spinner
		widget_mail_config_spinner_account.setSelection(0);
		//This may be erroneous code...
			//This code sets the default spinner to the one passed in by the intent
			//ArrayAdapter name_adapter_1 = (ArrayAdapter) widget_mail_config_spinner_account.getAdapter(); //cast to an ArrayAdapter
			//int spinnerPosition = name_adapter_1.getPosition(0);
			//set the default according to value
			//widget_mail_config_spinner_account.setSelection(0);
		widget_mail_config_spinner_tag.setSelection(0);
		widget_mail_config_spinner_color.setSelection(0);
		widget_mail_config_spinner_font.setSelection(0);

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

		c = MailWidgetConfig.this;

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

		//
		awm = AppWidgetManager.getInstance(c);
	}


	//Create the widget here
	public void onClick(View v) {
		//Set the string = to the info getText

		//Setup a remoteview referring to the context (Param1) and relating to the widget (Param2)
		RemoteViews v1 = new RemoteViews(c.getPackageName(), R.layout.widget_mail_layout);

		String e = "TESTING";
		//Setting the remote view (remote meaning on the homescreen widget) to the text_view
		v1.setTextViewText(R.id.text_view_config_input, e);

		//IMPORTANT! This intent opens the class when clicked
		Intent intent = new Intent(c, Splash.class);

		//A pending intent
		PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, intent, 0);

		//Set the onClickListener for the button
		v1.setOnClickPendingIntent(R.id.button_widget_open, pendingIntent);


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

	}

	//When an item is selected with the spinner
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		//First spinner, widget_mail_config_spinner_account
		if (parent == widget_mail_config_spinner_account){
			//Get the position within the spinner
			int position0 = widget_mail_config_spinner_account.getSelectedItemPosition();
			String word_in_spinner = user_accounts.get(position0);
			Log.d("SelectMessage.onItemSelected assigning selected account", "word in spinner "+ word_in_spinner);

			if (tag_chosen == "All"){
				//Check the account via the spinner chosen
				selectedAccount = AccountMan.GetAccount(word_in_spinner);
				Log.d("SelectMessage.onItemSelected", "Tag All Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID);
				Log.d("Select Message Activity, SelectedAccount", selectedAccount.userName);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();
				s.addListener(this);
				s.execute(sRequest);
				Log.d("LOOK HERE", "REQUEST SENT 266");
			} else {
				//Check the account via the spinner chosen
				selectedAccount = AccountMan.GetAccount(word_in_spinner);
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

			if (tag_chosen == "All"){
				Log.d("SelectMessage.onItemSelected", "Second Spinner Tag All Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				Log.d("Select Message Activity, SelectedAccount", selectedAccount.userName);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();
				s.addListener(this);
				s.execute(sRequest);
			} else {
				Log.d("SelectMessage.onItemSelected", "Second Spinner word in spinner All Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID, tag_chosen);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				Log.d("Select Message Activity, SelectedAccount", selectedAccount.userName);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();
				s.addListener(this);
				s.execute(sRequest);
			}
		}

		//Third spinner, Background Color of widget
		if (parent == widget_mail_config_spinner_color){
			int position1 = widget_mail_config_spinner_color.getSelectedItemPosition();
			String word_in_spinner = color_names.get(position1);
			color_background_choice = word_in_spinner;
		}

		//Fourth spinner, font color of widget
		if (parent == widget_mail_config_spinner_font){
			int position1 = widget_mail_config_spinner_font.getSelectedItemPosition();
			String word_in_spinner = color_names.get(position1);
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
		LinearLayout layout = (LinearLayout) findViewById(R.id.activity_add_account_layout);
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

}
