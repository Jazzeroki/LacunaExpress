package com.JazzDevStudio.LacunaExpress.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//Temporary database adapter and helper class
public class TEMPDatabaseAdapter {

	//Helper object
	DatabaseHelper helper;

	private SQLiteDatabase database;

	//Constructor
	public TEMPDatabaseAdapter(Context context){
		helper = new DatabaseHelper(context);
	}

	//Returns a long to determine if the insert was successful
	public long insertData(List<String> newData){
		//Creates an object of the SQLiteDatabase itself and then opens / creates a writeable database
		SQLiteDatabase db = helper.getWritableDatabase(); //This returns a database object

		//Create a content values object to help put data in
		ContentValues contentValues = new ContentValues();

		//Put data. Parameters are Key, Value
		//Put the values into the content values so that it can update
		contentValues.put(helper.COLUMN_WIDGET_ID,  newData.get(0));
		contentValues.put(helper.COLUMN_SYNC_FREQUENCY,  newData.get(1));
		contentValues.put(helper.COLUMN_USERNAME,  newData.get(2));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_ALL,  newData.get(3));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_CORRESPONDENCE,  newData.get(4));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_TUTORIAL,  newData.get(5));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_MEDAL,  newData.get(6));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_INTELLIGENCE,  newData.get(7));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_ALERT,  newData.get(8));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_ATTACK,  newData.get(9));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_COLONIZATION,  newData.get(10));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_COMPLAINT,  newData.get(11));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_EXCAVATOR,  newData.get(12));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_MISSION,  newData.get(13));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_PARLIAMENT,  newData.get(14));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_PROBE,  newData.get(15));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_SPIES,  newData.get(16));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_TRADE,  newData.get(17));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_FISSURE,  newData.get(18));
		contentValues.put(helper.COLUMN_TAG_CHOSEN,  newData.get(19));
		contentValues.put(helper.COLUMN_COLOR_BACKGROUND_CHOICE,  newData.get(20));
		contentValues.put(helper.COLUMN_COLOR_FONT_CHOICE,  newData.get(21));
		contentValues.put(helper.COLUMN_SESSION_ID,  newData.get(22));
		contentValues.put(helper.COLUMN_EMPIRE_ID,  newData.get(23));
		contentValues.put(helper.COLUMN_NOTIFICATIONS_BOOLEAN,  newData.get(24));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_TOTAL_RECEIVED,  newData.get(25));

		//Put the data into the database itself
		long result = db.insert(helper.TABLE_NAME, null, contentValues);

		return result;

	}

	//Returns ALL of the data in the database via a string
	public String getAllTableData(){
		//First make the object
		SQLiteDatabase db = helper.getWritableDatabase();

		//Cursor object for traversing the results of the database
		Cursor cursor;

		//Create a string array of data
		String[] columns = {helper.UID, helper.COLUMN_WIDGET_ID }; //Removed: , helper.COLUMN_WIDGET_ID

		//Query the database. Third param is the search params, null returns ALL
		//This returns a cursor object
		cursor = db.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null); //May need to add more Null arguments

		//Stringbuffer to hold the data from the cursor
		StringBuffer buffer = new StringBuffer();

		//While the cursor can move to the next (while there are more rows)
		while(cursor.moveToNext()){
			//Integer is retrieving the column number where the UID is. We do know it is at 0, but,
			//it could change eventually, so this is better programming for now.
			int index1 = cursor.getColumnIndex(DatabaseHelper.UID);
			//Get the integer from the column UID (Unique Identifier)
			int cid = cursor.getInt(index1);

			//Integer is retrieving column number where the name is
			int index2 = cursor.getColumnIndex(DatabaseHelper.COLUMN_WIDGET_ID); /////////////////This area needs to be updated///////////////////////
			//Get the String from the column Name
			String name0 = cursor.getString(index2);

			//Integer is retrieving column number where the password is
			int index3 = cursor.getColumnIndex(DatabaseHelper.COLUMN_WIDGET_ID); /////////////////This area needs to be updated///////////////////////
			//Get the String from the column password
			String password = cursor.getString(index3);

			//Add the items to the buffer
			buffer.append(cid + " " + name0 + " " + password + "\n");
		}

		return buffer.toString();
	}

	//This returns a specific query, the password for the username entered
	public List<String> getRow(String widget_id){
		//First make the object
		SQLiteDatabase db = helper.getWritableDatabase();

		//Cursor object for traversing the results of the database
		Cursor cursor;

		//Create a string array of data
		String[] columns = {helper.COLUMN_WIDGET_ID, helper.COLUMN_SYNC_FREQUENCY,
				helper.COLUMN_USERNAME, helper.COLUMN_MESSAGE_COUNT_ALL,
				helper.COLUMN_MESSAGE_COUNT_CORRESPONDENCE, helper.COLUMN_MESSAGE_COUNT_TUTORIAL,
				helper.COLUMN_MESSAGE_COUNT_MEDAL, helper.COLUMN_MESSAGE_COUNT_INTELLIGENCE,
				helper.COLUMN_MESSAGE_COUNT_ALERT, helper.COLUMN_MESSAGE_COUNT_ATTACK,
				helper.COLUMN_MESSAGE_COUNT_COLONIZATION, helper.COLUMN_MESSAGE_COUNT_COMPLAINT,
				helper.COLUMN_MESSAGE_COUNT_EXCAVATOR, helper.COLUMN_MESSAGE_COUNT_MISSION,
				helper.COLUMN_MESSAGE_COUNT_PARLIAMENT, helper.COLUMN_MESSAGE_COUNT_PROBE,
				helper.COLUMN_MESSAGE_COUNT_SPIES, helper.COLUMN_MESSAGE_COUNT_TRADE,
				helper.COLUMN_MESSAGE_COUNT_FISSURE, helper.COLUMN_TAG_CHOSEN,
				helper.COLUMN_COLOR_BACKGROUND_CHOICE, helper.COLUMN_COLOR_FONT_CHOICE,
				helper.COLUMN_SESSION_ID, helper.COLUMN_EMPIRE_ID, helper.COLUMN_NOTIFICATIONS_BOOLEAN,
				helper.COLUMN_MESSAGE_COUNT_TOTAL_RECEIVED};

		//Query the database. Third param are the search params. This returns a cursor object
		cursor = db.query(DatabaseHelper.TABLE_NAME, columns,
				helper.COLUMN_WIDGET_ID + " = '" + widget_id + "'", null, null, null, null);

		//Stringbuffer to hold the data from the cursor
		//StringBuffer buffer = new StringBuffer();

		List<String> pulled_data = new ArrayList<>();

		//While the cursor can move to the next (while there are more rows)
		while(cursor.moveToNext()){

			//Integer is retrieving column number where the name is
			int index0 = cursor.getColumnIndex(DatabaseHelper.COLUMN_WIDGET_ID);
			//Get the String from the column Name
			String str0 = cursor.getString(index0);
			int index1 = cursor.getColumnIndex(DatabaseHelper.COLUMN_SYNC_FREQUENCY);
			String str1 = cursor.getString(index1);
			int index2 = cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME);
			String str2 = cursor.getString(index2);
			int index3 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_ALL);
			String str3 = cursor.getString(index3);
			int index4 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_CORRESPONDENCE);
			String str4 = cursor.getString(index4);
			int index5 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_TUTORIAL);
			String str5 = cursor.getString(index5);
			int index6 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_MEDAL);
			String str6 = cursor.getString(index6);
			int index7 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_INTELLIGENCE);
			String str7 = cursor.getString(index7);
			int index8 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_ALERT);
			String str8 = cursor.getString(index8);
			int index9 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_ATTACK);
			String str9 = cursor.getString(index9);
			int index10 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_COLONIZATION);
			String str10 = cursor.getString(index10);
			int index11 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_COMPLAINT);
			String str11 = cursor.getString(index11);
			int index12 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_EXCAVATOR);
			String str12 = cursor.getString(index12);
			int index13 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_MISSION);
			String str13 = cursor.getString(index13);
			int index14 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_PARLIAMENT);
			String str14 = cursor.getString(index14);
			int index15 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_PROBE);
			String str15 = cursor.getString(index15);
			int index16 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_SPIES);
			String str16 = cursor.getString(index16);
			int index17 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_TRADE);
			String str17 = cursor.getString(index17);
			int index18 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_FISSURE);
			String str18 = cursor.getString(index18);
			int index19 = cursor.getColumnIndex(DatabaseHelper.COLUMN_TAG_CHOSEN);
			String str19 = cursor.getString(index19);
			int index20 = cursor.getColumnIndex(DatabaseHelper.COLUMN_COLOR_BACKGROUND_CHOICE);
			String str20 = cursor.getString(index20);
			int index21 = cursor.getColumnIndex(DatabaseHelper.COLUMN_COLOR_FONT_CHOICE);
			String str21 = cursor.getString(index21);
			int index22 = cursor.getColumnIndex(DatabaseHelper.COLUMN_SESSION_ID);
			String str22 = cursor.getString(index22);
			int index23 = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPIRE_ID);
			String str23 = cursor.getString(index23);
			int index24 = cursor.getColumnIndex(DatabaseHelper.COLUMN_NOTIFICATIONS_BOOLEAN);
			String str24 = cursor.getString(index24);
			int index25 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_COUNT_TOTAL_RECEIVED);
			String str25 = cursor.getString(index25);

			pulled_data.add(str0);
			pulled_data.add(str1);
			pulled_data.add(str2);
			pulled_data.add(str3);
			pulled_data.add(str4);
			pulled_data.add(str5);
			pulled_data.add(str6);
			pulled_data.add(str7);
			pulled_data.add(str8);
			pulled_data.add(str9);
			pulled_data.add(str10);
			pulled_data.add(str11);
			pulled_data.add(str12);
			pulled_data.add(str13);
			pulled_data.add(str14);
			pulled_data.add(str15);
			pulled_data.add(str16);
			pulled_data.add(str17);
			pulled_data.add(str18);
			pulled_data.add(str19);
			pulled_data.add(str20);
			pulled_data.add(str21);
			pulled_data.add(str22);
			pulled_data.add(str23);
			pulled_data.add(str24);
			pulled_data.add(str25);

		}

		return pulled_data;
	}

	//This returns the sync_interval
	public String getSyncInterval(String widget_id){
		//First make the object
		SQLiteDatabase db = helper.getWritableDatabase();

		//Cursor object for traversing the results of the database
		Cursor cursor;

		//Create a string array of data
		String[] columns = {helper.COLUMN_SYNC_FREQUENCY,};

		//Query the database. Third param are the search params. This returns a cursor object
		cursor = db.query(DatabaseHelper.TABLE_NAME, columns,
				helper.COLUMN_WIDGET_ID + " = '" + widget_id + "'", null, null, null, null);

		String str = "15";


		//While the cursor can move to the next (while there are more rows)
		while(cursor.moveToNext()){

			//Integer is retrieving column number where the name is
			int index0 = cursor.getColumnIndex(DatabaseHelper.COLUMN_SYNC_FREQUENCY);
			//Get the String from the column Name
			String str0 = cursor.getString(index0);
			str = str0;
		}

		return str;

	}

	//This returns the User ID of the user whose username and pw matches parameters
	public String getUID(String name, String pw){
		//First make the object
		SQLiteDatabase db = helper.getWritableDatabase();

		//Cursor object for traversing the results of the database
		Cursor cursor;

		//Create a string array of data to pass in for the second argument
		String[] columns = {helper.UID};

		//Create a string array of data to pass in for the third argument
		String[] selectionArgs = { name, pw };

		//Query the database. Third param are the search params. This returns a cursor object
		cursor = db.query(DatabaseHelper.TABLE_NAME, columns,
				helper.COLUMN_WIDGET_ID + " =? AND " + helper.COLUMN_WIDGET_ID + " =?", //selectionArgs holds strings to answer the ? vars
				selectionArgs, null, null, null, null); //May need to add more Null arguments

		/////////////////This area needs to be updated///////////////////////

		//Stringbuffer to hold the data from the cursor
		StringBuffer buffer = new StringBuffer();

		//While the cursor can move to the next (while there are more rows)
		while(cursor.moveToNext()){

			//Integer is retrieving column number where the name is
			int index1 = cursor.getColumnIndex(DatabaseHelper.UID);
			//Get the String from the column Name
			int id1 = cursor.getInt(index1);

			//Add the items to the buffer
			buffer.append(id1 + "\n");
		}

		return buffer.toString();
	}

	//Updates a name in the database
	public int updateRow(String widget_id, List<String> newData){

		//Creating an SQL database by referencing the adapter,
		//which references the helper object, which opens the database
		SQLiteDatabase db = helper.getWritableDatabase();

		//Content values for passing in data
		ContentValues contentValues = new ContentValues();

		//Put the values into the content values so that it can update
		contentValues.put(helper.COLUMN_WIDGET_ID,  newData.get(0));
		contentValues.put(helper.COLUMN_SYNC_FREQUENCY,  newData.get(1));
		contentValues.put(helper.COLUMN_USERNAME,  newData.get(2));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_ALL,  newData.get(3));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_CORRESPONDENCE,  newData.get(4));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_TUTORIAL,  newData.get(5));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_MEDAL,  newData.get(6));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_INTELLIGENCE,  newData.get(7));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_ALERT,  newData.get(8));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_ATTACK,  newData.get(9));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_COLONIZATION,  newData.get(10));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_COMPLAINT,  newData.get(11));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_EXCAVATOR,  newData.get(12));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_MISSION,  newData.get(13));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_PARLIAMENT,  newData.get(14));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_PROBE,  newData.get(15));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_SPIES,  newData.get(16));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_TRADE,  newData.get(17));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_FISSURE,  newData.get(18));
		contentValues.put(helper.COLUMN_TAG_CHOSEN,  newData.get(19));
		contentValues.put(helper.COLUMN_COLOR_BACKGROUND_CHOICE,  newData.get(20));
		contentValues.put(helper.COLUMN_COLOR_FONT_CHOICE,  newData.get(21));
		contentValues.put(helper.COLUMN_SESSION_ID,  newData.get(22));
		contentValues.put(helper.COLUMN_EMPIRE_ID,  newData.get(23));
		contentValues.put(helper.COLUMN_NOTIFICATIONS_BOOLEAN, newData.get(24));
		contentValues.put(helper.COLUMN_MESSAGE_COUNT_TOTAL_RECEIVED, newData.get(25));

		//The last parameter in update needs a string array, not a string, so creating the array here
		String[] whereArgs = {widget_id};

		//Update. Follows the command: UPDATE TABLE SET NAME = "" WHERE NAME = ?
		//Returns a count of how many rows were updated
		int count = db.update(helper.TABLE_NAME, contentValues, helper.COLUMN_WIDGET_ID + " =?", whereArgs);
		return count;
	}

	//Deletes a row in the database by the widget_id being passed in
	public int deleteRow(String widget_id){
		//Creating an SQL database by referencing the adapter,
		//which references the helper object, which opens the database
		SQLiteDatabase db = helper.getWritableDatabase();

		//The last parameter in update needs a string array, not a string, so creating the array here
		String[] whereArgs = {widget_id};

		//Delete. Follows the command: DELETE * FROM TABLE WHERE NAME = ""
		int count = db.delete(helper.TABLE_NAME, helper.COLUMN_WIDGET_ID + "=?", whereArgs);

		return count;
	}

	public void open() throws SQLException {
		database = helper.getWritableDatabase();
	}

	public void close() {
		helper.close();
	}

	/////////////////////////////////////////////////////////////////////////////////
	//This class creates an object which extends to the database for commands
	//It is nested to encapsulate it
	static class DatabaseHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "widgetdatabase";
		private static final String TABLE_NAME = "widgettable";
		private static final int DATBASE_VERSION = 5;
		private static final String UID = "_id";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;
		// Column with the foreign key into the location table.
		public static final String COLUMN_WIDGET_ID = "widget_id";
		// Stored as an int, frequency in minutes that the widget updates (IE 15)
		public static final String COLUMN_SYNC_FREQUENCY = "sync_frequency";
		// Stored as a string, username (IE Silmailos (US1))
		public static final String COLUMN_USERNAME = "username";
		// Stored as a string, total number of messages with no specified tag (IE 1210)
		public static final String COLUMN_MESSAGE_COUNT_ALL = "message_count_all";
		// Stored as an int, total number of messages with received tag correspondence. (IE 15)
		public static final String COLUMN_MESSAGE_COUNT_CORRESPONDENCE = "message_count_correspondence";
		// Stored as an int, total number of messages with received tag tutorial
		public static final String COLUMN_MESSAGE_COUNT_TUTORIAL = "message_count_tutorial";
		// Stored as an int, total number of messages with received tag medal
		public static final String COLUMN_MESSAGE_COUNT_MEDAL = "message_count_medal";
		// Stored as an int, total number of messages with received tag intelligence
		public static final String COLUMN_MESSAGE_COUNT_INTELLIGENCE = "message_count_intelligence";
		// Stored as an int, total number of messages with received tag alert
		public static final String COLUMN_MESSAGE_COUNT_ALERT = "message_count_alert";
		// Stored as an int, total number of messages with received tag attack
		public static final String COLUMN_MESSAGE_COUNT_ATTACK = "message_count_attack";
		// Stored as an int, total number of messages with received tag colonization
		public static final String COLUMN_MESSAGE_COUNT_COLONIZATION = "message_count_colonization";
		// Stored as an int, total number of messages with received tag complaint
		public static final String COLUMN_MESSAGE_COUNT_COMPLAINT = "message_count_complaint";
		// Stored as an int, total number of messages with received tag excavator
		public static final String COLUMN_MESSAGE_COUNT_EXCAVATOR = "message_count_excavator";
		// Stored as an int, total number of messages with received tag mission
		public static final String COLUMN_MESSAGE_COUNT_MISSION = "message_count_mission";
		// Stored as an int, total number of messages with received tag parliament
		public static final String COLUMN_MESSAGE_COUNT_PARLIAMENT = "message_count_parliament";
		// Stored as an int, total number of messages with received tag probe
		public static final String COLUMN_MESSAGE_COUNT_PROBE = "message_count_probe";
		// Stored as an int, total number of messages with received tag spies
		public static final String COLUMN_MESSAGE_COUNT_SPIES = "message_count_spies";
		// Stored as an int, total number of messages with received tag trade
		public static final String COLUMN_MESSAGE_COUNT_TRADE = "message_count_trade";
		// Stored as an int, total number of messages with received tag fissure
		public static final String COLUMN_MESSAGE_COUNT_FISSURE = "message_count_fissure";
		// Stored as a string, tag chosen (IE Correspondence)
		public static final String COLUMN_TAG_CHOSEN = "tag_chosen";
		// Stored as a string, choice of color background (IE blue)
		public static final String COLUMN_COLOR_BACKGROUND_CHOICE = "color_background_choice";
		// Stored as a string, choice of color font (IE blue)
		public static final String COLUMN_COLOR_FONT_CHOICE = "color_font_choice";
		// Stored as a String, the Session ID (IE 14th3-jk123k-12345k)
		public static final String COLUMN_SESSION_ID = "session_id";
		//Foreign Key
		public static final String COLUMN_EMPIRE_ID = "empire_id";
		// Stored as a String, actually a boolean, true or false
		public static final String COLUMN_NOTIFICATIONS_BOOLEAN = "notifications_boolean";
		// Stored as a String, actually an int of the total number of messages received. This factors in the tag they chose
		public static final String COLUMN_MESSAGE_COUNT_TOTAL_RECEIVED = "message_count_total_received";



		private Context context; //In case we need context

		//Create table statement//
		private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_WIDGET_ID + " VARCHAR(255),"
				+ COLUMN_SYNC_FREQUENCY + " VARCHAR(255),"
				+ COLUMN_USERNAME + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_ALL + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_CORRESPONDENCE + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_TUTORIAL + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_MEDAL + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_INTELLIGENCE + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_ALERT + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_ATTACK + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_COLONIZATION + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_COMPLAINT + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_EXCAVATOR + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_MISSION + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_PARLIAMENT + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_PROBE + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_SPIES + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_TRADE + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_FISSURE + " VARCHAR(255),"
				+ COLUMN_TAG_CHOSEN + " VARCHAR(255),"
				+ COLUMN_COLOR_BACKGROUND_CHOICE + " VARCHAR(255),"
				+ COLUMN_COLOR_FONT_CHOICE + " VARCHAR(255),"
				+ COLUMN_SESSION_ID + " VARCHAR(255),"
				+ COLUMN_EMPIRE_ID + " VARCHAR(255),"
				+ COLUMN_NOTIFICATIONS_BOOLEAN + " VARCHAR(255),"
				+ COLUMN_MESSAGE_COUNT_TOTAL_RECEIVED + " VARCHAR(255)"
				+");";

		//Constructor
		//Parameters are usually: Context context, String database_name, CursorFactory custom_cursor, int datbase_version
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATBASE_VERSION);
			this.context = context;
		}

		//This is called when the database is called for the first time
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_TABLE);
				Log.d("Database ", "Has been created");
			} catch (SQLException e) {
				Log.d("Database ", "Was NOT created");
				e.printStackTrace();
			}
		}

		//This is called when the database schema is changed (IE new columns or tables or deleting tables)
		//YOU CAN use the ALTER TABLE call instead whenever you change a table if you don't want to use this
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL(DROP_TABLE);
				Log.d("Database ", "Has been Dropped");
				onCreate(db);
			} catch (SQLException e) {
				e.printStackTrace();
				Log.d("Database ", " Issue with onUpgrade");
			}
		}
	}
}
