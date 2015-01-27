package com.JazzDevStudio.LacunaExpress.Widget;

import android.content.Context;
import android.util.Log;

import com.JazzDevStudio.LacunaExpress.Database.TEMPDatabaseAdapter;

import java.util.ArrayList;
import java.util.List;

//State management and utilities
public class WidgetState {

	//ADD aLL OF THE PuBLIC FIELDS HERE. IE mail_count, color background, etc
	//May need to change methods back to private if duplicate issues

	private static final String ACTION_WIDGET_CONTROL = "com.JazzDevStudio.LacunaExpress.Widget.WIDGET_CONTROL";
	String user_name, color_background_choice, font_color_choice, message_count_string,
			message_count_int, sessionID, homePlanetID;
	String sync_frequency = "15"; //Default
	String tag_chosen = "All";

	//Retrieves a new WidgetState object from the database
	//@Params: context, app widget identifier
	public static WidgetState getState(Context context, int appWidgetId) {
		WidgetState state = new WidgetState();

		//Pull data from the database here to add it to the 'state'
		//Create a database object and set the values here
		TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(context);

		//For the row ID
		String widget_id = Integer.toString(appWidgetId);

		try {
			//Extract the return data from the List and use it//

			//List to hold returned data
			List<String> db_data = new ArrayList<>();

			//Set the returned data = to the row's returned data
			db_data = db.getRow(widget_id);

			state.user_name = db_data.get(2);
			Log.d("MailWidgetUpdateService Database username = ", state.user_name);
			state.tag_chosen = db_data.get(19);
			Log.d("MailWidgetUpdateService Database tag chosen = ", state.tag_chosen);
			state.color_background_choice = db_data.get(20);
			Log.d("MailWidgetUpdateService Database color background choice = ", state.color_background_choice);
			state.font_color_choice = db_data.get(21);
			Log.d("MailWidgetUpdateService Database font color choice = ", state.font_color_choice);
			state.message_count_int = db_data.get(3);
			Log.d("MailWidgetUpdateService Database message count int = ", state.message_count_int);

			//Determine which tag chosen parameter was passed and return the mail respective to that call
			if (state.tag_chosen.equalsIgnoreCase("All")) {
				state.message_count_string = db_data.get(4);
			} else if (state.tag_chosen.equalsIgnoreCase("Correspondence")) {
				state.message_count_string = db_data.get(4);
			} else if (state.tag_chosen.equalsIgnoreCase("Tutorial")) {
				state.message_count_string = db_data.get(5);
			} else if (state.tag_chosen.equalsIgnoreCase("Medal")) {
				state.message_count_string = db_data.get(6);
			} else if (state.tag_chosen.equalsIgnoreCase("Intelligence")) {
				state.message_count_string = db_data.get(7);
			} else if (state.tag_chosen.equalsIgnoreCase("Alert")) {
				state.message_count_string = db_data.get(8);
			} else if (state.tag_chosen.equalsIgnoreCase("Attack")) {
				state.message_count_string = db_data.get(9);
			} else if (state.tag_chosen.equalsIgnoreCase("Colonization")) {
				state.message_count_string = db_data.get(10);
			} else if (state.tag_chosen.equalsIgnoreCase("Complaint")) {
				state.message_count_string = db_data.get(11);
			} else if (state.tag_chosen.equalsIgnoreCase("Excavator")) {
				state.message_count_string = db_data.get(12);
			} else if (state.tag_chosen.equalsIgnoreCase("Mission")) {
				state.message_count_string = db_data.get(13);
			} else if (state.tag_chosen.equalsIgnoreCase("Parliament")) {
				state.message_count_string = db_data.get(14);
			} else if (state.tag_chosen.equalsIgnoreCase("Probe")) {
				state.message_count_string = db_data.get(15);
			} else if (state.tag_chosen.equalsIgnoreCase("Spies")) {
				state.message_count_string = db_data.get(16);
			} else if (state.tag_chosen.equalsIgnoreCase("Trade")) {
				state.message_count_string = db_data.get(17);
			} else if (state.tag_chosen.equalsIgnoreCase("Fissure")) {
				state.message_count_string = db_data.get(18);
			}
			Log.d("MailWidgetUpdateService Database message count string = ", state.message_count_string);

			Log.d("Database", "Has been queried");

			state.sessionID = db_data.get(22);
			state.homePlanetID = db_data.get(23);

		} catch (IndexOutOfBoundsException e) {
		}

		//Close the database
		try {
			db.close();
		} catch (Exception e){
			Log.d("WidgetState", "ERROR closing db");
		}

		return state;
	}

	//Store the updated state via writing to the database. @Params: context, app widget identifier, the widgetstate that is being stored
	public static void storeState(Context context, int appWidgetId, WidgetState state) {
		//Create a database object and set the values here
		TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(context);

		//For the row ID
		String widget_id = Integer.toString(appWidgetId);

		try {

			//List of Strings to hold the passed data
			List<String> passed_data = new ArrayList<>();

			passed_data.add(widget_id); //0
			passed_data.add(state.sync_frequency); //1
			passed_data.add(state.user_name); //2
			passed_data.add(state.message_count_int); //3
			/*
			I am writing all of these in as the specific tag chosen because as the App widget ID is unique, it
			will not matter as it is not checking the other columns. Once I add an update to allow for editing
			widgets however, this will need to be changed. Furthermore, I will need to change it to raw SQL
			update code to allow for specific passing of the tag chosen and using that to write.
			 */
			passed_data.add(state.message_count_string); //4
			passed_data.add(state.message_count_string); //5
			passed_data.add(state.message_count_string); //6
			passed_data.add(state.message_count_string); //7
			passed_data.add(state.message_count_string); //8
			passed_data.add(state.message_count_string); //9
			passed_data.add(state.message_count_string); //10
			passed_data.add(state.message_count_string); //11
			passed_data.add(state.message_count_string); //12
			passed_data.add(state.message_count_string); //13
			passed_data.add(state.message_count_string); //14
			passed_data.add(state.message_count_string); //15
			passed_data.add(state.message_count_string); //16
			passed_data.add(state.message_count_string); //17
			passed_data.add(state.message_count_string); //18
			passed_data.add(state.tag_chosen); //19
			passed_data.add(state.color_background_choice); //20
			passed_data.add(state.font_color_choice); //21
			passed_data.add(state.sessionID); //22
			passed_data.add(state.homePlanetID); //23
			//AS OF RIGHT NOW, this line above is passing in the homePlanetID instead of the empire ID.
			//This will be changed later on
			db.insertData(passed_data);

		} catch (Exception e){
			e.printStackTrace();
			Log.d("WidgetState", "Error in insertData() method");
		}

		try {
			db.close();
		} catch (Exception e){
			Log.d("WidgetState", "ERROR closing db");
		}
	}

	//Deletes a set of state information from the database. @Params: context, app widget identifier
	public static void deleteStateForId(Context context, int appWidgetId) {
		//Create a database object and set the values here
		TEMPDatabaseAdapter db = new TEMPDatabaseAdapter(context);

		try {
			db.deleteRow(Integer.toString(appWidgetId));
		} catch (Exception e){
			Log.d("Widget State", "Issue with deleting row in database");
		}

		try {
			db.close();
		} catch (Exception e){
			Log.d("WidgetState", "ERROR closing db");
		}
	}
}

