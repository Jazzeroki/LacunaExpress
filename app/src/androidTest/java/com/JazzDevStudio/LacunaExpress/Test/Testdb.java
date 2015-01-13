package com.JazzDevStudio.LacunaExpress.Test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.JazzDevStudio.LacunaExpress.Database.DatabaseContract;
import com.JazzDevStudio.LacunaExpress.Database.DatabaseHelper;

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

	public void testInsertReadDb(){

		DatabaseHelper dbHelper = new DatabaseHelper(mContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		//Create map of values, where column names are keys
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

		long locationRowId;
		locationRowId = db.insert(DatabaseContract.WidgetEntry.TABLE_NAME, null, values);

		//Verify a row came back
		assertTrue(locationRowId != -1);
		Log.d("LOGGING", "New Row ID: "+locationRowId);

		//Specify columns returned
		String[] columns = {
				DatabaseContract.WidgetEntry.COLUMN_WIDGET_ID,
				DatabaseContract.WidgetEntry.COLUMN_COLOR_BACKGROUND_CHOICE,
				DatabaseContract.WidgetEntry.COLUMN_COLOR_FONT_CHOICE,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ALERT,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ALL,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ATTACK,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_COLONIZATION,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_COMPLAINT,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_CORRESPONDENCE,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_EXCAVATOR,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_FISSURE,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_INTELLIGENCE,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_MEDAL,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_MISSION,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_PARLIAMENT,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_PROBE,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_SPIES,
				DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_TUTORIAL,
				DatabaseContract.WidgetEntry.COLUMN_SYNC_FREQUENCY,
				DatabaseContract.WidgetEntry.COLUMN_TAG_CHOSEN,
				DatabaseContract.WidgetEntry.COLUMN_USERNAME,
				DatabaseContract.WidgetEntry.COLUMN_WIDGET_ID,
				DatabaseContract.WidgetEntry.COLUMN_SESSION_ID
		};

		//Cursor to query results
		Cursor cursor = db.query(
				DatabaseContract.WidgetEntry.TABLE_NAME,
				columns,
				null,
				null,
				null,
				null,
				null);

		if (cursor.moveToFirst()){
			//Get the value in each column by finding the appropriate index
			int color_background_choice_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_COLOR_BACKGROUND_CHOICE);
			String background_choice = cursor.getString(color_background_choice_index);

			int color_font_choice_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_COLOR_FONT_CHOICE);
			String font_choice = cursor.getString(color_font_choice_index);

			int all_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ALL);
			String all = cursor.getString(all_index);

			int tag_chosen_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_TAG_CHOSEN);
			String tag = cursor.getString(tag_chosen_index);

			int username_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_USERNAME);
			String username = cursor.getString(username_index);

			int widget_id_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_WIDGET_ID);
			String widget_id = cursor.getString(widget_id_index);

			int alert_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ALERT);
			String alert = cursor.getString(alert_index);

			int attack_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_ATTACK);
			String attack = cursor.getString(attack_index);

			int colonization_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_COLONIZATION);
			String colonization = cursor.getString(colonization_index);

			int complaint_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_COMPLAINT);
			String complaint = cursor.getString(complaint_index);

			int correspondence_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_CORRESPONDENCE);
			String correspondence = cursor.getString(correspondence_index);

			int excavator_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_EXCAVATOR);
			String excavator = cursor.getString(excavator_index);

			int fissure_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_FISSURE);
			String fissure = cursor.getString(fissure_index);

			int intelligence_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_INTELLIGENCE);
			String intelligence = cursor.getString(intelligence_index);

			int medal_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_MEDAL);
			String medal = cursor.getString(medal_index);

			int mission_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_MISSION);
			String mission = cursor.getString(mission_index);

			int parliament_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_PARLIAMENT);
			String parliament = cursor.getString(parliament_index);

			int probe_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_PROBE);
			String probe = cursor.getString(probe_index);

			int spies_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_SPIES);
			String spies = cursor.getString(spies_index);

			int tutorial_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_MESSAGE_COUNT_TUTORIAL);
			String tutorial = cursor.getString(tutorial_index);

			int frequency_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_SYNC_FREQUENCY);
			String frequency = cursor.getString(frequency_index);

			int session_id_index = cursor.getColumnIndex(DatabaseContract.WidgetEntry.COLUMN_SESSION_ID);
			String session_id = cursor.getString(session_id_index);

		} else {
			fail("Nothing returned! : (");
		}
	}
}
