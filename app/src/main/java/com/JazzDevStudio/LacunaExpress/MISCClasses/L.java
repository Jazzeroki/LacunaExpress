package com.JazzDevStudio.LacunaExpress.MISCClasses;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

//This is a class for shortcuts of code. Allows for reusability
public class L extends Activity{

	//Constructor for non-Static classes
	public L(){
	}
	//Simple log statement. Defaults to Log if no tag string is passed
	public static void m(String message){
		Log.d("Log", message);
	}
	
	//Simple log statement. Takes in the tag string
	public static void m(String tag, String message){
		Log.d(tag, message);
	}
	
	//Toast activity. LONG toast
	public static void makeToast(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	//Toast activity. SHORT toast
	public static void makeToastShort(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	//Calls finish, which ends the activity running. Useful so the app stack doesn't grow too large
	public void callFinishAfter(int seconds, Context aContext){
		final Context context = aContext;
		L.m("Seconds total: " + 1000*seconds);
		Handler handler = new Handler(); 
		handler.postDelayed(new Runnable() { 
			 public void run() { 
				 
				 //
				 finish();
				 L.m("Finish called");
				 //
			 } 
		}, (1000*seconds));
	}
	
	//Same as callFinishAfter but adds context if run within a thread
	public void callFinishAfter(Context context, int seconds){
		L.m("Seconds total: " + 1000*seconds);
		Handler handler = new Handler(); 
		handler.postDelayed(new Runnable() { 
			 public void run() { 
				 
				 //
				 finish();
				 L.m("Finish called");
				 //
			 } 
		}, (1000*seconds));
	}

	//For displaying really long Strings (IE JSON Requests)
	public static void longInfo(String str) {


		if(str.length() > 4000) {
			Log.d("Lengthy String", str.substring(0, 4000));
			longInfo(str.substring(4000));
		} else
			Log.d("Lengthy String", str);
	}


	//
}
