package com.JazzDevStudio.LacunaExpress;

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
import android.widget.EditText;
import android.widget.LinearLayout;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;


public class ComposeMessageActivity extends Activity implements OnClickListener, serverFinishedListener {

	private EditText mail_to, mail_message, subject;
	private Button send;
	private AccountInfo account;
    Intent intent;
    Bundle extras;
	SharedPreferences prefs;
	private static final int RESULT_SETTINGS = 1;

    public void onResponseReceived(String reply){
        Log.d("ComposeMessage.onResponseReceived", reply);
    }
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mail_compose);
		Initialize();
	    prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    //Set the background
	    setTheBackground();
	}

	private void Initialize() {

		Log.d("ComposeMessage.Initialize", "Starting Initialization");
		mail_to = (EditText) findViewById(R.id.activity_mail_compose_to);
		mail_message = (EditText) findViewById(R.id.activity_mail_compose_message);
        subject = (EditText) findViewById(R.id.editText_Subject);
		send = (Button) findViewById(R.id.activity_mail_compose_send);
		
		send.setOnClickListener(this);

        //pulling extras and populating fields
        intent = getIntent();
        extras = intent.getExtras();
        if (extras != null) {
            Log.d("ComposeMessage.Initialize", "Extras not Null");
            if (extras.containsKey("displayString")) {
                Log.d("ComposeMessage.Initialize", extras.getString("displayString"));
                Log.d("ComposeMessage.Initialize", "Starting Initialization");
                Log.d("ReadMessageActivity.onCreate", "contains Keys, prepping and launching request to server");
                account = AccountMan.GetAccount(extras.getString("displayString"));
                if(extras.containsKey("to")){
                    //Log.d("ComposeMessage.Initialize", "To: "+extras.getString("to"));
                    mail_to.setText((CharSequence)extras.get("from"));
                    Log.d("ComposeMessage.Initialize", extras.getString("subject"));
                    subject.setText((CharSequence)("RE:"+extras.get("subject")));
                    mail_message.setText((CharSequence) ("\n\n ------------------------------\n" + extras.get("body")));
                    //mail_message.setText((CharSequence) ( extras.get("body")));
                }
            }
        }

    }

	@Override
	public void onClick(View v) {
        if((subject.getText().length()!=0)&&(mail_to.getText().length()!=0)){
            switch (v.getId()){


                //Compose
                case R.id.activity_mail_compose_send:
                    try {
                        String compose_data = mail_message.getText().toString();
                        com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox inbox = new com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox();
                        Log.d("ComposeReadMessageActivity.onClick", mail_message.getText().toString());
                        String request = inbox.SendMessage(1,account.sessionID, mail_to.getText().toString(), subject.getText().toString(), mail_message.getText().toString() );
                        Log.d("ComposeReadMessageActivity.onClick", request);
                        request = request.replace("\n", "\\n");
                        Log.d("ComposeReadMessageActivity.onClick", request);
                        ServerRequest sRequest = new ServerRequest(account.server, Inbox.url, request);
                        AsyncServer s = new AsyncServer();
                        s.addListener(this);
                        s.execute(sRequest);

                        finish();

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
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


	private void setTheBackground(){
		String user_choice = prefs.getString("pref_background_choice","blue_glass");
		Log.d("User Background Choice", user_choice);
		LinearLayout layout = (LinearLayout) findViewById(R.id.activity_mail_compose_layout);
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
