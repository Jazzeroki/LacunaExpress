package com.JazzDevStudio.LacunaExpress;

import AccountMan.AccountInfo;
import JavaLEWrapper.Empire;
import Server.AsyncServer;
import Server.ServerRequest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ModifyAccount extends Activity implements OnClickListener, OnCheckedChangeListener, android.widget.CompoundButton.OnCheckedChangeListener {

	private AccountInfo account = new AccountInfo();
	
    //Global Variables -- May need to add private declaration
    Button bModifyAccount;
    RadioGroup rg;
    CheckBox cbdfAccount;
    EditText etusername, etpassword;
    
    String server;
    boolean remember_me;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account);
        
        //Clearing up the onCreate by adding this method, initializes variables
        Initialize();
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
	            s.addListener(this); //Not really sure why this is erroring out... Any Ideas Jazz?
	            s.execute(sRequest);
	            Log.d("Login", "Login Success");
	            //AsyncServer clears all listeners after the requests have been recieved.
	        }
	        else{
	        	Log.d("Blank Info", "account fields are blank?");
	        	if(account.userName.isEmpty())
	        		serverReply.setText((CharSequence) "No username Entered", EditText.BufferType.NORMAL);
	        	if(account.password.isEmpty())
	        		serverReply.setText((CharSequence) "No Password Entered", EditText.BufferType.NORMAL);
	        	if(account.server.isEmpty())
	        		serverReply.setText((CharSequence) "server is blank", EditText.BufferType.NORMAL);
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
		
}
