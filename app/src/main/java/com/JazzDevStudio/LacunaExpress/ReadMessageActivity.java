package com.JazzDevStudio.LacunaExpress;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;
import com.google.gson.Gson;


public class ReadMessageActivity extends Activity implements serverFinishedListener, OnClickListener {
    //declaring variables to be used in activity
	Button btReply, btDelete, btArchive, btForward;
	TextView tvFrom, tvTo, tvSubject, tvMessage, tvDate;
	String messageID;
	AccountInfo account;
	Intent intent;
	Bundle extras;
    Response.Messages message;
	private static final int RESULT_SETTINGS = 1;

	SharedPreferences prefs;
	//This will be set to true after the message has been recieved and will be used
	//as part of the onclick handler to prevent them from running if there is no message
	Boolean messageRetrieved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_read);
	    Initialize();
	    prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    setTheBackground();

    }
    
    public void Initialize() {

	    //Buttons
	    btReply = (Button) findViewById(R.id.account_mail_read_reply);
	    btDelete = (Button) findViewById(R.id.account_mail_read_delete);
	    btArchive = (Button) findViewById(R.id.account_mail_read_archive);
	    btForward = (Button) findViewById(R.id.account_mail_read_forward);

	    //TextViews
	    tvFrom = (TextView) findViewById(R.id.account_mail_read_from);
	    tvTo = (TextView) findViewById(R.id.account_mail_read_to);
	    tvSubject = (TextView) findViewById(R.id.account_mail_read_subject);
	    tvMessage = (TextView) findViewById(R.id.account_mail_read_message);
	    tvDate = (TextView) findViewById(R.id.account_mail_read_date);

	    //Set the buttons to a listener
	    btReply.setOnClickListener(this);
	    btDelete.setOnClickListener(this);
	    btArchive.setOnClickListener(this);
	    btForward.setOnClickListener(this);

	    //Makes the message scrollable
	    tvMessage.setMovementMethod(new ScrollingMovementMethod());
	    // You can be pretty confident that the intent will not be null here.
	    intent = getIntent();

	    // Get the extras and fetching message to display
	    extras = intent.getExtras();
	    if (extras != null) {

		    messageID = extras.getString("message_id_passed");
            Log.d("messageidpassed",extras.getString("message_id_passed") );
		    account = AccountMan.GetAccount(extras.getString("displayString"));
		    //Inbox inbox = new Inbox();
		    String request = Inbox.ReadMessage(account.sessionID, messageID);
            Log.d("request", request);
		    Log.d("ReadMessageActivity.onCreate", "MessageID "+messageID);
		    ServerRequest sRequest = new ServerRequest(account.server, Inbox.url, request);
		    AsyncServer s = new AsyncServer();
		    s.addListener(this);
		    s.execute(sRequest);

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



	@Override
	public void onClick(View v) {

		switch (v.getId()) {
            case R.id.account_mail_read_reply:
                try {
                    if (messageRetrieved) {

                        //setting extras to be loaded in reply activity
                        Log.d("ReadMessage.onclick", "Replying to message");
                        Log.d("ReadMessage.onclick", message.to);
	                    Intent openActivity = new Intent (this, ComposeMessageActivity.class);
	                    //openActivity.putExtra("displayString", selectedAccount.username);
	                    openActivity.putExtra("displayString", account.displayString);
                        openActivity.putExtra("from", message.from);
                        openActivity.putExtra("to", message.to);
                        openActivity.putExtra("subject", message.subject);
                        openActivity.putExtra("body", message.body);
	                    //openActivity.putExtra("server", "");
	                    //openActivity.putExtra("defaultAccount", "");
	                    startActivity(openActivity);
	                    finish();
                    }
                    //Intent openActivity = new Intent(this, SelectAccount.class);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.account_mail_read_delete:
                try {
                    if (messageRetrieved) {
                        Inbox inbox = new Inbox();
                        String request = inbox.TrashMessages(2, account.sessionID, messageID);
                        ServerRequest sRequest = new ServerRequest(account.server, Inbox.url, request);
                        AsyncServer s = new AsyncServer();
                        s.execute(sRequest);
                        finish();
                    }
                    //Starting to add intent code to launch addAccount as a modified account
                    //Intent openActivity = new Intent(this, SelectAccount.class);
                    //openActivity.putExtra("displayString", selectedAccount);
                    //openActivity.putExtra("username", "");
                    //openActivity.putExtra("server", "");
                    //openActivity.putExtra("defaultAccount", "");
                    //startActivity(openActivity);
                    //finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.account_mail_read_archive:
                try {
                    if (messageRetrieved) {
                        if (messageRetrieved) {
                            Inbox inbox = new Inbox();
                            String request = inbox.ArchiveMessages(2, account.sessionID, messageID);
                            ServerRequest sRequest = new ServerRequest(account.server, Inbox.url, request);
                            AsyncServer s = new AsyncServer();
                            s.execute(sRequest);
                            finish();
                        }
                        //Starting to add intent code to launch addAccount as a modified account
                        //Intent openActivity = new Intent(this, SelectAccount.class);
                        //openActivity.putExtra("displayString", selectedAccount);
                        //openActivity.putExtra("username", "");
                        //openActivity.putExtra("server", "");
                        //openActivity.putExtra("defaultAccount", "");
                        //startActivity(openActivity);
                        //finish();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

			case R.id.account_mail_read_forward:
				//Forward the mail here
				try{

				} catch (Exception e){
					e.printStackTrace();
				}
				break;
        }
        }
    @Override
    public void onResponseReceived(String reply) {
        Log.d("ReadMessageAcitivity.onResponseRecieved", reply);
        if(!reply.equals("error")){
            messageRetrieved = true;
            Response r = new Gson().fromJson(reply, Response.class);
            tvFrom.setText(r.result.message.from);
            tvTo.setText(r.result.message.to);
            tvSubject.setText(r.result.message.subject);
            tvMessage.setText(r.result.message.body);
            message = r.result.message;

	        //To set the date, need to Shorten it to the first 20 characters to eliminate time zone declaration
	        String str = r.result.message.date;
	        String adjust_date = str.substring(0, Math.min(str.length(), 20));
	        tvDate.setText(adjust_date);
        }

    }

	private void setTheBackground(){
		String user_choice = prefs.getString("pref_background_choice","blue_glass");
		Log.d("User Background Choice", user_choice);
		LinearLayout layout = (LinearLayout) findViewById(R.id.activity_mail_read_layout);
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



