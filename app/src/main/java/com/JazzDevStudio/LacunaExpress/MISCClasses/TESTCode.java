package com.JazzDevStudio.LacunaExpress.MISCClasses;

public class TESTCode {

	/*

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
