package com.JazzDevStudio.LacunaExpress;

import java.util.ArrayList;
import com.google.gson.Gson;
import AccountMan.AccountInfo;
import JavaLEWrapper.Empire;
import JavaLEWrapper.Inbox;
import LEWrapperResponse.Response;
import Server.AsyncServer;
import Server.ServerRequest;
import Server.serverFinishedListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import LEWrapperResponse.Response.Messages;


public class SelectMessageActivity extends Activity implements serverFinishedListener, OnClickListener {
	AccountInfo selectedAccount;
	ArrayList <Messages> messages = new ArrayList<Messages>();
	//String sessionID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_temp);
        
        //checking for intent Extra
        Intent i = getIntent();
        if(i.hasExtra("dispalyString")){
        	Log.d("SelectMessageActivity.onCreate", i.getStringExtra("displayString"));
        	String a = i.getStringExtra("displayString");
        	selectedAccount = AccountMan.AccountMan.GetAccount(a);
        	//Inbox inbox = new Inbox();
        	String request = Inbox.ViewInbox(selectedAccount.sessionID, Inbox.MessageTags.Correspondence.toString());
        	
        	Log.d("SelecteMessage.Oncreate Request to server", request);
            ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
            AsyncServer s = new AsyncServer();
            s.addListener(this);
            s.execute(sRequest);
        	
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
    	Log.d("SelectMessage.onResponse Recieved", reply);
    	
        if(!reply.equals("error")) {
        	Log.d("Deserializing Response", "Creating Response Object");
            
        	//Getting new messages, clearing list first.
            Response r = new Gson().fromJson(reply, Response.class);
            messages.clear();
            messages = r.result.messages;

            //Don't think I want to call finish here because not wanting to end activity.
            //finish();
        }
	}
}
