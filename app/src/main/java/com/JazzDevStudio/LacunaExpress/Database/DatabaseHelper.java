package com.JazzDevStudio.LacunaExpress.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PatrickSSD2 on 12/29/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

	// If you change the database schema, you must increment the database version.
	private static final int DATABASE_VERSION = 1;

	public static final String DATABASE_NAME = "lacunaexpress.db";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//On the creation of the database
	public void onCreate(SQLiteDatabase sqLiteDatabase) {

		// Create a table to hold locations.
		//NOT FINISHED AT THE MOMENT. JUST IMPLEMENTING BASICS SO WE CAN COMPILE
		final String SQL_CREATE_MESSAGE_TABLE = "CREATE TABLE " + DatabaseContract.MessagesEntry.TABLE_NAME + " (" +
				DatabaseContract.MessagesEntry._ID + " INTEGER PRIMARY KEY," +
				DatabaseContract.MessagesEntry.COLUMN_FROM + " TEXT UNIQUE NOT NULL, " +
				DatabaseContract.MessagesEntry.COLUMN_FROM + " TEXT NOT NULL, " +
				DatabaseContract.MessagesEntry.COLUMN_FROM + " REAL NOT NULL, " +
				DatabaseContract.MessagesEntry.COLUMN_FROM + " REAL NOT NULL, " +
				"UNIQUE (" + DatabaseContract.MessagesEntry.COLUMN_FROM +") ON CONFLICT IGNORE"+
				" );";

		final String SQL_CREATE_EMPIRE_TABLE = "CREATE TABLE " + DatabaseContract.EmpireEntry.TABLE_NAME + " (" +
				// Why AutoIncrement here, and not above?
				// Unique keys will be auto-generated in either case.  But for weather
				// forecasting, it's reasonable to assume the user will want information
				// for a certain date and all dates *following*, so the forecast data
				// should be sorted accordingly.
				DatabaseContract.EmpireEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

				// the ID of the location entry associated with this weather data
				//DatabaseContract.EmpireEntry.COLUMN_EMPIRE_ID + " INTEGER NOT NULL, " +
				DatabaseContract.EmpireEntry.COLUMN_RPC_COUNT + " TEXT NOT NULL, " +
				DatabaseContract.EmpireEntry.COLUMN_RPC_COUNT + " TEXT NOT NULL, " +
				DatabaseContract.EmpireEntry.COLUMN_RPC_COUNT + " INTEGER NOT NULL," +

				DatabaseContract.EmpireEntry.COLUMN_RPC_COUNT + " REAL NOT NULL, " +
				DatabaseContract.EmpireEntry.COLUMN_RPC_COUNT + " REAL NOT NULL, " +

				DatabaseContract.EmpireEntry.COLUMN_RPC_COUNT + " REAL NOT NULL, " +
				DatabaseContract.EmpireEntry.COLUMN_RPC_COUNT + " REAL NOT NULL, " +
				DatabaseContract.EmpireEntry.COLUMN_RPC_COUNT + " REAL NOT NULL, " +
				DatabaseContract.EmpireEntry.COLUMN_RPC_COUNT + " REAL NOT NULL, " +

				// Set up the location column as a foreign key to location table.
				//" FOREIGN KEY (" + DatabaseContract.EmpireEntry.COLUMN_LOC_KEY + ") REFERENCES " +
				//DatabaseContract.MessagesEntry.TABLE_NAME + " (" + DatabaseContract.MessagesEntry._ID + "), " +

				// To assure the application have just one weather entry per day
				// per location, it's created a UNIQUE constraint with REPLACE strategy
				//" UNIQUE (" + DatabaseContract.EmpireEntry.COLUMN_RPC_COUNT + ", " +
				//DatabaseContract.EmpireEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";
				");";

		final String SQL_CREATE_WIDGET_TABLE = "CREATE TABLE " + DatabaseContract.WidgetEntry.TABLE_NAME + " (" +
				// Why AutoIncrement here, and not above?
				// Unique keys will be auto-generated in either case.  But for weather
				// forecasting, it's reasonable to assume the user will want information
				// for a certain date and all dates *following*, so the forecast data
				// should be sorted accordingly.
				DatabaseContract.WidgetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

				// the ID of the location entry associated with this weather data
				DatabaseContract.WidgetEntry.COLUMN_TAG_CHOSEN + " TEXT NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_SYNC_FREQUENCY + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_USERNAME + " TEXT NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ALL + " TEXT NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_TAG_CHOSEN + " TEXT NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_COLOR_BACKGROUND_CHOICE + " TEXT NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_COLOR_FONT_CHOICE + " TEXT NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_CORRESPONDENCE + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_TUTORIAL + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_MEDAL + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_INTELLIGENCE + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ATTACK + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ALERT + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_COLONIZATION + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_COMPLAINT + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_EXCAVATOR + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_MISSION + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_PARLIAMENT + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_PROBE + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_SPIES + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_FISSURE + " INTEGER NOT NULL, " +
				DatabaseContract.WidgetEntry.COLUMN_SESSION_ID + "TEXT NOT NULL" +

				// Set up the location column as a foreign key to location table.
				//" FOREIGN KEY (" + DatabaseContract.WidgetEntry.COLUMN_WIDGET_ID + ") REFERENCES " +
				//DatabaseContract.MessagesEntry.TABLE_NAME + " (" + DatabaseContract.MessagesEntry._ID + "), " +

				// To assure the application have just one weather entry per day
				// per location, it's created a UNIQUE constraint with REPLACE strategy
				//" UNIQUE (" + DatabaseContract.WidgetEntry.COLUMN_RPC_COUNT + ", " +
				//DatabaseContract.EmpireEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";
				");";

		sqLiteDatabase.execSQL(SQL_CREATE_MESSAGE_TABLE);
		sqLiteDatabase.execSQL(SQL_CREATE_EMPIRE_TABLE);
		sqLiteDatabase.execSQL(SQL_CREATE_WIDGET_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		// Note that this only fires if you change the version number for your database.
		// It does NOT depend on the version number for your application.
		// If you want to update the schema without wiping data, commenting out the next 2 lines
		// should be your top priority before modifying this method.
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.EmpireEntry.TABLE_NAME);
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MessagesEntry.TABLE_NAME);
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.WidgetEntry.TABLE_NAME);
		onCreate(sqLiteDatabase);
	}
}
