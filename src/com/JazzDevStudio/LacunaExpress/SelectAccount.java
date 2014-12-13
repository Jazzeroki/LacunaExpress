package com.JazzDevStudio.LacunaExpress;
 
import java.util.ArrayList;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;

import AccountMan.AccountInfo;
import AccountMan.AccountMan;
import MISCClasses.L;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

 
public class SelectAccount extends Activity implements OnClickListener {
	ArrayList<AccountInfo> accounts;
	Button modifyAccount, addAccount, bMail;
	Spinner account_list;
	String selectedAccount;
	
	private static final int RESULT_SETTINGS = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);
        Log.d("Select Account", "Select Account oncreate");
        
        ArrayList<String> user_accounts = new ArrayList<String>();
        
        Initialize();
        ReadInAccounts();
        if(accounts.size() == 1){
        	selectedAccount = accounts.get(0).displayString;
        	Log.d("Select Account", selectedAccount);
        	user_accounts.add(selectedAccount);
        }
        else{
        	for(AccountInfo i: accounts){
        		Log.d("Select Account", i.displayString); //
        		user_accounts.add(i.displayString);
        	}
        }
        
        //This is for the spinner
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
 
    	modifyAccount = (Button) findViewById(R.id.select_account_modify);
    	addAccount = (Button) findViewById(R.id.select_account_add);
    	bMail = (Button) findViewById(R.id.select_account_mail);
    	
    	modifyAccount.setOnClickListener(this);
    	addAccount.setOnClickListener(this);
    	bMail.setOnClickListener(this);
		
    	account_list = (Spinner) findViewById(R.id.select_account_spinner);
	}
 
 
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        Log.d("Select Account", "Select Account on create options");
        
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
 
        StringBuilder builder = new StringBuilder();
 
        builder.append("\n Username: "
                + sharedPrefs.getString("prefUsername", "NULL"));
 
        builder.append("\n Send report:"
                + sharedPrefs.getBoolean("prefSendReport", false));
 
        builder.append("\n Sync Frequency: "
                + sharedPrefs.getString("prefSyncFrequency", "NULL"));
 
    }    
	@Override
	public void onClick(View v) {
		
		Log.d("SelectAccount.onclick", "Tracking which handler is being called");
		Log.d("SelectAccount.onclick", String.valueOf(v.getId()));
		Log.d("SelectAccount.onclick", String.valueOf(R.id.select_account_modify));
		Log.d("SelectAccount.onclick", String.valueOf(R.id.select_account_add));
		Log.d("SelectAccount.onclick", String.valueOf(R.id.select_account_mail));
		switch (v.getId()){
		
		
		//Modify Account
		case R.id.select_account_modify:
			try {
				//Starting to add intent code to launch addAccount as a modified account
				Intent openActivity = new Intent(this, SelectAccount.class);
				openActivity.putExtra("displayString", selectedAccount);
				//openActivity.putExtra("username", "");
				//openActivity.putExtra("server", "");
				//openActivity.putExtra("defaultAccount", "");
                startActivity(openActivity);
                finish();
			} catch (Exception e){
				e.printStackTrace();
			}
			break;
			
		//Add Account
		case R.id.select_account_add:
			try {
			
	            Intent openActivity1 = new Intent(this, AddAccount.class);
                startActivity(openActivity1);
                finish();
				
			} catch (Exception e){
				e.printStackTrace();
			}
			break;	
			
		//Open the mail activity
		case R.id.select_account_mail:
			
			try {
				Log.d("SelectAccount.onClick", "Mail button was clicked, launching mail activity");
				getDataFromSpinner();
	            Intent openActivity = new Intent(this, SelectMessageActivity.class);
	            openActivity.putExtra("displayString", selectedAccount);
                startActivity(openActivity);
                Log.d("Account Passed was: ", selectedAccount);
                finish();
				
			} catch (Exception e){
				e.printStackTrace();
			}
			break;	
			
		}
	}

	//This pulls the string from the spinner of the object that was selected
	private void getDataFromSpinner() {
		
		String aSelectedAccount = account_list.getSelectedItem().toString();
		if (aSelectedAccount != ""){
			selectedAccount = aSelectedAccount;
		}
		
	}

	
		
	
}