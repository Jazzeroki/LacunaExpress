package com.JazzDevStudio.LacunaExpress.Test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.JazzDevStudio.LacunaExpress.Database.DatabaseContract;
import com.JazzDevStudio.LacunaExpress.Database.DatabaseHelper;

import java.util.Map;
import java.util.Set;

/**
 * Created by PatrickSSD2 on 1/12/2015.
 */
public class Testdb extends AndroidTestCase{

	public void testCreatedb() throws Throwable{
		mContext.deleteDatabase(DatabaseHelper.DATABASE_NAME);
		SQLiteDatabase db = new DatabaseHelper(this.mContext).getWritableDatabase();
		assertEquals(true, db.isOpen());
		db.close();
	}

	ContentValues getWidgetContentValues(){

		ContentValues values = new ContentValues();
		values.put(DatabaseContract.WidgetEntry.COLUMN_COLOR_BACKGROUND_CHOICE, "blue");
		values.put(DatabaseContract.WidgetEntry.COLUMN_WIDGET_ID, "123");
		values.put(DatabaseContract.WidgetEntry.COLUMN_COLOR_FONT_CHOICE, "blue");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ALERT, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ALL, "1000");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ATTACK, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_COLONIZATION, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_COMPLAINT, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_CORRESPONDENCE, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_EXCAVATOR, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_FISSURE, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_INTELLIGENCE, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_MEDAL, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_MISSION, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_PARLIAMENT, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_PROBE, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_SPIES, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_TUTORIAL, "9");
		values.put(DatabaseContract.WidgetEntry.COLUMN_SYNC_FREQUENCY, "21");
		values.put(DatabaseContract.WidgetEntry.COLUMN_TAG_CHOSEN, "Attack");
		values.put(DatabaseContract.WidgetEntry.COLUMN_USERNAME, "Runescholar");
		values.put(DatabaseContract.WidgetEntry.COLUMN_SESSION_ID, "Session_ID_123_abc");

		return values;
	}

	//Validate the cursor
	static public void validateCursor(ContentValues expectedValues, Cursor valueCursor){
		//
		Set <Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

		for (Map.Entry<String, Object> entry : valueSet){
			String columnName = entry.getKey();
			int idx = valueCursor.getColumnIndex(columnName);
			assertFalse(-1 == idx);
			String expectedValue = entry.getValue().toString();
			assertEquals(expectedValue, valueCursor.getString(idx));
		}
	}

	//Reads in the database
	public void testInsertReadDb(){

		DatabaseHelper dbHelper = new DatabaseHelper(mContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		//Create map of values, where column names are keys
		ContentValues values = getWidgetContentValues();

		long locationRowId;
		locationRowId = db.insert(DatabaseContract.WidgetEntry.TABLE_NAME, null, values);

		//Verify a row came back
		assertTrue(locationRowId != -1);
		Log.d("LOGGING", "New Row ID: "+locationRowId);

		//Spe
		//Cursor to query results
		Cursor cursor = db.query(
				DatabaseContract.WidgetEntry.TABLE_NAME,
				null,
				null,
				null,
				null,
				null,
				null);

		if (cursor.moveToFirst()){
			validateCursor(values, cursor);
		} else {
			fail("Nothing returned! : (");
		}
	}
}
