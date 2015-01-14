package com.JazzDevStudio.LacunaExpress.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by PatrickSSD2 on 1/13/2015.
 */
public class DatabaseProvider extends ContentProvider {

	// The URI Matcher used by this content provider.
	private static final UriMatcher sUriMatcher = buildUriMatcher();
	private DatabaseHelper mOpenHelper;

	private static final SQLiteQueryBuilder sWeatherByLocationSettingQueryBuilder;

	static {
		sWeatherByLocationSettingQueryBuilder = new SQLiteQueryBuilder();
		sWeatherByLocationSettingQueryBuilder.setTables(
				DatabaseContract.WidgetEntry.TABLE_NAME +
				"InnerJoin" +
				DatabaseContract.EmpireEntry.TABLE_NAME +
				" ON " + DatabaseContract.WidgetEntry.TABLE_NAME +
				"." + DatabaseContract.WidgetEntry.COLUMN_EMPIRE_ID +
				" = " + DatabaseContract.EmpireEntry.TABLE_NAME +
				" . " + DatabaseContract.EmpireEntry._ID
		);
	}

	private static final String sLocationSettingSelection = //EDIT
			DatabaseContract.WidgetEntry.TABLE_NAME + //EDIT
			"." + DatabaseContract.WidgetEntry.COLUMN_WIDGET_ID + " = ?"; //EDIT

	private static final String sLocationSettingWithStartDateSelection = //EDIT
			DatabaseContract.EmpireEntry.TABLE_NAME+ //EDIT
					"." + DatabaseContract.EmpireEntry.COLUMN_NAME + " = ? AND " + //EDIT
					DatabaseContract.EmpireEntry.COLUMN_SERVER + " >= ? "; //EDIT

	private Cursor getWeatherByLocationSetting(Uri uri, String[] projection, String sortOrder){
		String locationSetting = DatabaseContract.EmpireEntry.getLocationSettingFromUri(uri);
		String startDate = DatabaseContract.EmpireEntry.getStartDateFromUri(uri);

		String[] selectionArgs;
		String selection;

		if (startDate == null) {
			selection = sLocationSettingSelection;
			selectionArgs = new String[]{locationSetting};
		} else {
			selectionArgs = new String[]{locationSetting, startDate};
			selection = sLocationSettingWithStartDateSelection;
		}

		return sWeatherByLocationSettingQueryBuilder.query(mOpenHelper.getReadableDatabase(),
				projection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder
		);
	}

	private static final int WEATHER = 100;
	private static final int WEATHER_WITH_LOCATION = 101;
	private static final int WEATHER_WITH_LOCATION_AND_DATE = 102;
	private static final int LOCATION = 300;
	private static final int LOCATION_ID = 301;

	private static UriMatcher buildUriMatcher(){
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = DatabaseContract.CONTENT_AUTHORITY;

		//Items stored as Strings use *
		matcher.addURI(authority, DatabaseContract.PATH_WIDGET, WEATHER);  //EDIT
		matcher.addURI(authority, DatabaseContract.PATH_WIDGET + "/*", WEATHER_WITH_LOCATION); //EDIT
		matcher.addURI(authority, DatabaseContract.PATH_WIDGET + "/*/*", WEATHER_WITH_LOCATION_AND_DATE); //EDIT

		//Items stored as numbers use #
		matcher.addURI(authority, DatabaseContract.PATH_WIDGET, LOCATION); //EDIT
		matcher.addURI(authority, DatabaseContract.PATH_WIDGET + "/#", LOCATION_ID); //EDIT //Though leave as # as _id is always long int

		return matcher;
	}

	//
	public boolean onCreate() {
		mOpenHelper = new DatabaseHelper(getContext());
		return true; //Return true to tell android the content provider has been created successfully
	}

	//The queries. EVERY URI has to be filled out here
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

		Cursor retCursor;

		switch (sUriMatcher.match(uri)) {
			// "weather/*/*"
			case WEATHER_WITH_LOCATION_AND_DATE:
			{
				retCursor = getWeatherByLocationSetting(uri, projection, sortOrder);
				break;
			}
			// "weather/*"
			case WEATHER_WITH_LOCATION: {
				retCursor = getWeatherByLocationSetting(uri, projection, sortOrder);
				break;
			}
			// "weather"
			case WEATHER: {
				retCursor = mOpenHelper.getReadableDatabase().query(
						WeatherContract.WeatherEntry.TABLE_NAME,
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sortOrder
				);
				break;
			}
			// "location/*"
			case LOCATION_ID: {
				retCursor = mOpenHelper.getReadableDatabase().query(
						WeatherContract.LocationEntry.TABLE_NAME,
						projection,
						WeatherContract.LocationEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
						null,
						null,
						null,
						sortOrder
				);
				break;
			}
			// "location"
			case LOCATION: {
				retCursor = mOpenHelper.getReadableDatabase().query(
						WeatherContract.LocationEntry.TABLE_NAME,
						projection,
						selection,
						selectionArgs,
						null,
						null,
						sortOrder
				);
				break;
			}

			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		//Watches for changes to the URI
		retCursor.setNotificationUri(getContext().getContentResolver(), uri);
		return retCursor;

	}

	//Returns the MIME type at the given URI
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);

		switch (match){
			case WEATHER_WITH_LOCATION_AND_DATE:
				return DatabaseContract.WidgetEntry.CONTENT_ITEM_TYPE; //vnd.android.cursor.item/ //Single Item

			case WEATHER_WITH_LOCATION:
				return DatabaseContract.WidgetEntry.CONTENT_TYPE; //vnd.android.cursor.dir/ //Multiple Items

			case WEATHER:
				return DatabaseContract.WidgetEntry.CONTENT_TYPE; //vnd.android.cursor.dir/ //Multiple Items

			default:
				throw new UnsupportedOperationException("Unknown URI: " + uri);


		}
	}

	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}
}
