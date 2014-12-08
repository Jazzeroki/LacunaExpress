package com.JazzDevStudio.LacunaExpress;
 
import java.util.ArrayList;
 
import AccountMan.AccountInfo;
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
import AccountMan.*;
import MISCClasses.UserSettingActivity;
 
public class SelectAccount extends Activity implements OnClickListener {
	ArrayList<AccountInfo> accounts;
	Button modifyAccount, addAccount;
	
	private static final int RESULT_SETTINGS = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);
        Log.d("Select Account", "Select Account oncreate");
        Initialize();
        ReadInAccounts();
        for(AccountInfo i: accounts){
        	Log.d("Select Account", i.userName);
        }
    }
 
    private void ReadInAccounts() {
		Log.d("SelectAccountActivity.ReadInAccounts", "checking for file" + AccountMan.CheckForFile());
		accounts = AccountMan.GetAccounts();
		Log.d("SelectAccountActivity.ReadInAccounts", String.valueOf(accounts.size()));
		
	}
 
	//Initialize Variables
    private void Initialize() {
 
    	modifyAccount = (Button) findViewById(R.id.select_account_modify);
    	addAccount = (Button) findViewById(R.id.select_account_add);
    	
    	modifyAccount.setOnClickListener(this);
    	addAccount.setOnClickListener(this);
		
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
			
			
				
			} catch (Exception e){
				e.printStackTrace();
			}
			break;
			
		//Add Account
		case R.id.select_account_add:
			try {
			
			
				
			} catch (Exception e){
				e.printStackTrace();
			}
			break;			
			
		}
	}
	
		
	
}