package com.JazzDevStudio.LacunaExpress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;

import java.util.ArrayList;
import java.util.Collections;

public class PlanetOptions extends Activity implements AdapterView.OnItemSelectedListener {
    AccountInfo account;
    Spinner planetList;
    ArrayList<String> planetNamesForSpinner = new ArrayList<String>();
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
          account = AccountMan.GetAccount(extras.getString("displayName"));
        }
        if(account != null){
            planetNamesForSpinner = (ArrayList<String>)account.colonies.values();
            Collections.sort(planetNamesForSpinner);
            //planetList.setOnItemSelectedListener(this);
            planetAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, planetNamesForSpinner);
            planetList.setAdapter(planetAdapter);
            planetList.setOnItemSelectedListener(this);
            //String text_display_string = account_list.getSelectedItem().toString();

            /*
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

		//First spinner, account_list
		if (parent == account_list){
			//Get the position within the spinner
			int position0 = account_list.getSelectedItemPosition();
			word_in_spinner = Integer.toString(account_list.getSelectedItemPosition());
			Log.d("Word in the spinner is: ", word_in_spinner);
		}
	}
             */
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
