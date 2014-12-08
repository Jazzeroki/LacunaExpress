package com.JazzDevStudio.LacunaExpress;
 
import java.util.ArrayList;
 
import AccountMan.AccountInfo;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import AccountMan.*;
 
public class SelectAccount extends Activity implements OnClickListener {
	ArrayList<AccountInfo> accounts;
	Button login, modifyAccount, addAccount;
	
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
 
    	login = (Button) findViewById(R.id.select_account_login);
    	modifyAccount = (Button) findViewById(R.id.select_account_modify);
    	addAccount = (Button) findViewById(R.id.select_account_add);
    	
    	login.setOnClickListener(this);
    	modifyAccount.setOnClickListener(this);
    	addAccount.setOnClickListener(this);
		
	}
 
 
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_account, menu);
        Log.d("Select Account", "Select Account on create options");
        
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
 
	@Override
	public void onClick(View v) {
		
		switch (v.getId()){
		
		//Login
		case R.id.select_account_login:
			
			try {
				
				
				
			} catch (Exception e){
				e.printStackTrace();
			}
			break;
		
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