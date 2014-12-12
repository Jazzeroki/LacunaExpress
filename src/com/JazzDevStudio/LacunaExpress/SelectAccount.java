package com.JazzDevStudio.LacunaExpress;
 
import java.util.ArrayList;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;

import AccountMan.AccountInfo;
import AccountMan.AccountMan;
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
	Button modifyAccount, addAccount;
	Spinner account_list;
	
	private static final int RESULT_SETTINGS = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);
        Log.d("Select Account", "Select Account oncreate");
        
        ArrayList<String> user_accounts = new ArrayList<String>();
        
        Initialize();
        ReadInAccounts();
        for(AccountInfo i: accounts){
        	Log.d("Select Account", i.userName);
        	user_accounts.add(i.userName);
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
    	
    	modifyAccount.setOnClickListener(this);
    	addAccount.setOnClickListener(this);
		
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
		
		switch (v.getId()){
		
		
		//Modify Account
		case R.id.select_account_modify:
			try {
				//Starting to add intent code to launch addAccount as a modified account
				Intent openActivity = new Intent(this, SelectAccount.class);
				openActivity.putExtra("username", "");
				openActivity.putExtra("server", "");
				openActivity.putExtra("defaultAccount", "");
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
			
		}
	}

	
		
	
}