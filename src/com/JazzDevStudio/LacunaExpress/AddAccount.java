package com.JazzDevStudio.LacunaExpress;

import AccountMan.AccountInfo;
import JavaLEWrapper.Empire;
import LEWrapperResponse.Response;
import Server.AsyncServer;
import Server.ServerRequest;
import Server.serverFinishedListener;
import android.app.Activity;
//import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
//import android.widget.Toast;

import com.google.gson.Gson;

public class AddAccount extends Activity implements serverFinishedListener, OnClickListener, OnCheckedChangeListener, android.widget.CompoundButton.OnCheckedChangeListener {

    private AccountInfo account = new AccountInfo();
    
    //Global Variables -- May need to add private declaration
    Button bAddAccount;
    RadioGroup rg;
    CheckBox cbdfAccount;
    EditText etusername, etpassword;
    
    String server;
    boolean remember_me;
    
    public void onResponseRecieved(String reply) {
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
            Log.d("Saving Account", "Creating AccountMan");
            AccountMan.AccountMan acm = new AccountMan.AccountMan();
            Log.d("Saving Account", "AddAccount called to finish save");
            acm.AddAccount(account);
            
            Log.d("Select Acount", "Launching Select Account");
            Intent intent = new Intent(this, SelectAccount.class);
            startActivity(intent);          
        } 
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        
        //Clearing up the onCreate by adding this method, initializes variables
        Initialize();
        Intent i = getIntent();
        if(i.hasExtra("username")){
        	Log.d("AddAccount.onCreate", "Intent is type Modify");
        }
        else{
        	Log.d("AddAccount.onCreate", "Intent is type addAccount");
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

    	//Server String set to nothing upon initialization
    	server ="https://us1.lacunaexpanse.com";
    	
    	//Default the remember me box to false
    	remember_me = false;
    	
    	//Initialize Global Variables
    	rg = (RadioGroup) findViewById(R.id.add_account_server_choices);
    	etusername = (EditText) findViewById(R.id.add_account_username);
    	etpassword = (EditText) findViewById(R.id.add_account_password);
    	cbdfAccount = (CheckBox) findViewById(R.id.add_account_default);
    	bAddAccount = (Button) findViewById(R.id.add_account_add_account);
    	
    	//Set the button to clickable
    	bAddAccount.setOnClickListener(this);
    	
    	//For the Radio button so it is waiting for the choice to be changes
    	rg.setOnCheckedChangeListener(this);
    	
    	//For the Checkbox
    	cbdfAccount.setOnCheckedChangeListener(this);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //
    	/*
    	 * Commenting this out for now, we will need to redo the design a bit if we want this as a menu.
    	 * I will look into a more efficient way of inflating this. Go ahead and just ignore it for now.
    	 */
    	//getMenuInflater().inflate(R.menu.menu_add_account, menu);
    	//
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
	            
	            Intent openActivity = new Intent(this, SelectAccount.class);
                startActivity(openActivity);
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
