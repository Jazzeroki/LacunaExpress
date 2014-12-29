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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;



public class SelectAccount extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener {
	ArrayList<AccountInfo> accounts;
	Button modifyAccount, addAccount, bMail;
	Spinner account_list;
	String selectedAccount, word_in_spinner;
    Boolean modifyAccountActivity = false;

	SharedPreferences prefs;

	ArrayList<String> user_accounts = new ArrayList<String>();
	
	private static final int RESULT_SETTINGS = 1;
	
    @SuppressWarnings("rawtypes")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);
        Log.d("Select Account", "Select Account oncreate");
        Initialize();
	    prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    //Set layout
	    setTheBackground();

        ReadInAccounts();
        if(accounts.size() == 1){
            selectedAccount = accounts.get(0).displayString;
            Log.d("Select Account", selectedAccount);
            user_accounts.add(selectedAccount);
        }
        else{
            for(AccountInfo i: accounts){
                Log.d("Select Account", i.displayString); //
	            Log.d("Account should be", "Listed above");
                user_accounts.add(i.displayString);
                if(i.defaultAccount == true){
                    Log.d("SelectAccount.Initialize", "Setting spinner "+i.displayString);
                    selectedAccount = i.displayString;
                }
            }
	        Log.d("For Loop", "Of Adding accounts completed");
        }

	    for (int i =0; i< user_accounts.size(); i++){
		    String temp = user_accounts.get(i);
		    Log.d("USER ACCOUNTS LISTED", temp);
	    }
	    if(user_accounts.size() == 0){
		    Log.d("USER ACCOUNTS", "ARRAYLIST IS EMPTY!");
	    }

	    //This is for the spinner - Make sure to run it after the user_accounts arraylist has been populated
	    @SuppressWarnings("unchecked")
	    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, user_accounts);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    account_list.setAdapter(adapter);
    }
 
    private void ReadInAccounts() {
		Log.d("SelectAccountActivity.ReadInAccounts", "checking for file" + AccountMan.CheckForFile());
		accounts = AccountMan.GetAccounts();
		//Comment out this line and uncomment the line above after purging the excess accounts
		//accounts = AccountMan.PurgeDuplicateAccounts(AccountMan.GetAccounts());
		
		Log.d("SelectAccountActivity.ReadInAccounts", String.valueOf(accounts.size()));
		
	}
 
	//Initialize Variables
    private void Initialize() {
	    word_in_spinner = "";

    	modifyAccount = (Button) findViewById(R.id.select_account_modify);
    	addAccount = (Button) findViewById(R.id.select_account_add);
    	bMail = (Button) findViewById(R.id.select_account_mail);
    	
    	modifyAccount.setOnClickListener(this);
    	addAccount.setOnClickListener(this);
    	bMail.setOnClickListener(this);
		
    	account_list = (Spinner) findViewById(R.id.select_account_spinner);
	    account_list.setOnItemSelectedListener(this);

        //experimenting with datetime stuff
        /*
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 2);
        Toast.makeText(getApplicationContext(), String.valueOf(c.getTime()), Toast.LENGTH_LONG).show();
        */
	}
 
 
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

        case R.id.menu_settings:
            Intent i = new Intent(this, UserSettingActivity.class);
            //startActivityForResult(i, RESULT_SETTINGS);
	        startActivityForResult(i, 0);
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

	    /*
        StringBuilder builder = new StringBuilder();

        builder.append("\n Username: "
                + sharedPrefs.getString("prefUsername", "NULL"));

        builder.append("\n Send report:"
                + sharedPrefs.getBoolean("prefSendReport", false));

        builder.append("\n Sync Frequency: "
                + sharedPrefs.getString("prefSyncFrequency", "NULL"));

	     */
    }    
	@Override
	public void onClick(View v) {
		/*
		Log.d("SelectAccount.onclick", "Tracking which handler is being called");
		Log.d("SelectAccount.onclick", "getID "+String.valueOf(v.getId()));
		Log.d("SelectAccount.onclick", String.valueOf(R.id.select_account_modify));
		Log.d("SelectAccount.onclick", String.valueOf(R.id.select_account_add));
		Log.d("SelectAccount.onclick", String.valueOf(R.id.select_account_mail));
		*/

       // Log.d("SelectAccount.onclick", );
		switch (v.getId()){
		
		
		//Modify Account
		case R.id.select_account_modify:
			try {
				//Starting to add intent code to launch addAccount as a modified account
				Intent openActivity = new Intent(this, AddAccount.class);
                Log.d("SelectAccount.onclick",selectedAccount);
				openActivity.putExtra("displayString", selectedAccount);
				//openActivity.putExtra("username", "");
				//openActivity.putExtra("server", "");
				//openActivity.putExtra("defaultAccount", "");
                startActivity(openActivity);

			} catch (Exception e){
				e.printStackTrace();
			}
			break;
			
		//Add Account
		case R.id.select_account_add:
			try {
			
	            Intent openActivity1 = new Intent(this, AddAccount.class);
                startActivity(openActivity1);
				
			} catch (Exception e){
				e.printStackTrace();
			}
			break;	
			
		//Open the mail activity
		case R.id.select_account_mail:
			
			try {
				Log.d("SelectAccount.onClick", "Mail button was clicked, launching mail activity");
                Log.d("SelectAccount.onclick", "selectAccountMail selected account "+selectedAccount);
	            Intent openActivity = new Intent(this, SelectMessageActivity2.class);
				String text_display_string = account_list.getSelectedItem().toString();
	            openActivity.putExtra("displayString", text_display_string);
                startActivity(openActivity);
                Log.d("Account Passed was: ", text_display_string);
				
			} catch (Exception e){
				e.printStackTrace();
			}
			break;	
			
		}
	}



	//When an item is selected with the spinner
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

		//First spinner, account_list
		if (parent == account_list){
			//Get the position within the spinner
			int position0 = account_list.getSelectedItemPosition();
			word_in_spinner = Integer.toString(account_list.getSelectedItemPosition());
			Log.d("Word in the spinner is: ", word_in_spinner);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	private void setTheBackground(){
		String user_choice = prefs.getString("pref_background_choice","blue_glass");
		Log.d("User Background Choice", user_choice);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_select_account_layout);
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