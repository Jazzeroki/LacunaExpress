package com.JazzDevStudio.LacunaExpress;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.JazzDevStudio.LacunaExpress.AccountMan.AccountInfo;
import com.JazzDevStudio.LacunaExpress.AccountMan.AccountMan;
import com.JazzDevStudio.LacunaExpress.JavaLeWrapper.Inbox;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response;
import com.JazzDevStudio.LacunaExpress.LEWrapperResponse.Response.Messages;
import com.JazzDevStudio.LacunaExpress.ListViewRemoval.BackgroundContainer;
import com.JazzDevStudio.LacunaExpress.ListViewRemoval.StableArrayAdapter;
import com.JazzDevStudio.LacunaExpress.MISCClasses.MailFormat;
import com.JazzDevStudio.LacunaExpress.Server.AsyncServer;
import com.JazzDevStudio.LacunaExpress.Server.ServerRequest;
import com.JazzDevStudio.LacunaExpress.Server.serverFinishedListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;


/*
TO DO! Need to have a centralized ArrayList<String> from_data = new ArrayList<String>();
that can be accessed by: SelectMessageActivity2 and StableArrayAdapter so that when the
swipe to delete/ remove works, it also updates that central arraylist, which will in turn
affect an adapter, which will control both the animations and the colors/ letters being
referenced in the image box on the left side of the opaque_view. Details are in
the StableArrayAdapter class.
 */
//This class houses the mail for the inbox and allows the user to browse it, delete it, view it, or compose new
public class SelectMessageActivity2 extends Activity implements serverFinishedListener, OnClickListener, AdapterView.OnItemSelectedListener {
	//for storing the current selected account
	AccountInfo selectedAccount;

    //for storing the selected Message
    String selectedMessageId = "";

	//Contains a list of all Mail objects
	ArrayList <Messages> messages_array = new ArrayList<Messages>();
	MailFormat mf;



	private static final int RESULT_SETTINGS = 1;

	//For debugging
	int counter = 0;
	int counter2 = 0;
	int temp_counter = 0;

	//Preferences
	SharedPreferences prefs;

	//Holds the actual formatted messages as Strings
	ArrayList<String> master_message_list = new ArrayList<String>();

	//Boolean for tracking when messages have been received
	Boolean messagesReceived = false;

	//For storing all account files
	ArrayList<AccountInfo> accounts;

	//ArrayList of display strings for the spinner
	ArrayList<String> user_accounts = new ArrayList<String>();

	//Arraylist of the From field in mail to pass to the StableArrayAdapter
	ArrayList<String> from_data = new ArrayList<String>();

	private Button compose;
	private Spinner account_list, message_tag;

	private String tag_chosen = "Correspondence";
	static final String[] messageTags = {"All", "Correspondence", "Tutorial", "Medal", "Intelligence", "Alert", "Attack", "Colonization", "Complaint", "Excavator", "Mission", "Parliament", "Probe", "Spies", "Trade", "Fissure"};

	//For the ListViewRemovalAnimation
	StableArrayAdapter mAdapter;
	ListView mListView;
	BackgroundContainer mBackgroundContainer;
	boolean mSwiping = false;
	boolean mItemPressed = false;
	HashMap<Long, Integer> mItemIdTopMap = new HashMap<Long, Integer>();
	private static final int SWIPE_DURATION = 250;
	private static final int MOVE_DURATION = 150;

	TextView tv;
	//String sessionID;

	//For accessing in separate class
	Intent iExtra;

	//Constructor
	public SelectMessageActivity2(){
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mail_select);

		//Initialize Variables
		Initialize();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		//Set layout
		setTheBackground();

		//This block populates user_accounts for values to display in the select account spinner
		ReadInAccounts();
		if(accounts.size() == 1){
			selectedAccount = accounts.get(0);
			Log.d("SelectMessage.Initialize", "only 1 account setting as default"+selectedAccount.displayString);
			user_accounts.add(selectedAccount.displayString);
		}
		else{
			for(AccountInfo i: accounts){
				Log.d("SelectMessage.Initialize", "Multiple accounts found, Setting Default account to selected account: "+i.displayString); //
				user_accounts.add(i.displayString);
				Log.d("DISPLAY STRING IS:", i.displayString);
                if(i.defaultAccount)
                    selectedAccount = i;
			}
		}

		//Configure the spinners:
		ArrayAdapter adapter_accounts = new ArrayAdapter(this, android.R.layout.simple_spinner_item, user_accounts);
		adapter_accounts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		account_list.setAdapter(adapter_accounts);

		//ArrayAdapter adapter_message_tag = new ArrayAdapter(this, android.R.layout.simple_spinner_item, message_tag_mail);
		ArrayAdapter adapter_message_tag = new ArrayAdapter(this, android.R.layout.simple_spinner_item, messageTags);
		adapter_message_tag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		message_tag.setAdapter(adapter_message_tag);

		//checking for intent Extra
		iExtra = getIntent();
		if(iExtra.hasExtra("displayString")){
			Log.d("SelectMessageActivity.onCreate", iExtra.getStringExtra("displayString"));
			String a = iExtra.getStringExtra("displayString");
            Log.d("SelectMessage", "Assigning selected account = a from hasExtras a is: "+a);

			//This code sets the default spinner to the one passed in by the intent
				ArrayAdapter name_adapter_1 = (ArrayAdapter) account_list.getAdapter(); //cast to an ArrayAdapter
				int spinnerPosition = name_adapter_1.getPosition(a);
				//set the default according to value
				account_list.setSelection(spinnerPosition);

			selectedAccount = AccountMan.GetAccount(a);
			//Inbox inbox = new Inbox();
            Log.d("SelectMessage.onCreate", "Calling View Inbox");
			String request = Inbox.ViewInbox(selectedAccount.sessionID);

			Log.d("SelecteMessage.Oncreate Request to server", request);
			ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
			AsyncServer s = new AsyncServer();
			s.addListener(this);
			s.execute(sRequest);

		}
		else if (iExtra.hasExtra("chosen_account_string")) {

			String a = iExtra.getStringExtra("chosen_account_string");
			String passed_tag = iExtra.getStringExtra("tag_chosen");

			Log.d("SelectMessage", "Assigning selected account = a from hasExtras a is: "+a);

			//This code sets the default spinner to the one passed in by the intent
			ArrayAdapter name_adapter_1 = (ArrayAdapter) account_list.getAdapter(); //cast to an ArrayAdapter
			int spinnerPosition = name_adapter_1.getPosition(a);
			//set the default according to value
			account_list.setSelection(spinnerPosition);

			//Sets the default tag via the passed in intent
			ArrayAdapter tag_adapter_1 = (ArrayAdapter) message_tag.getAdapter();
			int spinnerPosition1 = tag_adapter_1.getPosition(passed_tag);
			message_tag.setSelection(spinnerPosition1);

			selectedAccount = AccountMan.GetAccount(a);
			//Inbox inbox = new Inbox();
			Log.d("SelectMessage.onCreate", "Calling View Inbox");
			String request = Inbox.ViewInbox(selectedAccount.sessionID);

			Log.d("SelecteMessage.Oncreate Request to server", request);
			ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
			AsyncServer s = new AsyncServer();
			s.addListener(this);
			s.execute(sRequest);

		} else{
			Log.d("AddAccount.onCreate", "Intent is type addAccount");
		}

		//For the ListViewRemovalActivity
		mBackgroundContainer = (BackgroundContainer) findViewById(R.id.listViewBackground);
		android.util.Log.d("Debug", "d=" + mListView.getDivider());
	}

	//Initialize Variables
	private void Initialize() {

		compose = (Button) findViewById(R.id.activity_mail_temp_compose);

		account_list = (Spinner) findViewById(R.id.activity_mail_temp_account_spinner);
		message_tag = (Spinner) findViewById(R.id.activity_mail_temp_message_tag_spinner);

		account_list.setOnItemSelectedListener(this);
		message_tag.setOnItemSelectedListener(this);

		compose.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.activity_mail_list);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	//The next 3 methods work in conjunction to open up your options menu
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

			case R.id.menu_settings:
				Intent i = new Intent(this, UserSettingActivity.class);
				startActivityForResult(i, RESULT_SETTINGS);
				break;
		}
		return true;
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case RESULT_SETTINGS:
				showUserSettings();
				break;
		}
	}
	private void showUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
	}


	//This handles the onClick events like the compose button
	public void onClick(View v) {
		switch (v.getId()){

			//Compose
			case R.id.activity_mail_temp_compose:
				try {
					//Starting to add intent code to launch compose
					Intent openActivity = new Intent(this, ComposeMessageActivity.class);
                    openActivity.putExtra("displayString", selectedAccount.displayString);
					startActivity(openActivity);

				} catch (Exception e){
					e.printStackTrace();
				}
				break;
		}
	}

	//When an item is selected with the spinner
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		//First spinner, account_list
		if (parent == account_list){
			//Get the position within the spinner
			int position0 = account_list.getSelectedItemPosition();
			String word_in_spinner = user_accounts.get(position0);
            Log.d("SelectMessage.onItemSelected assigning selected account", "word in spinner "+ word_in_spinner);

			if (tag_chosen.equalsIgnoreCase("All")){
				//Check the account via the spinner chosen
				selectedAccount = AccountMan.GetAccount(word_in_spinner);
                Log.d("SelectMessage.onItemSelected", "Tag All Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID);
				Log.d("Select Message Activity, SelectedAccount", selectedAccount.userName);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();
				s.addListener(this);
				s.execute(sRequest);
			} else {
				//Check the account via the spinner chosen
				selectedAccount = AccountMan.GetAccount(word_in_spinner);
                Log.d("SelectMessage.onItemSelected", "Tag Word in spinner Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID, tag_chosen);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				Log.d("Select Message Activity, SelectedAccount", selectedAccount.userName);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();
				s.addListener(this);
				s.execute(sRequest);
			}
		}

		//Second spinner, message_tag
		if (parent == message_tag){
			int position0 = message_tag.getSelectedItemPosition();
			String word_in_spinner = messageTags[position0];
			tag_chosen = word_in_spinner; //Sets the tag for mail to the one chosen via the spinner
			Log.d("SelectMessage.onItemSelected assigning Tag", "word in spinner "+ word_in_spinner);

			if (tag_chosen == "All"){
                Log.d("SelectMessage.onItemSelected", "Second Spinner Tag All Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				Log.d("Select Message Activity, SelectedAccount", selectedAccount.userName);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();
				s.addListener(this);
				s.execute(sRequest);
				Log.d("LOOK HERE", "REQUEST SENT 292");
			} else {
                Log.d("SelectMessage.onItemSelected", "Second Spinner word in spinner All Calling View Inbox");
				String request = Inbox.ViewInbox(selectedAccount.sessionID, tag_chosen);
				Log.d("SelectMessage.OnSelectedItem Request to server", request);
				Log.d("Select Message Activity, SelectedAccount", selectedAccount.userName);
				ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
				AsyncServer s = new AsyncServer();
				s.addListener(this);
				s.execute(sRequest);
				Log.d("LOOK HERE", "REQUEST SENT 300");
				Log.d("LOOK HERE", "REQUEST SENT 300");
			}
		}
	}

	//Unused atm
	public void onNothingSelected(AdapterView<?> parent) {
	}

	//Reads in the accounts from the existing objects
	private void ReadInAccounts() {
		Log.d("SelectAccountActivity.ReadInAccounts", "checking for file" + AccountMan.CheckForFile());
		accounts = AccountMan.GetAccounts();
		//Comment out this line and uncomment the line above after purging the excess accounts
		//accounts = AccountMan.PurgeDuplicateAccounts(AccountMan.GetAccounts());
		Log.d("SelectAccountActivity.ReadInAccounts", String.valueOf(accounts.size()));
	}

	//For displaying really long Strings (IE JSON Requests)
	public static void longInfo(String str) {
		if(str.length() > 4000) {
			Log.i("Lengthy String", str.substring(0, 4000));
			longInfo(str.substring(4000));
		} else
			Log.i("Lengthy String", str);
	}

	@Override
	public void onResponseReceived(String reply) {
		longInfo(reply);

		if(!reply.equals("error")) {
			Log.d("Deserializing Response", "Creating Response Object");
			messagesReceived = true;
			//Getting new messages, clearing list first.
			Response r = new Gson().fromJson(reply, Response.class);
			messages_array.clear();
			messages_array = r.result.messages;

			counter++;
			loadMessagesIntoArray();
			Log.d("The Counter is at:", Integer.toString(counter));

			mAdapter = new StableArrayAdapter(this,R.layout.opaque_text_view, master_message_list, mTouchListener, from_data);
			mListView.setAdapter(mAdapter);
		} else {
			Log.d("Error with Reply", "Error in onResponseReceived()");
		}
	}

	public void loadMessagesIntoArray(){
		master_message_list.clear(); //Clears list from before, just in case
		master_message_list = dataConvertForMasterList(messages_array); //This populates the data from the messages_array object
		from_data.clear(); //Clears list from before, just in case
		from_data = dataConvertForFromArray(messages_array);
		Log.d("Number of messages received", Integer.toString(master_message_list.size()));
	}

	//Updates the arraylist for the master message list (all messages in readable format)
	private ArrayList<String> dataConvertForMasterList(ArrayList<Response.Messages> message_array_list){
		ArrayList<String> all_returned_data = new ArrayList<String>();

		String str1, str2, str3, str4, longString;
		str1 = "";
		str2 = "";
		str3 = "";
		str4 = "";
		longString = "";

		ArrayList<String> body_preview_data = new ArrayList<String>();
		ArrayList<String> subject_data = new ArrayList<String>();
		//ArrayList<String> from_data = new ArrayList<String>();  //Declared above already. Leaving this in for testing purposes
		ArrayList<String> date_data = new ArrayList<String>();

		try{
			body_preview_data = mf.fBody_Preview(message_array_list);
			subject_data = mf.fSubject(message_array_list);
			from_data = mf.fFrom(message_array_list);
			date_data = mf.fDate(message_array_list);
		} catch (NullPointerException e){
			e.printStackTrace();
		}

		//For loop to combine all data into readable format for message preview list
		for (int i = 0; i<message_array_list.size(); i++){
			str1 = date_data.get(i);
			str2 = from_data.get(i);
			str3 = subject_data.get(i);
			str4 = body_preview_data.get(i)+ "...";

			//Adjust the date string to remove the +0000. First 20 characters (to eliminate time zone declaration)
			String adjust_date = str1.substring(0, Math.min(str1.length(), 20));

			longString = adjust_date + "\n" + str2 + "\n" + str3 + "\n" + str4;

			all_returned_data.add(longString);
		}
		return all_returned_data;
	}

	//Updates the arraylist for the from list (only the from / senders)
	private ArrayList<String> dataConvertForFromArray(ArrayList<Response.Messages> message_array_list){
		ArrayList<String> all_returned_data = new ArrayList<String>();

		String str1 = "";

		try{
			from_data = mf.fFrom(message_array_list);
		} catch (NullPointerException e){
			e.printStackTrace();
		}

		//For loop to combine all data into readable format for message preview list
		for (int i = 0; i<message_array_list.size(); i++){
			str1 = from_data.get(i);
			all_returned_data.add(str1);
		}
		return all_returned_data;
	}
	//This method deletes mail that is swiped away by the user
	private void deleteMail(long position0){
		int position = (int) position0;

		ArrayList<String> messages_to_delete = new ArrayList<String>();
		messages_to_delete.clear(); //TESTING
		messages_to_delete.add(Integer.toString(messages_array.get(position).id));
		String str = Integer.toString(messages_array.get(position).id);
		counter2++;
		Log.d("ID is at:", str);
		Inbox inb = new Inbox();

		String request = inb.TrashMessages(2, selectedAccount.sessionID, messages_to_delete);

        Log.d("SelectMessage.DeleteMail", "Request String"+request);
		ServerRequest sRequest = new ServerRequest(selectedAccount.server, Inbox.url, request);
		AsyncServer s = new AsyncServer();
		//s.addListener(this);
		try {
			s.execute(sRequest);
		} catch (Exception e){
			e.printStackTrace();
		}

	}

	//The following methods are for the ListViewRemovalAnimation
	/**
	 * Handle touch events to fade/move dragged items as they are swiped out
	 */
	private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

		float mDownX;
		private int mSwipeSlop = -1;

		@Override
		public boolean onTouch(final View v, MotionEvent event) {
			if (mSwipeSlop < 0) {
				mSwipeSlop = ViewConfiguration.get(SelectMessageActivity2.this).
						getScaledTouchSlop();
			}
			switch (event.getAction()) {

				//Motion down: the user has pressed the screen down
				case MotionEvent.ACTION_DOWN:
					if (mItemPressed) {
						// Multi-item swipes not handled
						return false;
					}
					mItemPressed = true;
					mDownX = event.getX();
					break;

				//Canceled the move event
				case MotionEvent.ACTION_CANCEL:
					v.setAlpha(1);
					v.setTranslationX(0);
					mItemPressed = false;
					break;

				//When the item has been moved
				case MotionEvent.ACTION_MOVE:
				{
					//First get their initial position
					float x = event.getX() + v.getTranslationX();
					float deltaX = x - mDownX;
					float deltaXAbs = Math.abs(deltaX);
					if (!mSwiping) {
						//If they are swiping one direction or another, this code handles it
						if (deltaXAbs > mSwipeSlop) {
							mSwiping = true;
							//This line below prevents the listView from scrolling while the user is swiping in a direction
							mListView.requestDisallowInterceptTouchEvent(true);
							//Detail about what will be shown behind the item as it is shown away
							mBackgroundContainer.showBackground(v.getTop(), v.getHeight());
						}
					}
					//If it is swiping, go back and forth depending on how much they move their finger
					if (mSwiping) {
						v.setTranslationX((x - mDownX));
						v.setAlpha(1 - deltaXAbs / v.getWidth()); //Inverse of wherever they started.
					}
				}
				break;

				//When the user releases their finger
				case MotionEvent.ACTION_UP:
				{
					// User let go - figure out whether to animate the view out, or back into place
					if (mSwiping) {
						float x = event.getX() + v.getTranslationX();
						float deltaX = x - mDownX;
						float deltaXAbs = Math.abs(deltaX);
						float fractionCovered;
						float endX;
						float endAlpha;
						final boolean remove;
						if (deltaXAbs > v.getWidth() / 4) {
							// Greater than a quarter of the width - animate it out
							fractionCovered = deltaXAbs / v.getWidth();
							endX = deltaX < 0 ? -v.getWidth() : v.getWidth();
							endAlpha = 0;
							remove = true;
						} else {
							// Not far enough - animate it back, IE hit it by mistake
							fractionCovered = 1 - (deltaXAbs / v.getWidth());
							endX = 0;
							endAlpha = 1;
							remove = false;
						}
						// Animate position and alpha of swiped item
						// NOTE: This is a simplified version of swipe behavior, for the
						// purposes of this demo about animation. A real version should use
						// velocity (via the VelocityTracker class) to send the item off or
						// back at an appropriate speed.
						long duration = (int) ((1 - fractionCovered) * SWIPE_DURATION);
						//This is similar to line 115 except it prevents the list view from working while the animation is happening
						mListView.setEnabled(false);
						v.animate().setDuration(duration).
								alpha(endAlpha).translationX(endX). //Uses a view property animator (Android 3.1). Animate it to the endx (all the way off or on the screen)
								//If we have to write for API 14 instead of 16, we can replace withEndAction with listener on the viewProperty animator
								withEndAction(new Runnable() {
							//This animates the removal once the swipe has occurred.
							public void run() {
								// Restore animated values
								v.setAlpha(1);
								v.setTranslationX(0);
								//If removed fromt the list, animate its removal
								if (remove) {
									animateRemoval(mListView, v);
								} else {
									mBackgroundContainer.hideBackground();
									mSwiping = false;
									mListView.setEnabled(true);
								}
							}
						});
					} else {
						//CODE GOES HERE FOR ON CLICK OR ITEM PRESSED
						callTheOnClick(mListView, v);
					}
				}
				mItemPressed = false;
				break;
				default:
					return false;
			}
			return true;
		}
	};

	//This method is called once the user clicks on one of the listView icons to open
	private void callTheOnClick(ListView listview, View viewToRemove) {
		int firstVisiblePosition = listview.getFirstVisiblePosition();
		//For all children currently in the listview, track where items are and store that data in a hashmap
		for (int i = 0; i < listview.getChildCount(); ++i) {
			View child = listview.getChildAt(i);
			if (child == viewToRemove) {
				//Gets the position of the clicked icon within the lis
				int position = firstVisiblePosition + i;

				int temp = messages_array.get(i).id;

				String id_to_pass = Integer.toString(temp);
                Log.d("SelectMessage.onTheClick", "MessageID"+id_to_pass);

				Intent openActivity = new Intent (this, ReadMessageActivity.class);
				openActivity.putExtra("message_id_passed", id_to_pass);
				openActivity.putExtra("displayString", selectedAccount.displayString);
				startActivity(openActivity);
			}
		}
	}


	/**
	 * This method animates all other views in the ListView container (not including ignoreView)
	 * into their final positions. It is called after ignoreView has been removed from the
	 * adapter, but before layout has been run. The approach here is to figure out where
	 * everything is now, then allow layout to run, then figure out where everything is after
	 * layout, and then to run animations between all of those start/end positions.
	 */
	private void animateRemoval(final ListView listview, View viewToRemove) {
		int firstVisiblePosition = listview.getFirstVisiblePosition();
		long position_to_delete = 0;
		//For all children currently in the listview, track where items are and store that data in a hashmap
		for (int i = 0; i < listview.getChildCount(); ++i) {
			View child = listview.getChildAt(i);


			if (child == viewToRemove){
				int position = firstVisiblePosition + i;
				long itemID = mAdapter.getItemId(position);
				position_to_delete = itemID;

			}


			if (child != viewToRemove) {
				int position = firstVisiblePosition + i;
				long itemId = mAdapter.getItemId(position);

				mItemIdTopMap.put(itemId, child.getTop());

			}

		}
		// Delete the item from the adapter (Runs onNotifyDataSetChange to remove it from the list)
		int position = mListView.getPositionForView(viewToRemove);
		deleteMail(position_to_delete);

		mAdapter.remove(mAdapter.getItem(position));
		//deleteMail(mListView, viewToRemove);
		//master_message_list.remove(position);
		//messages_array.remove(position);//////////////////////////////////////////////////////////////////

		//Gets the view from the Stable Array Adapter
		final ViewTreeObserver observer = listview.getViewTreeObserver();
		observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			//This is called BEFORE draws happen
			public boolean onPreDraw() {
				//Remove the listener after the 1 frame
				observer.removeOnPreDrawListener(this);
				boolean firstAnimation = true;
				int firstVisiblePosition = listview.getFirstVisiblePosition();
				//Cycle through all current children of the list frame
				for (int i = 0; i < listview.getChildCount(); ++i) {
					final View child = listview.getChildAt(i);
					int position = firstVisiblePosition + i;
					long itemId = mAdapter.getItemId(position);
					Integer startTop = mItemIdTopMap.get(itemId);
					//Where is the child now, get the top position
					int top = child.getTop();
					if (startTop != null) { //This means the view used to be somewhere else in the container
						//If the child was somewhere else in the container, run an animation to move it
						if (startTop != top) {
							//Set a Y Value and animate to it
							int delta = startTop - top;
							child.setTranslationY(delta);
							child.animate().setDuration(MOVE_DURATION).translationY(0);
							//If this is the first time it's been run, restore values
							if (firstAnimation) {
								child.animate().withEndAction(new Runnable() {
									public void run() {
										mBackgroundContainer.hideBackground();
										mSwiping = false;
										mListView.setEnabled(true);
									}
								});
								firstAnimation = false;
							}
						}
					} else {
						// Animate new views along with the others. The catch is that they did not
						// exist in the start state, so we must calculate their starting position
						// based on neighboring views.
						int childHeight = child.getHeight() + listview.getDividerHeight();
						startTop = top + (i > 0 ? childHeight : -childHeight);
						int delta = startTop - top;
						child.setTranslationY(delta); //Again, set Y value to where it is going
						child.animate().setDuration(MOVE_DURATION).translationY(0);
						if (firstAnimation) {
							child.animate().withEndAction(new Runnable() {
								public void run() {
									mBackgroundContainer.hideBackground();
									mSwiping = false;
									mListView.setEnabled(true);
								}
							});
							firstAnimation = false;
						}
					}
				}
				//When finished, clear the item IDs associated with positions
				mItemIdTopMap.clear();
				return true;
			}
		});
	}


	//Sets the background as per user preference settings
	private void setTheBackground(){
		String user_choice = prefs.getString("pref_background_choice","blue_glass");
		Log.d("User Background Choice", user_choice);
		LinearLayout layout = (LinearLayout) findViewById(R.id.activity_mail_select_layout);
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
