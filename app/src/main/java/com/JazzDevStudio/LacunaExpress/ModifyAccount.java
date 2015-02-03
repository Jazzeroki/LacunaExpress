package com.JazzDevStudio.LacunaExpress;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Empire;
import com.JazzDevStudio.LacunaExpress.MISCClasses.L;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class ModifyAccount extends Activity implements serverFinishedListener, OnClickListener, OnCheckedChangeListener, android.widget.CompoundButton.OnCheckedChangeListener {

	private AccountInfo account = new AccountInfo();
	
    //Global Variables -- May need to add private declaration
    Button bModifyAccount;
    RadioGroup rg;
    CheckBox cbdfAccount;
    EditText etusername, etpassword;
	private static final int RESULT_SETTINGS = 1;
    
    String server;
    boolean remember_me;

	SharedPreferences prefs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account);
        
        //Clearing up the onCreate by adding this method, initializes variables
        Initialize();
	    prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    //Set the background
	    setTheBackground();

    }

	private void Initialize() {

		//Server String set to nothing upon initialization
    	server ="https://us1.lacunaexpanse.com";
    	
    	//Default the remember me box to false
    	remember_me = false;
    	
    	//Initialize Global Variables
    	rg = (RadioGroup) findViewById(R.id.modify_account_server_choices);
    	etusername = (EditText) findViewById(R.id.modify_account_username);
    	etpassword = (EditText) findViewById(R.id.modify_account_password);
    	cbdfAccount = (CheckBox) findViewById(R.id.modify_account_default);
    	bModifyAccount = (Button) findViewById(R.id.modify_account_modify_account);
    	
    	//Set the button to clickable
    	bModifyAccount.setOnClickListener(this);
    	
    	//For the Radio button so it is waiting for the choice to be changes
    	rg.setOnCheckedChangeListener(this);
    	
    	//For the Checkbox
    	cbdfAccount.setOnCheckedChangeListener(this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	//This handles the click methods of the buttons so when they are clicked, the actions are defined here
	public void onClick(View v) {
		TextView serverReply = (TextView) findViewById(R.id.textViewServerReply);
		serverReply.setText((CharSequence) "A button was clicked", EditText.BufferType.NORMAL);
		//serverReply.setText((CharSequence) "A button was clicked", EditText.BufferType.NORMAL);
		
		try {
			
			account.userName = etusername.getText().toString();
			account.password = etpassword.getText().toString();
			account.server = server;
			account.defaultAccount = cbdfAccount.isChecked();
			Log.d("username", account.userName);
			Log.d("password", account.password);
			Log.d("server", account.server);
			Log.d("default account", account.defaultAccount.toString());
			
	        //if all required fields are filled in then the request will be sent to the server
	        if(!account.userName.isEmpty()&&!account.password.isEmpty()&&!account.server.isEmpty()){
	            Empire e = new Empire();
	            String request = e.Login(account.userName, account.password, 1);
	            Log.d("Request to server", request);
	            ServerRequest sRequest = new ServerRequest(server, Empire.url, request);
	            AsyncServer s = new AsyncServer();
	            s.addListener(this); 
	            s.execute(sRequest);
	            Log.d("Login", "Login Success");
	            //AsyncServer clears all listeners after the requests have been recieved.
	        }
	        else{
	        	Log.d("Blank Info", "account fields are blank?");
	        	if(account.userName.isEmpty()){
	        		serverReply.setText((CharSequence) "No username Entered", EditText.BufferType.NORMAL);
	        		L.makeToast(this, "No username Entered");
	        	}
	        	if(account.password.isEmpty()){
	        		serverReply.setText((CharSequence) "No Password Entered", EditText.BufferType.NORMAL);
	        		L.makeToast(this, "No Password Entered");
	        	}
	        	if(account.server.isEmpty()){
	        		serverReply.setText((CharSequence) "Server is Blank", EditText.BufferType.NORMAL);
	        		L.makeToast(this, "Server is Blank");
	        	}
	        	//serverReply.setText((CharSequence) "A button was clicked", EditText.BufferType.NORMAL);
	        }
			
		} catch (Exception e){
			e.printStackTrace();
			Log.d("Error", "Login Error");
		}
	}

	//This handles the radio buttons
	public void onCheckedChanged(RadioGroup rg, int checkedId) {

		switch(checkedId){
		
		//US1, set the server choice to US1
		case R.id.add_account_us1:
			server = "https://us1.lacunaexpanse.com";
			account.server = "https://us1.lacunaexpanse.com";
			Log.d("Radio", "US1 Checked");
			break;
			
		//PT, set the server choice to PT			
		case R.id.add_account_pt:
			server = "https://us1.lacunaexpanse.com";
			account.server = "https://pt.lacunaexpanse.com";
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


	@Override
	public void onResponseReceived(String reply) {
	}

	private void setTheBackground(){
		String user_choice = prefs.getString("pref_background_choice","blue_glass");
		Log.d("User Background Choice", user_choice);
		LinearLayout layout = (LinearLayout) findViewById(R.id.activity_modify_account_layout);
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
