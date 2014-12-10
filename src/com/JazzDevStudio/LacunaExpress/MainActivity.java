package com.JazzDevStudio.LacunaExpress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import AccountMan.AccountInfo;
import AccountMan.AccountMan;
import MISCClasses.sessionRefresh;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        //This adds a text file to the phone and then reads it for testing purpose
        File file = this.getFileStreamPath("test123.txt");
        if(file.exists()){
        	Log.d("The File", "Exists");
        } else {
	        try {
	        	writeToFile("TESTING JAZZ! TESTING");
	        	Log.d("The File", "Has Been Written");
	        	String test = readFromFile();
	        	Log.d("READ FILE", test);
	        } catch (Exception e){
	        	e.printStackTrace();
	        }
        }
        //End adding text file
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //This block is setup to refresh all sessions on loading.  Pauses main while the accounts are refreshed. 
            try {
            	ArrayList<AccountInfo> a = AccountMan.GetAccounts();
            	if(a.size()>0){
            		sessionRefresh r = new sessionRefresh();
            		r.execute("i");
            		Thread.sleep((1500 * a.size()));
            	}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            //After creating the load file code need to setup if fill not found
            //start add account, otherwise if 1 account open account, if multiple accounts
            //open account selection            
            if (AccountMan.CheckForFile()) {
                Intent intent = new Intent(getActivity(), SelectAccount.class);
                startActivity(intent);
                return rootView;
            }
            
            else {
                Intent intent = new Intent(getActivity(), AddAccount.class);
                startActivity(intent);
                return rootView;
            }  
        }
    }
    
    //Write a file to disk
    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("test123.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } 
    }
    
    //Read a file from disk
    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("test123.txt");
            InputStream is = openFileInput("test");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }    
}
