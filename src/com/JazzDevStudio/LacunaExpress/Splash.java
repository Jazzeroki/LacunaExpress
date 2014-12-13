package com.JazzDevStudio.LacunaExpress;

import MISCClasses.L;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {

MediaPlayer ourIntroSong; 
	
	@Override
	protected void onCreate(Bundle inputVariableToSendToSuperClass) {

		super.onCreate(inputVariableToSendToSuperClass);
		setContentView(R.layout.splash);
		
		ourIntroSong = MediaPlayer.create(Splash.this, R.raw.cinematic_impact);
		ourIntroSong.start();
		
		Thread timer = new Thread()
		{
			public void run()
			{
				try
				{
					//In Milliseconds, set to 3 seconds at this point
					sleep(3000);
				} catch (InterruptedException e01) {
					String error_in_splash = e01.toString(); //For Debugging purposes
					e01.printStackTrace();
				} finally {

					
					//Testing this instead of a popup window
					Intent openMain = new Intent(Splash.this, MainActivity.class);
					//Intent openMain = new Intent(Splash.this, com.JazzDevStudio.LacunaExpress.ListViewRemoval.ListViewRemovalAnimation.class); //Testing something
					startActivity(openMain);
				}
			}
		};

		timer.start();
		
		callFinishAfter(3);
	}
	
	public void callFinishAfter(int seconds){
		L.m("Seconds total: " + 1000*seconds);
		Handler handler = new Handler(); 
		handler.postDelayed(new Runnable() { 
			 public void run() { 
				 
				 //
				 finish();
				 L.m("Finish called in Splash");
				 //
			 } 
		}, (1000*seconds));
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		//This kills the music so it isn't carried over between splash screens
		ourIntroSong.release();
		
		//Destroys the class when it goes on pause. Not ideal for most programs, but, for a splash screen, this works fine as we don't want it to show up again. 
		finish(); 
	}
}
