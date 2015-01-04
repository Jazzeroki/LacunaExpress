package com.JazzDevStudio.LacunaExpress.Widget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox;
import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Splash;

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
public class MailWidgetConfig extends Activity implements View.OnClickListener, OnCheckedChangeListener {

	Button create;
	Spinner widget_mail_config_spinner_account, widget_mail_config_spinner_tag,
			widget_mail_config_spinner_color, widget_mail_config_spinner_font;
	
	RadioGroup widget_mail_config_radiogroup;

	AppWidgetManager awm;
	Context c;
	int awID;

	//Shared Preferences
	SharedPreferences prefs;

	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_mail_config);
		Initialize();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setTheBackground();



	}

	private void Initialize() {
		create = (Button) findViewById(R.id.widget_mail_config_create);
		create.setOnClickListener(this);

		c = MailWidgetConfig.this;

		//Spinners
		widget_mail_config_spinner_account = (Spinner) findViewById(R.id.widget_mail_config_spinner_account);
		widget_mail_config_spinner_tag = (Spinner) findViewById(R.id.widget_mail_config_spinner_tag);
		widget_mail_config_spinner_color = (Spinner) findViewById(R.id.widget_mail_config_spinner_color);
		widget_mail_config_spinner_font = (Spinner) findViewById(R.id.widget_mail_config_spinner_font);

		//Radio Group
		widget_mail_config_radiogroup = (RadioGroup) findViewById(R.id.widget_mail_config_radiogroup);
		widget_mail_config_radiogroup.setOnCheckedChangeListener(this);





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
		//First spinner, account_list
		if (parent == widget_mail_config_spinner_account){
			//Get the position within the spinner
			int position0 = account_list.getSelectedItemPosition();
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

		}

		//Fourth spinner, font color of widget
		if (parent == widget_mail_config_spinner_font){

		}
	}


	//This handles the radio buttons
	public void onCheckedChanged(RadioGroup rg, int checkedId) {
		//Depending on which one is selected. Default is 15 minutes
		switch(checkedId){

			//5 Minutes Refresh
			case R.id.widget_mail_config_button5:

				break;

			//10 Minutes Refresh
			case R.id.widget_mail_config_button10:

				break;

			//15 Minutes Refresh
			case R.id.widget_mail_config_button15:

				break;

			//30 Minutes Refresh
			case R.id.widget_mail_config_button30:

				break;

			//60 Minutes Refresh
			case R.id.widget_mail_config_button60:

				break;
		}
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
