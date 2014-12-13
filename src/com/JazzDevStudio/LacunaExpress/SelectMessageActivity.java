package com.JazzDevStudio.LacunaExpress;


import Server.serverFinishedListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


public class SelectMessageActivity extends Activity implements serverFinishedListener, OnClickListener {
	String selectedAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_temp);
        
        //checking for intent Extra
        Intent i = getIntent();
        if(i.hasExtra("dispalyString")){
        	Log.d("SelectMessageActivity.onCreate", i.getStringExtra("displayString"));
        	selectedAccount = i.getStringExtra("displayString");
        }
        else{
        	Log.d("AddAccount.onCreate", "Intent is type addAccount");
        }
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


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onResponseRecieved(String reply) {
		// TODO Auto-generated method stub
		
	}
}
