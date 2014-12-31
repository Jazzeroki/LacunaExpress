package com.JazzDevStudio.LacunaExpress;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Empire;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.MISCClasses.CheckInternetConnection;
import com.JazzDevStudio.LacunaExpress.MISCClasses.L;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;
import com.google.gson.Gson;

import java.util.Calendar;


//import android.content.Context;
//import android.widget.Toast;

public class AddAccount extends Activity implements serverFinishedListener, OnClickListener, OnCheckedChangeListener, android.widget.CompoundButton.OnCheckedChangeListener {

    private AccountInfo account = new AccountInfo();
    
    //Global Variables -- May need to add private declaration
    Button bAddAccount, delete, open_web;
    RadioGroup rg;
    CheckBox cbdfAccount;
    EditText etusername, etpassword;
	private static final int RESULT_SETTINGS = 1;
    
    String mServer = "https://us1.lacunaexpanse.com";
    boolean remember_me;

	SharedPreferences prefs;

    //used to tell the button whether the request is of type modify or add
    private Boolean activityTypeModify = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_account);

		//Clearing up the onCreate by adding this method, initializes variables
		Initialize();
		//Set the background
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setTheBackground();

		//Checking for internet connection
		boolean do_we_have_network_connection = false;
		//Check if there is internet connection
		do_we_have_network_connection = CheckInternetConnection.haveNetworkConnection(AddAccount.this);
		Log.d("Checking for internet connection", Boolean.toString(do_we_have_network_connection));
		//DO SOMETHING HERE IF NO INTERNET CONNECTION HAPPENS
	}

	public void onResponseReceived(String reply) {
        //This is the listener to the server event
        //if server request errors it returns error
    	Log.d("onResponse Recieved", reply);
    	//TextView serverReply = (TextView) findViewById(R.id.textViewServerReply);
        //serverReply.setText((CharSequence)reply, EditText.BufferType.NORMAL);
    	
        if(!reply.equals("error")) {
        	Log.d("Deserializing Response", "Creating Response Object");
            //Context context = getApplicationContext();
            //CharSequence text = reply;
            //int duration = Toast.LENGTH_LONG;
            //Toast toast = Toast.makeText(context, text, duration);
            //toast.show();
            //TextView serverReply = (TextView) findViewById(R.id.textViewServerReply);
            //serverReply.setText((CharSequence) serverReply, EditText.BufferType.NORMAL);

            //Deserializing response and pulling session data out
            
            Response r = new Gson().fromJson(reply, Response.class);
            Log.d("Getting Session ID","");

            account.sessionID = r.result.session_id;
            account.CreateDisplayString();
            Calendar c = Calendar.getInstance();
            account.colonies = r.result.status.empire.colonies;
            account.stations = r.result.status.empire.stations;
            account.bodiesCombined = r.result.status.empire.planets;
            account.homePlanetID = r.result.status.empire.home_planet_id;
            //c.add(Calendar.HOUR, 2);
            account.sessionExpires = c;
            Log.d("AddAccount.onResponseReceived", account.displayString);
            Log.d("Saving Account", "Creating AccountMan");
            //AccountMan.AccountMan acm = new AccountMan.AccountMan();
            Log.d("Saving Account", "AddAccount called to finish save");
            //acm.AddAccount(account);
            com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan.AddAccount(account);
            
            Log.d("Select Acount", "Launching Select Account");
            Intent intent = new Intent(this, SelectAccount.class);
            startActivity(intent);
        } 
    }

    /*
     * Helps un-clutter the onCreate. All of this code could go int
     * the onCreate method, but it makes for easier viewing/ reading.
     * Note, NO new variables are defined here else they would be 
     * isolated to this class alone
     */
    //To Initialize Variables
    private void Initialize() {

    	//Default the remember me box to false
    	remember_me = false;
    	
    	//Initialize Global Variables
    	rg = (RadioGroup) findViewById(R.id.add_account_server_choices);
    	etusername = (EditText) findViewById(R.id.add_account_username);
    	etpassword = (EditText) findViewById(R.id.add_account_password);
    	cbdfAccount = (CheckBox) findViewById(R.id.add_account_default);
    	bAddAccount = (Button) findViewById(R.id.add_account_add_account);
        delete = (Button)findViewById(R.id.bt_Delete_File);
	    open_web = (Button) findViewById(R.id.add_account_open_internet);
    	
    	//Set the button to clickable
    	bAddAccount.setOnClickListener(this);
	    delete.setOnClickListener(this);
	    open_web.setOnClickListener(this);
    	
    	//For the Radio button so it is waiting for the choice to be changes
    	rg.setOnCheckedChangeListener(this);
    	
    	//For the Checkbox
    	cbdfAccount.setOnCheckedChangeListener(this);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras !=null){
            Log.d("addAccount.Initialize", "request is type modify");
            activityTypeModify = true;
            Log.d("addAccount.Initialize", extras.getString("displayString"));
            account = com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan.GetAccount(extras.getString("displayString"));
            etusername.setText(account.userName);
            Log.d("addAccount.Initialize", "setting server radiobuttons");
            if(account.server.contains("US1"))

                rg.check(R.id.add_account_us1);
            else {
                rg.check(R.id.add_account_pt);
            }
            Log.d("addAccount.Initialize", "setting default account checkbox");
            if(account.defaultAccount == true)
                cbdfAccount.setChecked(true);

        }
        if(i.hasExtra("displayString")){
            Log.d("AddAccount.onCreate", "Intent is type Modify");
        }
        else{
            Log.d("AddAccount.onCreate", "Intent is type addAccount");
        }
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	//The next 3 methods work in conjunction to open up your options menu
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

			case R.id.menu_settings:
				Intent i = new Intent(this, UserSettingActivity.class);
				startActivityForResult(i, RESULT_SETTINGS);
				break;
		}
		return true;
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case RESULT_SETTINGS:
				showUserSettings();
				break;
		}
	}
	private void showUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
	}


	//This handles the click methods of the buttons so when they are clicked, the actions are defined here
	public void onClick(View v) {
		TextView serverReply = (TextView) findViewById(R.id.textViewServerReply);
		//serverReply.setText((CharSequence) "A button was clicked", EditText.BufferType.NORMAL);
		//serverReply.setText((CharSequence) "A button was clicked", EditText.BufferType.NORMAL);

		switch(v.getId()) {

			//Add an account to the app
			case R.id.add_account_add_account:
				try {

					account.userName = etusername.getText().toString();
					account.password = etpassword.getText().toString();
					account.server = mServer;
					account.defaultAccount = cbdfAccount.isChecked();

					Log.d("username", account.userName);
					Log.d("password", account.password);
					Log.d("server", account.server);
					Log.d("default account", account.defaultAccount.toString());

					//if all required fields are filled in then the request will be sent to the server
					if (!account.userName.isEmpty() && !account.password.isEmpty() && !account.server.isEmpty()) {
						Empire e = new Empire();
						String request = e.Login(account.userName, account.password, 1);
						Log.d("Request to server", request);
						ServerRequest sRequest = new ServerRequest(mServer, Empire.url, request);
						AsyncServer s = new AsyncServer();
						s.addListener(this);
						s.execute(sRequest);
						Log.d("Login", "Login Success");
						//AsyncServer clears all listeners after the requests have been recieved.

						//Account has been added
						L.makeToastShort(this, "Account Added Successfully");

						Intent openActivity = new Intent(this, SelectAccount.class);
						startActivity(openActivity);

					} else {
						Log.d("Blank Info", "account fields are blank?");
						if (account.userName.isEmpty()) {
							serverReply.setText((CharSequence) "No username Entered", EditText.BufferType.NORMAL);
							L.makeToast(this, "No username Entered");
						}
						if (account.password.isEmpty()) {
							serverReply.setText((CharSequence) "No Password Entered", EditText.BufferType.NORMAL);
							L.makeToast(this, "No Password Entered");
						}
						if (account.server.isEmpty()) {
							serverReply.setText((CharSequence) "Server is Blank", EditText.BufferType.NORMAL);
							L.makeToast(this, "Server is Blank");
						}
						//serverReply.setText((CharSequence) "A button was clicked", EditText.BufferType.NORMAL);
					}

				} catch (Exception e) {
					e.printStackTrace();
					Log.d("Error", "Login Error");
				}
				break;

			//PURGES ALL ACCOUNTS
			case R.id.bt_Delete_File:
				//Adding a dialog interface to confirm the user wants to delete everything

				//This is the dialog listener
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						switch (which) {

							//If they hit no
							case DialogInterface.BUTTON_NEGATIVE:
								try {
									dialog.dismiss();
								} catch (Exception e) {
									e.printStackTrace();
								}
								break;

							case DialogInterface.BUTTON_POSITIVE:
								try {
									com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan.DeleteFile();
									Log.d("Delete File", "Was Called");
									dialog.dismiss();
								} catch (Exception e){
									e.printStackTrace();
								}
								break;
						}
					}
				};
				String confirm = "Are you SURE you want to DELETE all accounts?";
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(confirm).setNegativeButton("No", dialogClickListener).setPositiveButton("Yes", dialogClickListener).show();
				break;

			//Opens up the web address for the user to create an account
			case R.id.add_account_open_internet:
				try{
					String lacuna_url = "https://us1.lacunaexpanse.com/";
					//Opens a link directly to my play store download
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lacuna_url));
					//This line makes it so the user comes back into the Lacuna Express app once they go home as opposed to their browser
					browserIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
					startActivity(browserIntent);

				} catch(Exception e){
					e.printStackTrace();
				}
				break;
		}
	}

	//This handles the radio buttons
	public void onCheckedChanged(RadioGroup rg, int checkedId) {
		Log.d("AddAccount.onCheckedChange", "Checking the values of the server radio buttons");
		Log.d("AddAccount.onCheckedChange", "checkedID "+ checkedId);
		Log.d("AddAccount.onCheckedChange", "R.id.addaccountUS1 "+R.id.add_account_us1);
		Log.d("AddAccount.onCheckedChange", "R.id.addaccountUS1 "+R.id.add_account_pt);

		switch(checkedId){
		
		//US1, set the server choice to US1
		case R.id.add_account_us1:
			Log.d("AddAccount.onCheckedChange", "Setting Server to US1");
			mServer = "https://us1.lacunaexpanse.com";
			//account.server = "https://us1.lacunaexpanse.com";
			Log.d("Radio", "US1 Checked");
			break;
			
		//PT, set the server choice to PT			
		case R.id.add_account_pt:
			Log.d("AddAccount.onCheckedChange", "Setting Server to PT");
			mServer = "https://pt.lacunaexpanse.com";
			//account.server = "https://pt.lacunaexpanse.com";
			Log.d("Radio", "PT Checked");
			break;
		}
	}

	//This covers the Checkbox. Same method as radiogroup, but overloaded for checkbox
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//cbdfAccount <-- Checkbox
		if (buttonView.isChecked()) { 
			//checked
			Log.d("Checkbox", "Default Button Checked");
			remember_me = true;
		} else {
			//not checked
			Log.d("Checkbox", "Default Button Unchecked");
			remember_me = false;
		} 
	}

	private void setTheBackground(){
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
