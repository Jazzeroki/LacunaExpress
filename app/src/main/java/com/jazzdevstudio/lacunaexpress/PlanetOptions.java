package com.jazzdevstudio.lacunaexpress;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.JazzDevStudio.LacunaExpress.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PlanetOptions extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo account;
    Spinner planetList;
    ArrayList<String> planetNamesForSpinner = new ArrayList<>();
    ArrayAdapter<String> planetAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_options);
        Initialize();
    }
    void Initialize(){
        planetList = (Spinner)findViewById(R.id.spinnerPlanetActivityPlanetSelection);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
          account = com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan.GetAccount(extras.getString("displayName"));
        }
        if(account != null){
            planetNamesForSpinner = account.colonies.values();
            planetList.onItemSelectedListener(this);
            planetAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, planetNamesForSpinner);
            planetList.setAdapter(planetAdapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planet_options, menu);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
