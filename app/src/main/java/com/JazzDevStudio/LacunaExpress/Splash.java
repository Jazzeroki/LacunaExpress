package com.JazzDevStudio.LacunaExpress;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.LinearLayout;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.MISCClasses.IsMyActivityRunning;
import com.JazzDevStudio.LacunaExpress.MISCClasses.sessionRefresh;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Splash extends Activity {

	//For checking internet connection
	protected NetworkInfo networkInfo;
	//For the intro music / song playing in the background
	MediaPlayer ourIntroSong;

	SharedPreferences prefs;

	private boolean do_we_have_network_connection;

	@Override
	protected void onCreate(Bundle inputVariableToSendToSuperClass) {

		super.onCreate(inputVariableToSendToSuperClass);
		setContentView(R.layout.splash);
		Initialize();
		//Setting background
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setTheBackground();



		//Checking for background music
		boolean music_on = prefs.getBoolean("pref_splash_screen_music", true);

		//Default to no network connection
		do_we_have_network_connection = false;
		//Check if there is internet connection
		do_we_have_network_connection = haveNetworkConnection();

		//DO SOMETHING HERE IF NO INTERNET CONNECTION HAPPENS


		if (music_on == true){
			ourIntroSong = MediaPlayer.create(Splash.this, R.raw.cinematic_impact);
		} else{
			ourIntroSong = MediaPlayer.create(Splash.this, R.raw.no_sound);
		}

		ourIntroSong.start();


		Thread timer = new Thread() {
			public void run() {
				try {
					//After creating the load file code need to setup if fill not found
					//start add account, otherwise if 1 account open account, if multiple accounts
					//open account selection
					if (AccountMan.CheckForFile()) {
                        ArrayList<AccountInfo> a = AccountMan.GetAccounts();
                        int x = a.size();
                        if (x <= 0) {
                            sleep(3100);
                        } else if (x == 1) {
                            sleep(1600);
                        } else if (x >= 2) {
                            sleep(100);
                        }

                        try {

                            if (a.size() > 0) {
                                sessionRefresh r = new sessionRefresh();
                                r.execute("i");
                                Thread.sleep((1500 * a.size()));
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
						Intent intent = new Intent(Splash.this, SelectAccount.class);
						startActivity(intent);
						finish();
					} else {
						sleep(3100);
						Intent intent = new Intent(Splash.this, AddAccount.class);
						startActivity(intent);
						finish();
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		};

		timer.start();

		//This is a failsafe built in-case the intents never fire
		Thread timer2 = new Thread() {
			public void run() {
				try {
					ArrayList<AccountInfo> a = AccountMan.GetAccounts();
					int x = a.size();
					int y = ((2000*x) + 5000); //Just in case they have a TON of accounts
					sleep(y);
					boolean isIt = true;
					isIt = IsMyActivityRunning.isActivityVisible(); //Should set the boolean to false if the class has been set to pause
					if (isIt == true){
						Log.e("SPLASH SCREEN", "Error, things never loaded and force moved to select account");
						Intent intent = new Intent(Splash.this, SelectAccount.class);
						startActivity(intent);
						finish();
					} else {
						Log.d("SPLASH SCREEN", "Calling finish() on Splash screen");
						finish();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						sleep(4100);
						boolean isIt = true;
						isIt = IsMyActivityRunning.isActivityVisible(); //Should set the boolean to false if the class has been set to pause
						if (isIt == true){
							Log.e("SPLASH SCREEN", "Error, things never loaded and force moved to select account");
							Intent intent = new Intent(Splash.this, SelectAccount.class);
							startActivity(intent);
							finish();
						} else {
							Log.d("SPLASH SCREEN", "Calling finish() on Splash screen");
							finish();
						}
					}catch (Exception e) {
						e.printStackTrace();

					}

				}
			}
		};
		timer2.start();
	}

	private void Initialize() {

	}

	@Override
	protected void onPause() {
		super.onPause();
		
		//This kills the music so it isn't carried over between splash screens
		ourIntroSong.release();
		
		//Destroys the class when it goes on pause. Not ideal for most programs, but, for a splash screen, this works fine as we don't want it to show up again. 
		finish(); 
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

	private boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}

	private void setTheBackground(){
		String user_choice = prefs.getString("pref_background_choice","blue_glass");
		LinearLayout layout = (LinearLayout) findViewById(R.id.activity_splash_layout);
		if (user_choice.equalsIgnoreCase("blue_glass")){
			layout.setBackground(getResources().getDrawable(R.drawable.blue_glass));
		} else if (user_choice.equalsIgnoreCase("blue_oil_painting")){
			layout.setBackground(getResources().getDrawable(R.drawable.blue_oil_painting));
		} else if (user_choice.equalsIgnoreCase("stained_glass_blue")){
			layout.setBackground(getResources().getDrawable(R.drawable.stained_glass_blue));
		} else if (user_choice.equalsIgnoreCase("light_blue_boxes")){
			layout.setBackground(getResources().getDrawable(R.drawable.light_blue_boxes));
		} else if (user_choice.equalsIgnoreCase("light_silver_background")){
			layout.setBackground(getResources().getDrawable(R.drawable.light_silver_background));
		} else if (user_choice.equalsIgnoreCase("simple_grey")){
			layout.setBackground(getResources().getDrawable(R.drawable.simple_grey));
		} else if (user_choice.equalsIgnoreCase("simple_apricot")){
			layout.setBackground(getResources().getDrawable(R.drawable.simple_apricot));
		} else if (user_choice.equalsIgnoreCase("simple_teal")){
			layout.setBackground(getResources().getDrawable(R.drawable.simple_teal));
		} else if (user_choice.equalsIgnoreCase("xmas")){
			layout.setBackground(getResources().getDrawable(R.drawable.xmas));
		} else if (user_choice.equalsIgnoreCase("lacuna_logo")){
			layout.setBackground(getResources().getDrawable(R.drawable.lacuna_logo));
		} else {
		}
	}
}
