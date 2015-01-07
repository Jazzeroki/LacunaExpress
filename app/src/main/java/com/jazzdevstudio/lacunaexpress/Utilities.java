package com.jazzdevstudio.lacunaexpress;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.JazzDevStudio.LacunaExpress.R;

public class Utilities extends ActionBarActivity implements View.OnClickListener {
    Button planet, station, mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilities);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonPlanetUtilities:
                break;
            case R.id.buttonStationUtilities:
                break;
            case R.id.buttonMailUtilities:
                break;
        }
    }
}
