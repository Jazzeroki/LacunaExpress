package com.JazzDevStudio.LacunaExpress.Widget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.Splash;

/**
 * Created by PatrickSSD2 on 1/3/2015.
 */
public class MailWidgetConfig extends Activity implements View.OnClickListener {

	Button b1;

	AppWidgetManager awm;
	Context c;
	int awID;

	//Shared Preferences
	SharedPreferences prefs;

	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_mail_config);
		Initialize();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		setTheBackground();



	}

	private void Initialize() {
		b1 = (Button) findViewById(R.id.button_widget_config);
		b1.setOnClickListener(this);

		c = MailWidgetConfig.this;


		//An intent is opening this class, therefore, must make one
		Intent i = getIntent();

		//Create a bundle since info is being passed around (Which app launched this activity)
		Bundle extras = i.getExtras();

		//As long as the extras had something, setup the app widget id
		if (extras != null){
			//Get an ID and pass it in. IE, a way to checking which widget activated this class
			awID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			//This returns 1 App widget ID
		} else {
			//In case something gets a-broken!
			finish();
		}

		//
		awm = AppWidgetManager.getInstance(c);
	}


	@Override
	public void onClick(View v) {
		//Set the string = to the info getText

		//Setup a remoteview referring to the context (Param1) and relating to the widget (Param2)
		RemoteViews v1 = new RemoteViews(c.getPackageName(), R.layout.widget_mail_layout);

		String e = "TESTING";
		//Setting the remote view (remote meaning on the homescreen widget) to the text_view
		v1.setTextViewText(R.id.text_view_config_input, e);

		//IMPORTANT! This intent opens the class when clicked
		Intent intento = new Intent(c, Splash.class);

		//A pending intent. Apparently needed for widgets
		PendingIntent pendingintento = PendingIntent.getActivity(c, 0, intento, 0);

		//Set the onClickListener for the button
		v1.setOnClickPendingIntent(R.id.button_widget_open, pendingintento);


		//Update the widget with the remote view
		awm.updateAppWidget(awID, v1);

		//Lastly, need to set a result
		Intent result = new Intent();

		//Updating the ID that is being called
		result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, awID);

		//Confirm the result works then set it
		setResult(RESULT_OK, result);

		//We want this to finish when the button is clicked
		finish();

	}

	private void setTheBackground() {
		String user_choice = prefs.getString("pref_background_choice","blue_glass");
		Log.d("User Background Choice", user_choice);
		LinearLayout layout = (LinearLayout) findViewById(R.id.activity_add_account_layout);
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
