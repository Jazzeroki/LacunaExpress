package com.jazzdevstudio.lacunaexpress;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.SelectMessageActivity2;

public class Utilities extends ActionBarActivity implements View.OnClickListener {
    Button planet, station, mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilities);
        Initialize();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_utilities, menu);
        return true;
    }
    private void Initialize(){
        planet = (Button) findViewById(R.id.buttonPlanetUtilities);
        station = (Button) findViewById(R.id.buttonPlanetUtilities);
        mail = (Button) findViewById(R.id.buttonPlanetUtilities);

        planet.setOnClickListener(this);
        station.setOnClickListener(this);
        mail.setOnClickListener(this);
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
    /*
    void intentions(){
        Intent intent = getIntent();

        // Get the extras and fetching message to display
        Bundle extras = intent.getExtras();
        if (extras != null) {

            messageID = extras.getString("message_id_passed");
            Log.d("messageidpassed", extras.getString("message_id_passed"));
            account = com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan.GetAccount(extras.getString("displayString"));
            //Inbox inbox = new Inbox();
            String request = com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox.ReadMessage(account.sessionID, messageID);
            Log.d("request", request);
            Log.d("ReadMessageActivity.onCreate", "MessageID "+messageID);
            com.JazzDevStudio.LacunaExpress.Server.ServerRequest sRequest = new com.JazzDevStudio.LacunaExpress.Server.ServerRequest(account.server, com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox.url, request);
            com.JazzDevStudio.LacunaExpress.Server.AsyncServer s = new com.JazzDevStudio.LacunaExpress.Server.AsyncServer();
            s.addListener(this);
            s.execute(sRequest);

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

        }
    }
    */
    @Override
    public void onClick(View v) {
        Intent openActivity;
        switch(v.getId()){
            case R.id.buttonPlanetUtilities:
                openActivity = new Intent (this, PlanetOptions.class);
                openActivity.putExtra("displayString", getIntent().getExtras().getString("displayString"));
                startActivity(openActivity);
                finish();
                break;
            case R.id.buttonStationUtilities:
                //openActivity = new Intent (this, PlanetOptions.class);
                //openActivity.putExtra("displayString", getIntent().getExtras().getString("displayString"));
                //startActivity(openActivity);
                //finish();
                break;
            case R.id.buttonMailUtilities:
                openActivity = new Intent (this, SelectMessageActivity2.class);
                openActivity.putExtra("displayString", getIntent().getExtras().getString("displayString"));
                startActivity(openActivity);
                finish();
                break;
        }
    }
}
